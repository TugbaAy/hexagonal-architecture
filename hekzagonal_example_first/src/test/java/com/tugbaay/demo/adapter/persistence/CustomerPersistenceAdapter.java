package com.tugbaay.demo.adapter.persistence;

import com.tugbaay.demo.adapter.outbound.persistence.CustomerEntity;
import com.tugbaay.demo.adapter.outbound.persistence.CustomerMapper;
import com.tugbaay.demo.adapter.outbound.persistence.CustomerPersistenceAdapter;
import com.tugbaay.demo.domain.Customer;
import com.tugbaay.demo.infrastructure.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerPersistenceAdapterTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerPersistenceAdapter adapter;

    private Customer customerDomain;
    private CustomerEntity customerEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customerDomain = new Customer(1L, "John Doe", "johndoe@example.com");
        customerEntity = new CustomerEntity(1L, "John Doe", "johndoe@example.com");
    }

    @Test
    void save_ShouldSaveCustomer() {
        // Arrange
        when(mapper.toEntityList(customerDomain)).thenReturn(customerEntity);
        when(repository.save(customerEntity)).thenReturn(customerEntity);
        when(mapper.toDomain(customerEntity)).thenReturn(customerDomain);

        // Act
        Customer savedCustomer = adapter.save(customerDomain);

        // Assert
        assertNotNull(savedCustomer);
        assertEquals(customerDomain, savedCustomer);
        verify(repository, times(1)).save(customerEntity);
        verify(mapper, times(1)).toEntityList(customerDomain);
        verify(mapper, times(1)).toDomain(customerEntity);
    }

    @Test
    void findById_ShouldReturnCustomer_WhenCustomerExists() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(customerEntity));
        when(mapper.toDomain(customerEntity)).thenReturn(customerDomain);

        // Act
        Optional<Customer> foundCustomer = adapter.findById(id);

        // Assert
        assertTrue(foundCustomer.isPresent());
        assertEquals(customerDomain, foundCustomer.get());
        verify(repository, times(1)).findById(id);
        verify(mapper, times(1)).toDomain(customerEntity);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenCustomerDoesNotExist() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<Customer> foundCustomer = adapter.findById(id);

        // Assert
        assertFalse(foundCustomer.isPresent());
        verify(repository, times(1)).findById(id);
        verify(mapper, never()).toDomain(any());
    }

    @Test
    void findAll_ShouldReturnAllCustomers() {
        // Arrange
        List<CustomerEntity> entities = List.of(customerEntity);
        List<Customer> customers = List.of(customerDomain);
        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDomainList(entities)).thenReturn(customers);

        // Act
        List<Customer> foundCustomers = adapter.findAll();

        // Assert
        assertNotNull(foundCustomers);
        assertEquals(1, foundCustomers.size());
        assertEquals(customerDomain, foundCustomers.get(0));
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDomainList(entities);
    }
}
