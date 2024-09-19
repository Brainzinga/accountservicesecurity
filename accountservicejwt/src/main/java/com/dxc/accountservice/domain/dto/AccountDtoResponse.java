package com.dxc.accountservice.domain.dto;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountDtoResponse {
    private Long id;
    private String type;
    private LocalDate openingDate;
    private int balance;
    private Long customerId;
}
