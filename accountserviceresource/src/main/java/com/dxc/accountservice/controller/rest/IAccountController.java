package com.dxc.accountservice.controller.rest;

import com.dxc.accountservice.domain.dto.AccountDtoRequest;
import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.domain.dto.AddAmountBalanceDto;
import com.dxc.accountservice.domain.dto.RestMoneyBalanceDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
public interface IAccountController {


    @GetMapping
    ResponseEntity<List<AccountDtoResponse>> getAccount();

    @GetMapping(path = "/customer/{customerId}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<List<AccountDtoResponse>> getAccountByCustomer(@PathVariable Long customerId);


    @GetMapping(path = "/{accountId}/{customerId}" ,produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE} )
    ResponseEntity<AccountDtoResponse> obtenerCuentaPorId(@Positive @PathVariable Long accountId, @Positive @PathVariable Long customerId);


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<AccountDtoResponse> crearCuenta(@Valid @RequestBody  AccountDtoRequest AccountDtoRequest);

    @PutMapping("/{accountId}")
    ResponseEntity<AccountDtoResponse> actualizarCuenta(@PathVariable Long accountId,
                                                               @Valid @RequestBody AccountDtoRequest account);

    @DeleteMapping("/{customerId}")
    ResponseEntity<String> eliminarCuentas(@PathVariable Long customerId);

    @PostMapping("/add-money")
    ResponseEntity<Boolean> addMoneyToBalance(@Valid @RequestBody AddAmountBalanceDto addAmountBalanceDto);

    @PostMapping("/rest-money")
    ResponseEntity<Boolean> addMoneyToBalance(@Valid @RequestBody RestMoneyBalanceDto restMoneyBalanceDto,
                                                     @RequestParam(required = false) boolean allAccounts);


    @GetMapping(path = "/comprobar-prestamo/{customerId}/{monto}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    ResponseEntity<String> comprobarPrestamo(
            @Positive @PathVariable Long customerId,
            @Positive @PathVariable Integer monto);
}
