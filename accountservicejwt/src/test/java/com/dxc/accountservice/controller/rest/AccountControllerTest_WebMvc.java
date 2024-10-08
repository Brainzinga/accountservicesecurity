package com.dxc.accountservice.controller.rest;

import com.dxc.accountservice.domain.dto.AccountDtoRequest;
import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.domain.dto.RestMoneyBalanceDto;
import com.dxc.accountservice.domain.service.AccountService;
import com.dxc.accountservice.exception.AccountNotFoundException;
import com.dxc.accountservice.exception.CustomerNotfoundException;
import com.dxc.accountservice.persistence.entity.Customer;
import com.dxc.accountservice.persistence.mapper.AccountMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@ExtendWith(SpringExtension.class)
class AccountControllerTest_WebMvc extends AccountControllerTestAbstract {

    @MockBean
    AccountService accountService;
    @MockBean
    AccountMapper accountMapper;



    private AccountDtoResponse accDto;
    private AccountDtoRequest accountDtoRequest;
    private Customer customer;

    @BeforeEach
    public void init() {
        accDto = AccountDtoResponse.builder()
                .id(1L).balance(400).openingDate(LocalDate.now()).type("Personal").customerId(1L)
                .build();
        accountDtoRequest = AccountDtoRequest.builder()
                .balance(400).openingDate(LocalDate.now()).type("Personal").customerId(1L).build();
        List<AccountDtoResponse> accounts = List.of(new AccountDtoResponse(1L,"Company",LocalDate.now(),100,1L));
        Mockito.when(accountService.listarCuentasCliente(1L)).thenReturn(accounts);

    }

    @Test
    void givenCostumerId_whenGetAccountByCustomer_thenAccountList() throws Exception {

        MvcResult result = mockMvc.perform(get("/account/customer/1")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Bearer " + accessToken))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].balance").value(100))
            .andExpect(jsonPath("$[0].openingDate").value(LocalDate.now().toString()))
            .andExpect(jsonPath("$[0].type").value("Company"))
            .andExpect(jsonPath("$[0].customerId").value(1))
            .andReturn();
    }

    @Test

    void givenCostumerId_whenGetAccountByCustomerNotExist_thenCustomerNotFoundException() throws Exception {
        Mockito.when(accountService.listarCuentasCliente(100L)).thenThrow(new CustomerNotfoundException("Customer not found"));
        MvcResult result = mockMvc.perform(get("/account/customer/100")
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .header("Authorization", "Bearer " + accessToken))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isNotFound())
            //.andExpect(jsonPath("$.message").value("Customer not found"))
            .andReturn();
        System.out.println(result.getResponse());
    }

    @Test
    void givenAccountIdAndCostumerId_whenObtenerCuentaPorId_thenOneAccount() throws Exception {
        Mockito.when(accountService.getByAccountIdAndCustomerId(1L, 1L))
                .thenReturn(accDto);
        MvcResult fakeaccount = mockMvc.perform(
                        get("/account/1/1")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                 .header("Authorization", "Bearer " + accessToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.id", is(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.balance", is(greaterThanOrEqualTo(400))))
//                    .andExpect(jsonPath("$.type", hasItem("Personal")))
                .andReturn();
    }

    @Test
    void givenAccountIdAndCostumerId_whenCostumerIdNotExist_thenCustomerNotFoundException() throws Exception {
        Mockito.when(accountService.getByAccountIdAndCustomerId(2L, 1L))
                .thenThrow(new AccountNotFoundException("Account not found"));

        MvcResult fakeaccount = mockMvc.perform(
                        get("/account/2/1")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                 .header("Authorization", "Bearer " + accessToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message").value("Account not found"))
                .andReturn();
    }

    @Test
    void givenAccount_whenCrearCuenta_thenAccountCreated() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        Mockito.when(accountService.crearCuenta(accountDtoRequest))
                .thenReturn(accDto);
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(accountDtoRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    void givenAccount_whenInvalidAccount_thenMethodArgumentNotValidException() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        accountDtoRequest.setBalance(-100);
        Mockito.when(accountService.crearCuenta(accountDtoRequest))
                .thenThrow(RuntimeException.class);
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(accountDtoRequest))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + accessToken)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAccountAndBalance_whenRestMoneyToBalance_thenTrue() {

        RestMoneyBalanceDto restMoneyBalanceDto = new RestMoneyBalanceDto(2000, 1L, 1L);
//        Mockito.when(accountService.restMoneyToBalance(restMoneyBalanceDto, true)).thenReturn(accDto);

    }

    @Test
    void givenAccountAndBalance_whenRestInsuficientMoneyToBalance_thenInsufficientBalanceException() {


    }
}