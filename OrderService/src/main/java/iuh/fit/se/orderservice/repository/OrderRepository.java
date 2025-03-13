package iuh.fit.se.orderservice.repository;

import iuh.fit.se.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders", exported = false)
public interface OrderRepository extends JpaRepository<Order, String> {
    // get list of orders by customer id
    List<Order> getOrdersByCustomerId(String customerId);

    boolean findOrderById(String id);
}
