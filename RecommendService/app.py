from flask import Flask, request, jsonify
from content_based import content_based_filtering, get_product_details
from collaborative import collaborative_filtering
from hybrid import hybrid_recommendation
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

@app.route('/api/recommendations/content-based/<customer_id>', methods=['GET'])
def get_content_based_recommendations(customer_id):
    """Get content-based recommendations for a customer"""
    try:
        top_n = request.args.get('top_n', default=5, type=int)
        recommendations = content_based_filtering(customer_id, top_n)
        
        return jsonify({
            "success": True,
            "customer_id": customer_id,
            "recommendations": recommendations
        })
    except Exception as e:
        return jsonify({
            "success": False,
            "error": str(e)
        }), 500

@app.route('/api/recommendations/collaborative/<customer_id>', methods=['GET'])
def get_collaborative_recommendations(customer_id):
    """Get collaborative filtering recommendations for a customer"""
    try:
        top_n = request.args.get('top_n', default=5, type=int)
        recommendations = collaborative_filtering(customer_id, top_n)
        
        return jsonify({
            "success": True,
            "customer_id": customer_id,
            "recommendations": recommendations
        })
    except Exception as e:
        return jsonify({
            "success": False,
            "error": str(e)
        }), 500

@app.route('/api/recommendations/hybrid/<customer_id>', methods=['GET'])
def get_hybrid_recommendations(customer_id):
    """Get hybrid recommendations for a customer"""
    try:
        top_n = request.args.get('top_n', default=5, type=int)
        recommendations = hybrid_recommendation(customer_id, top_n)
        
        return jsonify({
            "success": True,
            "customer_id": customer_id,
            "recommendations": recommendations
        })
    except Exception as e:
        return jsonify({
            "success": False,
            "error": str(e)
        }), 500

@app.route('/api/product/<product_id>', methods=['GET'])
def get_product(product_id):
    """Get detailed product information"""
    try:
        product = get_product_details(product_id)
        if product:
            return jsonify({
                "success": True,
                "product": product
            })
        else:
            return jsonify({
                "success": False,
                "error": "Product not found"
            }), 404
    except Exception as e:
        return jsonify({
            "success": False,
            "error": str(e)
        }), 500

@app.route('/')
def hello():
    return 'Hello, World!'

if __name__ == '__main__':
    app.run(debug=True, port=5000)