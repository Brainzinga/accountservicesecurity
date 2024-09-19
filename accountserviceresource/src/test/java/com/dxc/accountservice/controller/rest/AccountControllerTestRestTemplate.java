package com.dxc.accountservice.controller.rest;

import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.domain.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestClientException;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTestRestTemplate {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @MockBean
    private AccountService accountService;

    @Test
    void givenCostumerId_whenGetAccountByCustomer_thenAccountList() {

    }
    @Test
    void givenCostumerId_whenGetAccountByCustomerNotExist_thenCustomerNotFoundException()
    {
    }

    @Test
    void givenAccountIdAndCostumerId_whenObtenerCuentaPorId_thenOneAccount() throws Exception{
        AccountDtoResponse mockResponse = AccountDtoResponse.builder().id(1L).customerId(1L).type("Personal").openingDate(LocalDate.now()).balance(400).build();
        Mockito.when(accountService.getByAccountIdAndCustomerId(1L,1L)).thenReturn(mockResponse);

        ResponseEntity<AccountDtoResponse> response = restTemplate.getForEntity("http://localhost:" + port + "/account/1/1", AccountDtoResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void givenAccountIdAndCostumerId_whenCostumerIdNotExist_thenRestClientException() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        try{
            restTemplate.getForEntity("http://localhost:" + port + "/account/1/50", AccountDtoResponse.class);
        }
        catch(RestClientException e){
            assertThat(e.getMessage()).contains("Unrecognized token 'Customer'");
        }
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