from collaborative import collaborative_filtering
from content_based import content_based_filtering

def hybrid_recommendation(customer_id, top_n=5):
    collaborative_results = collaborative_filtering(customer_id, top_n * 2)
    content_based_results = content_based_filtering(customer_id, top_n * 2)

    combined_results = []
    seen = set()

    for product in collaborative_results + content_based_results:
        if product and product['variant_id'] not in seen:
            combined_results.append(product)
            seen.add(product['variant_id'])
        if len(combined_results) >= top_n:
            break

    return combined_results
