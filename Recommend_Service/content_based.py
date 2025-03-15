import requests
import pyodbc
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

def get_sql_connection():
    conn = pyodbc.connect('DRIVER={SQL Server};SERVER=your_server;DATABASE=TechGalaxy;UID=your_user;PWD=your_password')
    return conn

def get_product_details(product_id):
    url = f"http://localhost:8081/{product_id}"
    try:
        response = requests.get(url)
        response.raise_for_status()  # Kiểm tra nếu có lỗi HTTP
        product = response.json()  # Chuyển đổi dữ liệu JSON sang dict
        return product if product else None
    except requests.exceptions.RequestException as e:
        print(f"Lỗi khi gọi API: {e}")
        return None
        
def content_based_filtering(customer_id, top_n=5):
    conn = get_sql_connection()
    query = "SELECT DISTINCT product_id FROM user_actions WHERE customer_id = ? AND action_type = 'VIEW'"
    viewed_products = pd.read_sql(query, conn, params=[customer_id])
    conn.close()

    if viewed_products.empty:
        return []

    product_data = [get_product_details(pid) for pid in viewed_products["product_id"] if get_product_details(pid)]
    if not product_data:
        return []

    df = pd.DataFrame(product_data)
    tfidf = TfidfVectorizer(stop_words='english')
    tfidf_matrix = tfidf.fit_transform(df['name'] + " " + df['description'])
    cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

    indices = pd.Series(df.index, index=df['id'])
    recommended_products = cosine_sim[indices[df["id"].iloc[0]]].argsort()[-top_n:][::-1]
    
    return df['id'].iloc[recommended_products].tolist()
