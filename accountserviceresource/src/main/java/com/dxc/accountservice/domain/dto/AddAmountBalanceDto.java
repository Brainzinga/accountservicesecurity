package com.dxc.accountservice.domain.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class AddAmountBalanceDto {
    @NotNull
    @Positive
    private Integer amount;
    @NotNull
    @Positive
    private Long accountId;
    @NotNull
    @Positive
    private Long customerId;
}
