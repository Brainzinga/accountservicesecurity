package com.dxc.accountservice.persistence.repository;

import com.dxc.accountservice.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
