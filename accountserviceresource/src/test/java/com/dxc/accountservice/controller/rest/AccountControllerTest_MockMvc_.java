package com.dxc.accountservice.controller.rest;

import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.domain.service.AccountService;
import com.dxc.accountservice.persistence.entity.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles("testing")
@Sql(value = "classpath:data_testing.sql")
class AccountControllerTest_MockMvc_ {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Test
    void givenCostumerId_whenGetAccountByCustomer_thenAccountList() throws Exception {
        MvcResult result = mockMvc.perform(get("/account/customer/1")
                    .accept(MediaType.APPLICATION_JSON_VALUE))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$[0].id",is(greaterThanOrEqualTo(0))))
            /*.andExpect(jsonPath("$[0].balance").value(100))
            .andExpect(jsonPath("$[0].openingDate").value(LocalDate.now().toString()))
            .andExpect(jsonPath("$[0].type").value("Company"))
            .andExpect(jsonPath("$[0].customerId").value(1))*/
            .andReturn();
    }
    @Test
    void givenCostumerId_whenGetAccountByCustomerNotExist_thenCustomerNotFoundException()
    {
    }

    @Test
    void givenAccountIdAndCostumerId_whenObtenerCuentaPorId_thenOneAccount() throws Exception{
        Account account = Account.builder().id(1L).build();

        MvcResult fakeaccount = mockMvc.perform(
                get("/account/1/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(jsonPath("$.type").exists())
                    .andExpect(jsonPath("$.id", is(greaterThanOrEqualTo(1))))
                    .andExpect(jsonPath("$.balance", is(greaterThanOrEqualTo(400))))
                    .andReturn();
    }

    @Test
    void givenAccountIdAndCostumerId_whenCostumerIdNotExist_thenCustomerNotFoundException() throws Exception{
                    MvcResult fakeaccount = mockMvc.perform(
                        get("/account/2/9")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                    )
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Customer not found with id: 9"))
                    .andReturn();
    }

    @Test
    void givenAccount_whenCrearCuenta_thenAccountCreated() {
    }

    @Test
    void givenAccount_whenInvalidAccount_thenMethodArgumentNotValidException() {
    }

    @Test
    void givenAccountAndBalance_whenRestMoneyToBalance_thenTrue() {
    }

    @Test
    void givenAccountAndBalance_whenRestInsuficientMoneyToBalance_thenInsufficientBalanceException() {
    }
}