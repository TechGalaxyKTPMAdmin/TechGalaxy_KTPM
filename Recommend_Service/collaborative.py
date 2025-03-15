import pyodbc
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
from config import Config

ACTION_WEIGHTS = {
    "PURCHASE": 10,
    "ADD_TO_CART": 7,
    "CLICK": 5,
    "VIEW": 3,
    "SEARCH": 1
}

import pyodbc

def get_sql_connection():
    conn = pyodbc.connect(Config.get_connection_string())
    return conn

def get_product_details(product_id):
    conn = get_sql_connection()
    query = "SELECT id, name FROM products WHERE id = ?"
    product = pd.read_sql(query, conn, params=[product_id])
    conn.close()
    return product.to_dict('records')[0] if not product.empty else None

def collaborative_filtering(customer_id, top_n=5):
    conn = get_sql_connection()
    query = "SELECT customer_id, product_id, action_type FROM user_actions"
    df = pd.read_sql(query, conn)
    conn.close()

    if df.empty:
        return []

    df["weight"] = df["action_type"].map(ACTION_WEIGHTS)
    user_product_matrix = df.pivot_table(index="customer_id", columns="product_id", values="weight", aggfunc="sum", fill_value=0)
    
    similarity_matrix = cosine_similarity(user_product_matrix)
    similarity_df = pd.DataFrame(similarity_matrix, index=user_product_matrix.index, columns=user_product_matrix.index)
    
    similar_users = similarity_df.get(customer_id)
    if similar_users is None:
        return []

    similar_users = similar_users.sort_values(ascending=False)[1:6]
    recommended_products = user_product_matrix.loc[similar_users.index].sum().sort_values(ascending=False)
    
    return [get_product_details(pid) for pid in recommended_products.index[:top_n]]
