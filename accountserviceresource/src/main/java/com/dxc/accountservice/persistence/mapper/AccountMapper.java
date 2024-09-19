package com.dxc.accountservice.persistence.mapper;

import com.dxc.accountservice.domain.dto.AccountDtoRequest;
import com.dxc.accountservice.domain.dto.AccountDtoResponse;
import com.dxc.accountservice.persistence.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    @Mapping(source = "customer.id", target = "customerId"  )
    AccountDtoResponse toAccountDtoResponse(Account account);
    Account toAccount(AccountDtoRequest accountDtoRequest);

    List<AccountDtoResponse> toAccountDtoResponseList(List<Account> accounts);

}
