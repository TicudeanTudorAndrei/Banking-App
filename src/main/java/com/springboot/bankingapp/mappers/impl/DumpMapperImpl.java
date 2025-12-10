package com.springboot.bankingapp.mappers.impl;

import com.springboot.bankingapp.domain.dto.DumpDto;
import com.springboot.bankingapp.domain.entities.DumpEntity;
import com.springboot.bankingapp.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DumpMapperImpl implements Mapper<DumpEntity, DumpDto> {

    private final ModelMapper modelMapper;

    @Override
    public DumpDto mapTo(DumpEntity dumpEntity) {
        return modelMapper.map(dumpEntity, DumpDto.class);
    }

    @Override
    public DumpEntity mapFrom(DumpDto dumpDto) {
        return modelMapper.map(dumpDto, DumpEntity.class);
    }
}
