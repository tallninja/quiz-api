package com.example.quizapi.controller;

import com.example.quizapi.domain.User;
import com.example.quizapi.dto.auth.RegisterUserDto;
import com.example.quizapi.dto.user.CreateUserDto;
import com.example.quizapi.dto.user.GetUserDto;
import com.example.quizapi.mappers.UserMapper;
import com.example.quizapi.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    @PostMapping("/register")
    public ResponseEntity<GetUserDto> registerNewUser(
            @Valid @RequestBody RegisterUserDto registerUserDto
    ) throws Exception {
        User user = userMapper.registerUserDtoToUser(registerUserDto);
        User _user = authService.registerUser(user);
        GetUserDto getUserDto = userMapper.userToGetUserDto(_user);
        return ResponseEntity.created(new URI("")).body(getUserDto);
    }

    @GetMapping("/profile")
    public ResponseEntity<GetUserDto> getUserProfile(Principal principal) {
        User user = authService.profile(principal);
        GetUserDto getUserDto = userMapper.userToGetUserDto(user);
        return ResponseEntity.ok(getUserDto);
    }
}
