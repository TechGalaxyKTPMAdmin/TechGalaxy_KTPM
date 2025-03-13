package iuh.fit.se.userservice.service.impl;


import iuh.fit.se.userservice.dto.request.CustomerRequest;
import iuh.fit.se.userservice.dto.response.CustomerResponse;
import iuh.fit.se.userservice.entities.Account;
import iuh.fit.se.userservice.entities.Customer;
import iuh.fit.se.userservice.exception.AppException;
import iuh.fit.se.userservice.exception.ErrorCode;
import iuh.fit.se.userservice.mapper.CustomerMapper;
import iuh.fit.se.userservice.repository.AccountRepository;
import iuh.fit.se.userservice.repository.CustomerRepository;
import iuh.fit.se.userservice.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;


    /**
     * Find all customers with pagination
     *
     * @param page
     * @param size
     * @return PagedModel<CustomerResponse>
     * author: PhamVanThanh
     */
    @Override
    public PagedModel<CustomerResponse> findAllCustomers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageRequest);

        List<CustomerResponse> customerResponses = customerPage.getContent()
                .stream()
                .map(CustomerMapper.INSTANCE::toCustomerResponse)
                .collect(Collectors.toList());
        return PagedModel.of(
                customerResponses,
                new PagedModel.PageMetadata(
                        customerPage.getSize(),
                        customerPage.getNumber(),
                        customerPage.getTotalElements()
                )
        );
    }

    /**
     * Find all customers
     *
     * @return List<CustomerResponse>
     * author: PhamVanThanh
     */
    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(CustomerMapper.INSTANCE::toCustomerResponse)
                .collect(Collectors.toList());
    }

    /**
     * Find customer by id and convert to CustomerResponse if exist else return null
     *
     * @param id
     * @return CustomerResponse
     * author: PhamVanThanh
     */
    @Override
    public CustomerResponse findById(String id) {
        return customerRepository.findById(id)
                .map(CustomerMapper.INSTANCE::toCustomerResponse).orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOTFOUND));
    }

    /**
     * Save new customer
     *
     * @param customerRequest
     * @return CustomerResponse
     * author: PhamVanThanh
     */
    @Override
    @Transactional
    public CustomerResponse save(CustomerRequest customerRequest) {
        Account account = accountRepository.findById(customerRequest.getAccount().getId()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOTFOUND));
        customerRequest.setAccount(account);
        Customer customer = CustomerMapper.INSTANCE.toCustomerFromRequest(customerRequest);
        Customer customerAdd = customerRepository.save(customer);
        return CustomerMapper.INSTANCE.toCustomerResponse(customerAdd);
    }

    /**
     * Update customer if exist else return null
     *
     * @param customerRequest
     * @return CustomerResponse
     * author: PhamVanThanh
     */
    @Override
    @Transactional
    public CustomerResponse update(String id, CustomerRequest customerRequest) {
        if (!customerRepository.existsById(id))
            throw new AppException(ErrorCode.CUSTOMER_NOTFOUND);

        Customer customerSaved = customerRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOTFOUND));
        customerSaved.setName(customerRequest.getName());
        customerSaved.setGender(customerRequest.getGender());
        customerSaved.setDateOfBirth(customerRequest.getDateOfBirth());
        customerSaved.setPhone(customerRequest.getPhone());
        customerSaved.setAvatar(customerRequest.getAvatar());
        customerSaved.setUserStatus(customerRequest.getUserStatus());
        //        Account account = accountRepository.findById(customerRequest.getAccount().getId()).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOTFOUND));
//        Customer request = CustomerMapper.INSTANCE.toCustomerFromRequest(customerRequest);
//        request.setAccount(account);
        Customer customer = customerRepository.save(customerSaved);
        return CustomerMapper.INSTANCE.toCustomerResponse(customer);
    }

    /**
     * Delete customer by id
     *
     * @param id
     * @return boolean
     * author: PhamVanThanh
     */
    @Override
    @Transactional
    public boolean delete(String id) {
        if (!customerRepository.existsById(id))
            return false;
//        long orderCount = customerRepository.countOrdersByCustomerId(id);
//        if (orderCount > 0) {
//            throw new IllegalStateException("Cannot delete account as the user has placed orders.");
//        }
        customerRepository.deleteById(id);
        return true;
    }

    /**
     * Find customer by email
     *
     * @param email
     * @return List<CustomerResponse>
     * author: PhamVanThanh
     */
    @Override
    public CustomerResponse findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .stream()
                .map(CustomerMapper.INSTANCE::toCustomerResponse)
                .findFirst()
                .orElse(null);
    }
}
