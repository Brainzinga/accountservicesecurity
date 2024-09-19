package com.dxc.accountservice.persistence.repository;

import com.dxc.accountservice.exception.InsufficientException;
import com.dxc.accountservice.persistence.entity.Account;
import com.dxc.accountservice.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByCustomer(Customer customer);
    Optional<Account> findByIdAndCustomer(Long accountId, Customer customer);

    @Transactional
    default boolean restMoneyAllAccount(Customer customer, Integer amount) {
        List<Account> accounts = this.findAllByCustomer(customer);
        if(!accounts.isEmpty()){
            Integer totalBalance = accounts.stream().mapToInt(Account::getBalance).sum();
            if(totalBalance >= amount){
               Integer  amountRest = amount;
                Account accountWithBalanceBiggerThanAmount= accounts.stream().filter(a -> a.getBalance() >= amount).findFirst().orElse(null);
                if(accountWithBalanceBiggerThanAmount == null) {
                 for (Account account : accounts) {
                     amountRest = amountRest - account.getBalance();
                     account.setBalance(0);
                     this.save(account);
                     if(amountRest == 0){
                         break;
                     }
                 }
                 return true;
                 }
                accountWithBalanceBiggerThanAmount.setBalance(accountWithBalanceBiggerThanAmount.getBalance() - amountRest);
                this.save(accountWithBalanceBiggerThanAmount);
                return true;
            }
            throw new InsufficientException("Insufficient balance in all account with client id: " +  customer.getId());
        }
        throw new RuntimeException("Client with id: " +  customer.getId() + " does not have any accounts.");
    }

}
