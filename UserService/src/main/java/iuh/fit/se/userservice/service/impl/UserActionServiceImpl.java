package iuh.fit.se.userservice.service.impl;

import iuh.fit.se.userservice.entities.Customer;
import iuh.fit.se.userservice.entities.UserAction;
import iuh.fit.se.userservice.entities.enumeration.ActionType;
import iuh.fit.se.userservice.repository.CustomerRepository;
import iuh.fit.se.userservice.repository.UserActionRepository;
import iuh.fit.se.userservice.service.UserActionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserActionServiceImpl implements UserActionService {
    private final UserActionRepository userActionRepository;
    private final CustomerRepository customerRepository;

    public UserActionServiceImpl(UserActionRepository userActionRepository, CustomerRepository customerRepository) {
        this.userActionRepository = userActionRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserAction saveAction(UserAction userAction) {
        // Validate the user action
        if (userAction.getCustomer() == null || userAction.getActionType() == null) {
            throw new IllegalArgumentException("User ID and Action Type must not be null");
        }

        // Save the user action to the database
        return userActionRepository.save(userAction);
    }

    @Override
    public List<UserAction> getActionsByUserId(String userId) {
        // Fetch actions by user ID
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID must not be null or empty");
        }
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + userId));
        return userActionRepository.findByCustomer(customer);
    }

    @Override
    public UserAction getActionsByUserIdAndType(String userId, ActionType actionType) {
        // Fetch actions by user ID and action type
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID must not be null or empty");
        }
        if (actionType == null) {
            throw new IllegalArgumentException("Action Type must not be null");
        }
        Customer customer = customerRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + userId));
        return userActionRepository.findByCustomerAndActionType(customer, actionType);
    }
}
