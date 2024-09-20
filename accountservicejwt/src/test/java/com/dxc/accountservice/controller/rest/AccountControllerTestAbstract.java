package com.dxc.accountservice.controller.rest;

import com.dxc.accountservice.domain.dto.AuthRequest;
import com.dxc.accountservice.domain.dto.AuthResponse;
import com.dxc.accountservice.domain.service.AccountService;
import com.dxc.accountservice.persistence.entity.RoleEnum;
import com.dxc.accountservice.persistence.entity.User;
import com.dxc.accountservice.util.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public abstract class AccountControllerTestAbstract {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountService accountService;

    private String username = "user";
    private String password = "password";
    protected String accessToken = null;

    @BeforeAll
    public void SetUpUser() {
        // Create user
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String enc_password = passwordEncoder.encode(password);

        User aUser = new User(null, username, enc_password, RoleEnum.Cashier);

    }

    @BeforeEach
    public void setUp() throws Exception {


        // Get Token
        AuthRequest authRequest = new AuthRequest(username, password);

        MvcResult result = mvc.perform(post("/auth/login")
                        .content(JsonUtil.asJsonString(authRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        AuthResponse response = new ObjectMapper().readValue(contentAsString, AuthResponse.class);

        System.out.println("JWT Tokem: " + response.toString());

        accessToken = response.getAccessToken();
        System.out.println(accessToken);
    }


}
