package iuh.fit.se.productservice.repository;

import iuh.fit.se.productservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.hateoas.PagedModel;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "customers", path = "customers", exported = false)
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query("SELECT c FROM  Customer c JOIN c.account a WHERE a.email LIKE :email")
    List<Customer> findByEmail(String email);

    @Query("SELECT c FROM Customer c")
    PagedModel<Customer> findAllCustomers(int page, int size);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.customer.id = :customerId")
    long countOrdersByCustomerId(@Param("customerId") String customerId);
}
