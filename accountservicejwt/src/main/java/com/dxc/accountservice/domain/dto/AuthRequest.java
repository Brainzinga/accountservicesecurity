package com.dxc.accountservice.domain.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class AuthRequest {
    @NotNull 
    private String username;

    @NotNull @Length(min = 5, max = 10)
    private String password;
}