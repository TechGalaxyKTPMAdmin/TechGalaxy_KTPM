package iuh.fit.se.orderservice.repository;

import iuh.fit.se.orderservice.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "orderDetails", path = "orderDetails", exported = false)
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {
    List<OrderDetail> findOrderDetailsByOrderId(String orderId);

    OrderDetail findOrderDetailByOrderIdAndProductVariantDetailId(String orderId, String productVariantDetailId);
}
