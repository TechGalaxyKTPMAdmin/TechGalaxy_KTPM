import pyodbc
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
from config import Config

ACTION_WEIGHTS = {
    "PURCHASE": 10,
    "ADD_TO_CART": 7,
    "VIEW": 5,
    "SEARCH": 1
}

def get_sql_connection():
    # Kết nối đến TechGalaxy chứa RecommendDB và UserDB
    return pyodbc.connect(Config.get_connection_string())

def get_product_details(product_variant_id):
    """
    Lấy thông tin chi tiết của một variant sản phẩm bằng cách join
    bảng products_variants và products.
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

def collaborative_filtering(customer_id, top_n=5):
    conn = get_sql_connection()
    query = "SELECT customer_id, product_id, action_type FROM user_actions WHERE customer_id IS NOT NULL"
    df = pd.read_sql(query, conn)
    conn.close()

    if df.empty or customer_id not in df['customer_id'].values:
        return []

    # Tính trọng số cho hành động
    df["weight"] = df["action_type"].map(ACTION_WEIGHTS)
    user_product_matrix = df.pivot_table(index="customer_id", columns="product_id", values="weight", aggfunc="sum", fill_value=0)
    
    if customer_id not in user_product_matrix.index:
        return []
    
    # Tính toán cosine similarity giữa các khách hàng
    similarity_matrix = cosine_similarity(user_product_matrix)
    similarity_df = pd.DataFrame(similarity_matrix, index=user_product_matrix.index, columns=user_product_matrix.index)
    
    similar_users = similarity_df.get(customer_id)
    if similar_users is None:
        return []

    # Chọn 5 khách hàng tương tự (bỏ chính khách hàng đang xét)
    similar_users = similar_users.sort_values(ascending=False)[1:6]
    recommended_products = user_product_matrix.loc[similar_users.index].sum().sort_values(ascending=False)
    
    recommendations = []
    for pid in recommended_products.index[:top_n]:
        product = get_product_details(pid)
        if product:
            recommendations.append(product)
    return recommendations
