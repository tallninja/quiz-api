package com.example.quizapi.controller;

import com.example.quizapi.domain.User;
import com.example.quizapi.dto.user.GetUserDto;
import com.example.quizapi.mappers.UserMapper;
import com.example.quizapi.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @GetMapping("/profile")
    public ResponseEntity<GetUserDto> getUserProfile(Principal principal) {
        User user = authService.profile(principal);
        GetUserDto getUserDto = userMapper.userToGetUserDto(user);
        return ResponseEntity.ok(getUserDto);
    }
}
