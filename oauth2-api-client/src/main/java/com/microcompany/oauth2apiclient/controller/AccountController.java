package com.microcompany.oauth2apiclient.controller;

import com.microcompany.oauth2apiclient.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@RestController
public class AccountController {
    @Autowired
    private WebClient webClient;

    @GetMapping(value = "/account-view")
    public List<Account> getProducts(
            @RegisteredOAuth2AuthorizedClient("account-client-authorization-code") OAuth2AuthorizedClient authorizedClient
    ) {
        return this.webClient
                .get()
                .uri("http://localhost:9900/account")
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Account>>() {
                })
                .block();
    }
}