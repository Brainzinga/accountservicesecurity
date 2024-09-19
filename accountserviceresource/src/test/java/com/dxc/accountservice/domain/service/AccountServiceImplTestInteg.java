package com.dxc.accountservice.domain.service;

import com.dxc.accountservice.domain.dto.AccountDtoRequest;
import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.exception.AccountNotFoundException;
import com.dxc.accountservice.exception.CustomerNotfoundException;
import com.dxc.accountservice.persistence.mapper.AccountMapper;
import com.dxc.accountservice.persistence.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@SpringBootTest
@Sql(value = "classpath:data_testing.sql")
class AccountServiceImplTestInteg {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository  accountRepository;
    @Autowired
    private AccountMapper accountMapper;

    @Test
    void givenAccountIdAndCustomerId_whenGetByAccountIdAndCustomerId_thenAccountIsNotNull() {
        AccountDtoResponse accountDtoResponse= accountService.getByAccountIdAndCustomerId(1L,1L);
        assertNotNull(accountDtoResponse);
         assertThat(accountDtoResponse).isNotNull().hasFieldOrPropertyWithValue("id",1L);

    }
    @Test
    void  givenAccountIdAndCustomerId_whenGetByAccountIdNotExistAndCustomerId_thenAccountNotFoundException() {

       AccountNotFoundException notFoundException =  assertThrows(AccountNotFoundException.class,()->{
            accountService.getByAccountIdAndCustomerId(100L,1L);
        });

        assertThat(notFoundException).hasMessage("this account does not exist for customer with id: 1");
    }
    @Test
    void givenOneAccount_whenCrearCuenta_thenAccountCreated() {
        AccountDtoRequest accountDtoRequest = AccountDtoRequest.builder().balance(100).type("Company").openingDate(LocalDate.now()).customerId(1L).build();
        AccountDtoResponse accountDtoResponse = accountService.crearCuenta(accountDtoRequest);
        assertNotNull(accountDtoResponse);
        assertThat(accountDtoResponse).hasFieldOrPropertyWithValue("balance",100);
    }
       @Test
    void givenOneAccount_whenInvalidAccount_thenCustomerNotFoundException() {
        AccountDtoRequest accountDtoRequest = AccountDtoRequest.builder().balance(100).type("Company").openingDate(LocalDate.now()).customerId(100L).build();
         CustomerNotfoundException  notFoundException =  assertThrows(CustomerNotfoundException.class,()->{
            accountService.crearCuenta(accountDtoRequest);
        });
         assertThat(notFoundException).hasMessage("Customer not found with id: 100");

    }
}