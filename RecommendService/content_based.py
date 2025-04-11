import pyodbc
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from config import Config

def get_sql_connection():
    # Kết nối đến TechGalaxy chứa RecommendDB và UserDB
    return pyodbc.connect(Config.get_connection_string())

def get_product_details(product_id):
    # Kết nối đến ProductDB để truy xuất thông tin sản phẩm
    conn = pyodbc.connect(Config.get_product_connection_string())
    query = "SELECT id, name FROM products WHERE id = ?"
    product = pd.read_sql(query, conn, params=[product_id])
    conn.close()
    return product.to_dict('records')[0] if not product.empty else None

def content_based_filtering(customer_id, top_n=5):
    # Lấy các sản phẩm mà người dùng đã VIEW từ TechGalaxy
    conn = get_sql_connection()
    query = "SELECT DISTINCT product_id FROM user_actions WHERE customer_id = ? AND action_type = 'VIEW'"
    viewed_products_df = pd.read_sql(query, conn, params=[customer_id])
    conn.close()

    if viewed_products_df.empty:
        return []

    products = []
    for pid in viewed_products_df["product_id"]:
        product = get_product_details(pid)
        if product:
            products.append(product)
    
    if not products:
        return []

    df = pd.DataFrame(products)
    if 'description' not in df.columns:
        df['description'] = ""
    
    # Xây dựng ma trận TF-IDF từ tên và mô tả sản phẩm
    tfidf = TfidfVectorizer(stop_words='english')
    tfidf_matrix = tfidf.fit_transform(df['name'] + " " + df['description'])
    cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

    # Chọn sản phẩm đầu tiên trong danh sách đã view làm mốc so sánh
    indices = pd.Series(df.index, index=df['id'])
    first_index = df.index[0]
    sim_scores = list(enumerate(cosine_sim[first_index]))
    sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
    # Loại bỏ sản phẩm đã view làm mốc
    sim_scores = [score for score in sim_scores if score[0] != first_index]
    top_indices = [i for i, score in sim_scores[:top_n]]
    
    recommended_products = []
    for idx in top_indices:
        pid = df.loc[idx, 'id']
        product = get_product_details(pid)
        if product:
            recommended_products.append(product)
    return recommended_products
