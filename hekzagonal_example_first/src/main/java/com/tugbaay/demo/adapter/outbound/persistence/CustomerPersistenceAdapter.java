package com.tugbaay.demo.adapter.outbound.persistence;

import com.tugbaay.demo.domain.Customer;
import com.tugbaay.demo.infrastructure.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerPersistenceAdapter {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerPersistenceAdapter(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Customer save(Customer customer) {
        CustomerEntity entity = mapper.toEntityList(customer);
        CustomerEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    public Optional<Customer> findById(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    public List<Customer> findAll() {
        return mapper.toDomainList(repository.findAll());
    }

}
