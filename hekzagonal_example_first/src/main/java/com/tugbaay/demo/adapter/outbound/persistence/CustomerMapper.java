package com.tugbaay.demo.adapter.outbound.persistence;

import com.tugbaay.demo.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerMapper {

    public Customer toDomain(CustomerEntity entity) {
        return new Customer(entity.getId(), entity.getName(), entity.getEmail());
    }

    public CustomerEntity toEntityList(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.id());
        entity.setName(customer.name());
        entity.setEmail(customer.email());
        return entity;
    }

    public List<Customer> toDomainList(List<CustomerEntity> entities) {
        List<Customer> customers = new ArrayList<>();
        for (CustomerEntity entity : entities) {
            customers.add(toDomain(entity));
        }
        return customers;
    }

    public List<CustomerEntity> toEntityList(List<Customer> customers) {
        List<CustomerEntity> entities = new ArrayList<>();
        for (Customer customer : customers) {
            entities.add(toEntityList(customer));
        }
        return entities;
    }
}
