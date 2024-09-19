package com.dxc.accountservice.domain.service;

import com.dxc.accountservice.domain.dto.AccountDtoRequest;
import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.domain.dto.AddAmountBalanceDto;
import com.dxc.accountservice.domain.dto.RestMoneyBalanceDto;

import java.util.List;

public interface AccountService {


    AccountDtoResponse getByAccountIdAndCustomerId(Long accountId, Long customerId);
    List<AccountDtoResponse> listarCuentasCliente(Long customerId);
    AccountDtoResponse crearCuenta(AccountDtoRequest accountDtoRequest);
    AccountDtoResponse actualizarCuenta(AccountDtoRequest accountDtoRequest, Long accountId);
    boolean addMoneyToBalance(AddAmountBalanceDto addAmountBalanceDto);
    boolean restMoneyToBalance(RestMoneyBalanceDto restMoneyBalanceDto,boolean allAccounts);
    String comprobarPrestamo(Long customerId,Integer monto);

    String eliminarCuentasPorCliente(Long id);

}
