package com.dxc.accountservice.controller.rest;

import com.dxc.accountservice.domain.dto.AccountDtoRequest;
import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.domain.dto.AddAmountBalanceDto;
import com.dxc.accountservice.domain.dto.RestMoneyBalanceDto;

import com.dxc.accountservice.domain.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/account")
@Validated
public class AccountController implements IAccountController {

    @Autowired
    private AccountService accountService;

    public ResponseEntity<List<AccountDtoResponse>> getAccountByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountService.listarCuentasCliente(customerId));
    }

    public ResponseEntity<AccountDtoResponse> obtenerCuentaPorId(@Positive @PathVariable Long accountId, @Positive @PathVariable Long customerId) {
        return ResponseEntity.ok(accountService.getByAccountIdAndCustomerId(accountId,customerId));
    }

    public ResponseEntity<AccountDtoResponse> crearCuenta(@Valid @RequestBody  AccountDtoRequest AccountDtoRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.crearCuenta(AccountDtoRequest));
    }


    public ResponseEntity<AccountDtoResponse> actualizarCuenta(@PathVariable Long accountId,
                                                               @Valid @RequestBody AccountDtoRequest account) {
        return ResponseEntity.ok(accountService.actualizarCuenta(account, accountId));
    }

    public ResponseEntity<String> eliminarCuentas(@PathVariable Long customerId) {
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(accountService.eliminarCuentasPorCliente(customerId));
    }

    public ResponseEntity<Boolean> addMoneyToBalance(@Valid @RequestBody AddAmountBalanceDto addAmountBalanceDto) {
        return ResponseEntity.ok(accountService.addMoneyToBalance(addAmountBalanceDto));
    }

    public ResponseEntity<Boolean> addMoneyToBalance(@Valid @RequestBody RestMoneyBalanceDto restMoneyBalanceDto,
                                                     @RequestParam(required = false) boolean allAccounts) {
        allAccounts=true;
        return ResponseEntity.ok(accountService.restMoneyToBalance(restMoneyBalanceDto, allAccounts));
    }

    public ResponseEntity<String> comprobarPrestamo(@Positive @PathVariable Long customerId,@Positive @PathVariable Integer monto) {
        return ResponseEntity.ok(accountService.comprobarPrestamo(customerId,monto));
    }
}
