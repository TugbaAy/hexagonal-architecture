package com.tugbaay.demo.application;

import com.tugbaay.demo.adapter.outbound.persistence.CustomerPersistenceAdapter;
import com.tugbaay.demo.domain.Customer;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private final CustomerPersistenceAdapter persistenceAdapter = mock(CustomerPersistenceAdapter.class);
    private final CustomerService customerService = new CustomerService(persistenceAdapter);

    @Test
    void shouldCreateCustomerSuccessfully() {
        // Given
        String name = "John Doe";
        String email = "john.doe@example.com";
        Customer customer = new Customer(null, name, email);
        Customer savedCustomer = new Customer(1L, name, email);

        when(persistenceAdapter.save(customer)).thenReturn(savedCustomer);

        // When
        Customer customer1 = new Customer(null,"John Doe", "john.doe@example.com");
        Customer result = customerService.createCustomer(customer1);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals(name, result.name());
        assertEquals(email, result.email());
        verify(persistenceAdapter, times(1)).save(customer);
    }

    @Test
    void shouldReturnCustomerById() {
        // Given
        Customer customer = new Customer(1L, "John Doe", "john.doe@example.com");
        when(persistenceAdapter.findById(1L)).thenReturn(Optional.of(customer));

        // When
        Optional<Customer> result = customerService.getCustomerById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
        verify(persistenceAdapter, times(1)).findById(1L);
    }
}
