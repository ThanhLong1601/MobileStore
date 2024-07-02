package com.ex.mobilestore.mapper;

import com.ex.mobilestore.dto.ManufactureDto;
import com.ex.mobilestore.entity.ManufactureEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManufactureMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public ManufactureMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ManufactureDto toManufactureDto(ManufactureEntity manufactureEntity) {
        return modelMapper.map(manufactureEntity, ManufactureDto.class);
    }

    public ManufactureEntity toManufactureEntity(ManufactureDto manufactureDto) {
        return modelMapper.map(manufactureDto, ManufactureEntity.class);
    }
}
