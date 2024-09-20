package com.dxc.accountservice.controller.rest;

import com.dxc.accountservice.domain.dto.AccountDtoRequest;
import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.domain.service.AccountService;
import com.dxc.accountservice.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestClientException;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("testing")
@Sql(value = "classpath:data_testing.sql")
class AccountControllerTestRestTemplate extends AccountControllerTestAbstract {

//    @LocalServerPort
//    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
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
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<AccountDtoResponse> response = restTemplate.exchange(
        "/account/1/1", HttpMethod.GET, requestEntity, AccountDtoResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void givenAccountIdAndCostumerId_whenCostumerIdNotExist_thenRestClientException() throws Exception{
           HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        try{
            restTemplate.exchange("/account/1/50",HttpMethod.GET,requestEntity, AccountDtoResponse.class);
        }
        catch(RestClientException e){
            assertThat(e.getMessage()).contains("Unrecognized token 'Customer'");
        }
    }

    @Test
    void givenAccount_whenCrearCuenta_thenAccountCreated() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

        ObjectMapper mapper = new ObjectMapper();
        AccountDtoRequest dto = AccountDtoRequest.builder().balance(400).customerId(1L).openingDate(
                LocalDate.now()).type("Personal").build();
        mapper.registerModule(new JavaTimeModule());

        HttpEntity<String> request = new HttpEntity<String>(mapper.writeValueAsString(dto), headers);

        ResponseEntity<AccountDtoResponse> response = restTemplate.postForEntity(
        "/account",request, AccountDtoResponse.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
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