package iuh.fit.se.userservice.repository;

import iuh.fit.se.userservice.entities.Customer;
import iuh.fit.se.userservice.entities.UserAction;
import iuh.fit.se.userservice.entities.enumeration.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "userActions", path = "userActions", exported = false)
public interface UserActionRepository extends JpaRepository<UserAction, String>, JpaSpecificationExecutor<UserAction> {
    // Tìm tất cả UserAction liên kết với một Customer cụ thể
    List<UserAction> findByCustomer(Customer customer);

    // findByCustomerAndActionType
    UserAction findByCustomerAndActionType(Customer customer, ActionType actionType);

}
