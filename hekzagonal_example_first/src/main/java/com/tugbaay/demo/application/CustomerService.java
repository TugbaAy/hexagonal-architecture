package com.tugbaay.demo.application;

import com.tugbaay.demo.adapter.outbound.persistence.CustomerPersistenceAdapter;
import com.tugbaay.demo.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerPersistenceAdapter customerPersistenceAdapter;

    public CustomerService(CustomerPersistenceAdapter customerPersistenceAdapter) {
        this.customerPersistenceAdapter = customerPersistenceAdapter;
    }

    public Customer createCustomer(Customer customer) {
        return customerPersistenceAdapter.save(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerPersistenceAdapter.findById(id);
    }
}
