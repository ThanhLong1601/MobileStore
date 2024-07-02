package com.ex.mobilestore.mapper;

import com.ex.mobilestore.dto.UserDto;
import com.ex.mobilestore.entity.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto toUserDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return modelMapper.map(userEntity, UserDto.class);
    }
    public UserEntity toUserEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        return modelMapper.map(userDto, UserEntity.class);
    }
}
