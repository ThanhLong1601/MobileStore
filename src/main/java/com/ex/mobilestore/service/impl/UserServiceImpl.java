package com.ex.mobilestore.service.impl;

import com.ex.mobilestore.dto.JwtDto;
import com.ex.mobilestore.dto.UserDto;
import com.ex.mobilestore.entity.UserEntity;
import com.ex.mobilestore.exception.AlreadyExistsException;
import com.ex.mobilestore.exception.InvalidCredentialsException;
import com.ex.mobilestore.repository.RoleRepository;
import com.ex.mobilestore.repository.UserRepository;
import com.ex.mobilestore.security.CustomUserDetails;
import com.ex.mobilestore.security.JwtTokenProvider;
import com.ex.mobilestore.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDto register(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new AlreadyExistsException("User", "username", userDto.getUsername());
        }

        // create user
        UserEntity entity = new UserEntity();
        entity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        entity.setUsername(userDto.getUsername());
        entity.setRoles(roleRepository.findAllByRoleNameIn(userDto.getRoles()));

        UserEntity savedUser = userRepository.save(entity);
        userDto.setId(savedUser.getId());

        return userDto;

    }

    public JwtDto login(UserDto userDto) {
        try {
            // Perform authentication
            // 1. pass encoder hash thong password truyen vao
            // 2. userDetail service lay thong tin tu trong db theo username
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

            // Retrieve user details from the authenticated token
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // Generate JWT token
            String accessToken = jwtTokenProvider.generateToken(userDetails);
            Date expriedDate = jwtTokenProvider.extractExpiration(accessToken);

            return JwtDto.builder()
                    .accessToken(accessToken)
                    .expiresIn(expriedDate)
                    .build();
        } catch (AuthenticationException e) {
            // Handle authentication failure
            log.error("Wrong username or password {}", e.getMessage(), e);
            throw new InvalidCredentialsException("Wrong username or password");
        }

    }
}
