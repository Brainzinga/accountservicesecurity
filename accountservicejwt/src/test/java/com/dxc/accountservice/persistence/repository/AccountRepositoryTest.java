package com.dxc.accountservice.persistence.repository;

import com.dxc.accountservice.exception.AccountNotFoundException;
<<<<<<< HEAD
import com.dxc.accountservice.exception.InsufficientException;
=======
import com.dxc.accountservice.exception.CustomerNotfoundException;
>>>>>>> feabe1464e98d08d72bbf222c5e0e510fc450f5e
import com.dxc.accountservice.persistence.entity.Account;
import com.dxc.accountservice.persistence.entity.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest()
@ComponentScan(basePackages = {"com.dxc.accountservice.persistence.repository"})
@AutoConfigureTestEntityManager
//@ActiveProfiles("dev")
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void givenOneCustomer_whenFindAllByCustomer_thenIsNotNull() {
        Customer customer = new Customer(1L, "fakeCustomer", "j@j.com");
        customerRepository.save(customer);
        Account account = Account.builder()
                .balance(100)
                .type("Company")
                .openingDate(LocalDate.now())
                .customer(customer)
                .build();

        accountRepository.save(account);
        List<Account> accounts=accountRepository.findAllByCustomer(customer);
        assertThat(accounts.size())
                .isGreaterThan(0);

        assertNotNull(accounts);

    }

     @Test
    void givenOneCustomer_whenFindAllByCustomerNotExist_ThenListEmpty() {
        Customer customer = new Customer(1L, "fakeCustomer", "j@j.com");
        customerRepository.save(customer);
        Account account = Account.builder()
                .balance(100)
                .type("Company")
                .openingDate(LocalDate.now())
                .customer(customer)
                .build();

        accountRepository.save(account);
        List<Account> accounts=accountRepository.findAllByCustomer(new Customer(2L, "fakeCustomer", "j@j.com"));

        assertThat(accounts.size())
                .isEqualTo(0);

        assertTrue(accounts.isEmpty());

    }

     @Test
    void givenAccountIdAndCustomer_whenFindByIdAndCustomer_thenOneAccount() {
        Customer customer = Customer.builder().email("email@email.com").name("test").build();
        customerRepository.save(customer);
        Account account = Account.builder()
                .balance(100)
                .type("Company")
                .openingDate(LocalDate.now())
                .customer(customer)
                .build();
         accountRepository.save(account);

        Account findAccount = accountRepository.findByIdAndCustomer(1L,customer).get();

        assertThat(findAccount).isNotNull().extracting(Account::getBalance).isEqualTo(100);
    }

     @Test
    void givenAccountIdAndCustomer_whenFindByIdNotExistAndCustomer_thenAccountNotFoundException() {
                Customer customer = Customer.builder().id(1L).email("email@email.com").name("test").build();
        customerRepository.save(customer);
        Account account = Account.builder()
                .balance(100)
                .type("Company")
                .openingDate(LocalDate.now())
                .customer(customer)
                .build();
         accountRepository.save(account);

         Account findAccount = accountRepository.findByIdAndCustomer(2L,customer).get();

         Exception exception = assertThrows(AccountNotFoundException.class, () -> {
           accountRepository.findByIdAndCustomer(1L,customer);
        });

         assertEquals("this account does not exist for customer with id: 1", exception.getMessage());

    }

    @Test
    void givenOneCostumerAndAmount_whenRestMoneyAllAccount_thenIsTrue() {

        Customer customer = new Customer(1L, "Laura", "laura@dxc.com");
        Account account = Account.builder()
                .balance(1000)
                .type("Company")
                .openingDate(LocalDate.now())
                .customer(customer)
                .build();

        Account account2 = Account.builder()
                .balance(100)
                .type("Company")
                .openingDate(LocalDate.now())
                .customer(customer)
                .build();

        customerRepository.save(customer);
        accountRepository.save(account);
        accountRepository.save(account2);
        assertTrue(accountRepository.restMoneyAllAccount(customer, 1000));

    }

    @Test
    void givenOneCostumerAndAmount_whenInsufficientBalance_thenInsufficientBalanceException() {

        Customer customer = new Customer(1L, "Laura", "laura@dxc.com");
        Account account = Account.builder()
                .balance(1000)
                .type("Company")
                .openingDate(LocalDate.now())
                .customer(customer)
                .build();

        Account account2 = Account.builder()
                .balance(100)
                .type("Company")
                .openingDate(LocalDate.now())
                .customer(customer)
                .build();

        customerRepository.save(customer);
        accountRepository.save(account);
        accountRepository.save(account2);
        assertThrows(InsufficientException.class, () -> {
            accountRepository.restMoneyAllAccount(customer, 2000);
        });

    }
}