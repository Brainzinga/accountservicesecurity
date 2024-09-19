package com.dxc.accountservice.domain.service;

import com.dxc.accountservice.domain.dto.AccountDtoRequest;
import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.domain.dto.RestMoneyBalanceDto;
import com.dxc.accountservice.domain.dto.AddAmountBalanceDto;
import com.dxc.accountservice.persistence.entity.Account;
import com.dxc.accountservice.persistence.entity.Customer;
import com.dxc.accountservice.exception.AccountNotFoundException;
import com.dxc.accountservice.exception.CustomerNotfoundException;
import com.dxc.accountservice.exception.InsufficientException;
import com.dxc.accountservice.persistence.mapper.AccountMapper;
import com.dxc.accountservice.persistence.repository.AccountRepository;
import com.dxc.accountservice.persistence.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService  {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountMapper accountMapper;



    @Override
    public List<AccountDtoResponse> listarCuentasCliente(Long customerId){
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotfoundException("Customer not found with id: " + customerId));
        return accountMapper.toAccountDtoResponseList(accountRepository.findAllByCustomer(customer));
    }
    @Override
    public AccountDtoResponse getByAccountIdAndCustomerId(Long accountId, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotfoundException("Customer not found with id: " + customerId));
        Optional<Account> account = accountRepository.findByIdAndCustomer(accountId, customer);
        if(account.isPresent()){
            return accountMapper.toAccountDtoResponse(account.get());
        }
        throw new AccountNotFoundException("this account does not exist for customer with id: " + customerId);
    }

    @Override
    @Transactional
    public AccountDtoResponse crearCuenta(AccountDtoRequest accountDtoRequest) {
       Account account  = accountMapper.toAccount(accountDtoRequest);
       Customer customer = customerRepository.findById(accountDtoRequest.getCustomerId())
               .orElseThrow(()-> new CustomerNotfoundException("Customer not found with id: " + accountDtoRequest.getCustomerId()));
       account.setCustomer(customer);
           return accountMapper.toAccountDtoResponse(accountRepository.save(account));
    }
    @Override
    @Transactional
    public AccountDtoResponse actualizarCuenta(AccountDtoRequest accountDtoRequest,Long accountId) {
        if(accountRepository.existsById(accountId)){
            return  accountMapper.toAccountDtoResponse(accountRepository.save(accountMapper.toAccount(accountDtoRequest)));
        }
        throw new RuntimeException("Account not found with id: " + accountId);
    }

    @Override
    public String eliminarCuentasPorCliente(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotfoundException("Customer not found with id: " + customerId));
//        if(customer!= null){
            List<Account> accounts = accountRepository.findAllByCustomer(customer);
            if(accounts.isEmpty()){
                return "El cliente no tiene cuentas";
            }
            accountRepository.deleteAll(accounts);
            return "Se han eliminado todas las cuentas del cliente";
//            return true;
//        }
//        return false;
    }

    @Override
    @Transactional
    public boolean addMoneyToBalance(AddAmountBalanceDto addAmountBalanceDto) {
        Optional<Account> account = accountRepository.findById(addAmountBalanceDto.getAccountId());
       if(account.isPresent()){
           if(customerRepository.existsById(addAmountBalanceDto.getCustomerId())){
               account.get().setBalance(account.get().getBalance() + addAmountBalanceDto.getAmount());
               accountRepository.save(account.get());
               return true;
           }
           throw new CustomerNotfoundException("Customer not found with id: " + addAmountBalanceDto.getCustomerId());
       }
       throw new AccountNotFoundException("Account not found with id: " + addAmountBalanceDto.getAccountId());
    }

    @Override
    @Transactional
    public boolean restMoneyToBalance(RestMoneyBalanceDto restMoneyBalanceDto,boolean allAccounts) {
        Customer customer=customerRepository.findById(restMoneyBalanceDto.getCustomerId()).orElseThrow(() -> new CustomerNotfoundException("Customer not found with id: " + restMoneyBalanceDto.getCustomerId()  ));
        if(allAccounts){
            System.out.println("*************************************************************************************************************************************");
             return accountRepository.restMoneyAllAccount(customer,restMoneyBalanceDto.getAmount());
        }
         Account account = accountRepository.findById(restMoneyBalanceDto.getAccountId()).orElseThrow(() -> new AccountNotFoundException("Account not found with id: " + restMoneyBalanceDto.getAccountId()  ));
        if(account != null && account.getBalance() >= restMoneyBalanceDto.getAmount() ){
                    account.setBalance(account.getBalance() - restMoneyBalanceDto.getAmount());
                    accountRepository.save(account);
                    return true;
        }
        throw new InsufficientException("Insufficient balance in account with id: " + restMoneyBalanceDto.getAccountId());
    }

    @Override
    public String comprobarPrestamo(Long customerId,Integer monto) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotfoundException("Customer not found with id: " + customerId));
        Integer totalBalance = accountRepository.findAllByCustomer(customer).stream().mapToInt(Account::getBalance).sum();
        Integer calculadoPrestamo = Math.toIntExact(Math.round(totalBalance * 0.8));

        if(monto < calculadoPrestamo){
            return "El cliente " + customer.getName() + " puede solicitar el prestamo";
        }

        return "El cliente " + customer.getName() + " NO puede solicitar el prestamo";
    }

}
