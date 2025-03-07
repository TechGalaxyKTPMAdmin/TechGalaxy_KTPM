package iuh.fit.se.userservice.service;

import iuh.fit.se.userservice.dto.request.CustomerRequest;
import iuh.fit.se.userservice.dto.response.CustomerResponse;
import org.springframework.hateoas.PagedModel;

import java.util.List;

public interface CustomerService {
    CustomerResponse findByEmail(String email);

    PagedModel<CustomerResponse> findAllCustomers(int page, int size);

    List<CustomerResponse> findAll();

    CustomerResponse findById(String id);

    CustomerResponse save(CustomerRequest customerRequest);

    CustomerResponse update(String id, CustomerRequest customerRequest);

    boolean delete(String id);
}
