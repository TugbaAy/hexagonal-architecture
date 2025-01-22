package com.tugbaay.demo.infrastructure;

import com.tugbaay.demo.adapter.outbound.persistence.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {}
