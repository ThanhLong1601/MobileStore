package com.ex.mobilestore.controller;

import com.ex.mobilestore.dto.JwtDto;
import com.ex.mobilestore.dto.UserDto;
import com.ex.mobilestore.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    // Response: ResponseEntity<>
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.register(userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody UserDto loginDto) {
        return ResponseEntity.ok(userService.login(loginDto));
    }
}
