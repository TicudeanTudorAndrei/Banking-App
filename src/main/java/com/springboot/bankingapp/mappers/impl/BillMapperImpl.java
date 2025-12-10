package com.springboot.bankingapp.mappers.impl;

import com.springboot.bankingapp.domain.dto.BillDto;
import com.springboot.bankingapp.domain.entities.BillEntity;
import com.springboot.bankingapp.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BillMapperImpl implements Mapper<BillEntity, BillDto> {

    private final ModelMapper modelMapper;

    @Override
    public BillDto mapTo(BillEntity billEntity) {
        return modelMapper.map(billEntity, BillDto.class);
    }

    @Override
    public BillEntity mapFrom(BillDto billDto) {
        return modelMapper.map(billDto, BillEntity.class);
    }
}
