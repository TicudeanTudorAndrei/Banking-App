package com.springboot.bankingapp.mappers.impl;

import com.springboot.bankingapp.domain.dto.AccountDto;
import com.springboot.bankingapp.domain.entities.AccountEntity;
import com.springboot.bankingapp.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapperImpl implements Mapper<AccountEntity, AccountDto> {

    private final ModelMapper modelMapper;

    @Override
    public AccountDto mapTo(AccountEntity accountEntity) {
        return modelMapper.map(accountEntity, AccountDto.class);
    }

    @Override
    public AccountEntity mapFrom(AccountDto accountDto) {
        return modelMapper.map(accountDto, AccountEntity.class);
    }
}
