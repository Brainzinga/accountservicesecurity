package com.dxc.accountservice.domain.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDtoRequest {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
}
