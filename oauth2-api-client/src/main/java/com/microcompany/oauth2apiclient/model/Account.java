package com.microcompany.oauth2apiclient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    private Long id;
    private String type;
    private LocalDate openingDate;
    private int balance;
    private Long customerId;
}