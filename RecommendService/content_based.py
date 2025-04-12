import pyodbc
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from config import Config

def get_sql_connection():
    # Kết nối đến TechGalaxy chứa RecommendDB và UserDB
    return pyodbc.connect(Config.get_connection_string())

def get_product_details(product_variant_id):
    """
    Lấy thông tin variant của sản phẩm bao gồm thông tin của sản phẩm và chi tiết variant.
    """
    conn = pyodbc.connect(Config.get_product_connection_string())
    query = """
    SELECT 
        pv.id AS variant_id, 
        p.id AS product_id, 
        p.name AS product_name,
        pv.name AS variant_name, 
        pv.description AS variant_description, 
        pv.content,
        pv.avatar,
        pv.status
    FROM products_variants pv
    JOIN products p ON pv.product_id = p.id
    WHERE p.id = ?
    """
    product = pd.read_sql(query, conn, params=[product_variant_id])
    conn.close()
    return product.to_dict('records')[0] if not product.empty else None

def content_based_filtering(customer_id, top_n=5):
    # Lấy danh sách các variant mà khách hàng đã VIEW từ TechGalaxy
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
    # Các cột cần đảm bảo có giá trị chuỗi để tính toán TF-IDF
    for col in ['product_name', 'variant_name', 'variant_description', 'content']:
        if col not in df.columns:
            df[col] = ""
        else:
            df[col] = df[col].fillna("")
    
    # Xây dựng chuỗi văn bản đặc trưng gồm product_name, variant_name, variant_description, và content
    text_features = df['product_name'] + " " + df['variant_name'] + " " + df['variant_description'] + " " + df['content']
    
    tfidf = TfidfVectorizer(stop_words='english')
    tfidf_matrix = tfidf.fit_transform(text_features)
    cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

    # Sử dụng variant đầu tiên mà khách hàng đã xem làm mốc so sánh
    indices = pd.Series(df.index, index=df['variant_id'])
    first_index = df.index[0]
    sim_scores = list(enumerate(cosine_sim[first_index]))
    sim_scores = sorted(sim_scores, key=lambda x: x[1], reverse=True)
    # Bỏ qua variant làm mốc
    sim_scores = [score for score in sim_scores if score[0] != first_index]
    top_indices = [i for i, score in sim_scores[:top_n]]
    
    recommended_products = []
    for idx in top_indices:
        pid = df.loc[idx, 'variant_id']
        product = get_product_details(pid)
        if product:
            recommended_products.append(product)
    return recommended_products
