import py_eureka_client.eureka_client as eureka_client
from flask import Flask, request, jsonify
from flask_cors import CORS
from content_based import content_based_filtering, get_product_details
from collaborative import collaborative_filtering
from hybrid import hybrid_recommendation

app = Flask(__name__)
CORS(app)

# Khởi tạo Eureka client để hỗ trợ service discovery (nếu cần)
eureka_client.init(
    eureka_server="http://discoveryserver:8761/eureka/",
    app_name="RecommendationService",
    instance_port=5000
)

@app.route('/api/recommendations/content-based/<customer_id>', methods=['GET'])
def get_content_based_recommendations(customer_id):
    try:
        top_n = request.args.get('top_n', default=5, type=int)
        recommendations = content_based_filtering(customer_id, top_n)
        return jsonify({
            "success": True,
            "customer_id": customer_id,
            "recommendations": recommendations
        })
    except Exception as e:
        return jsonify({"success": False, "error": str(e)}), 500

@app.route('/api/recommendations/collaborative/<customer_id>', methods=['GET'])
def get_collaborative_recommendations(customer_id):
    try:
        top_n = request.args.get('top_n', default=5, type=int)
        recommendations = collaborative_filtering(customer_id, top_n)
        return jsonify({
            "success": True,
            "customer_id": customer_id,
            "recommendations": recommendations
        })
    except Exception as e:
        return jsonify({"success": False, "error": str(e)}), 500

@app.route('/api/recommendations/hybrid/<customer_id>', methods=['GET'])
def get_hybrid_recommendations(customer_id):
    try:
        top_n = request.args.get('top_n', default=5, type=int)
        recommendations = hybrid_recommendation(customer_id, top_n)
        return jsonify({
            "success": True,
            "customer_id": customer_id,
            "recommendations": recommendations
        })
    except Exception as e:
        return jsonify({"success": False, "error": str(e)}), 500

@app.route('/api/product/<product_variant_id>', methods=['GET'])
def get_product(product_variant_id):
    try:
        product = get_product_details(product_variant_id)
        if product:
            return jsonify({"success": True, "product": product})
        else:
            return jsonify({"success": False, "error": "Product variant not found"}), 404
    except Exception as e:
        return jsonify({"success": False, "error": str(e)}), 500

@app.route('/')
def hello():
    return 'Hello, World!'

if __name__ == '__main__':
    app.run(debug=True, port=5000)
