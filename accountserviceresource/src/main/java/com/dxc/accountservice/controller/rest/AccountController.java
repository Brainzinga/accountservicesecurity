package com.dxc.accountservice.controller.rest;

import com.dxc.accountservice.domain.dto.AccountDtoRequest;
import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.domain.dto.AddAmountBalanceDto;
import com.dxc.accountservice.domain.dto.RestMoneyBalanceDto;

import com.dxc.accountservice.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController implements IAccountController {

    @Autowired
    private AccountService accountService;

    @Override
    public ResponseEntity<List<AccountDtoResponse>> getAccount() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    public ResponseEntity<List<AccountDtoResponse>> getAccountByCustomer(Long customerId) {
        return ResponseEntity.ok(accountService.listarCuentasCliente(customerId));
    }

    public ResponseEntity<AccountDtoResponse> obtenerCuentaPorId(Long accountId,  Long customerId) {
        return ResponseEntity.ok(accountService.getByAccountIdAndCustomerId(accountId,customerId));
    }

    public ResponseEntity<AccountDtoResponse> crearCuenta(AccountDtoRequest AccountDtoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.crearCuenta(AccountDtoRequest));
    }


    public ResponseEntity<AccountDtoResponse> actualizarCuenta( Long accountId, AccountDtoRequest account) {
        return ResponseEntity.ok(accountService.actualizarCuenta(account, accountId));
    }

    public ResponseEntity<String> eliminarCuentas( Long customerId) {
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(accountService.eliminarCuentasPorCliente(customerId));
    }

    public ResponseEntity<Boolean> addMoneyToBalance(AddAmountBalanceDto addAmountBalanceDto) {
        return ResponseEntity.ok(accountService.addMoneyToBalance(addAmountBalanceDto));
    }

    public ResponseEntity<Boolean> addMoneyToBalance(RestMoneyBalanceDto restMoneyBalanceDto, boolean allAccounts) {
        allAccounts=true;
        return ResponseEntity.ok(accountService.restMoneyToBalance(restMoneyBalanceDto, allAccounts));
    }

    public ResponseEntity<String> comprobarPrestamo( Long customerId,Integer monto) {
        return ResponseEntity.ok(accountService.comprobarPrestamo(customerId,monto));
    }
}
