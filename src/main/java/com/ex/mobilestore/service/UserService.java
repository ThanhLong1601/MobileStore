package com.ex.mobilestore.service;

import com.ex.mobilestore.dto.JwtDto;
import com.ex.mobilestore.dto.UserDto;

public interface UserService {
    UserDto register(UserDto userDto);
    JwtDto login(UserDto userDto);
}
