package com.example.quizapi.service.auth;

import com.example.quizapi.domain.User;
import com.example.quizapi.service.user.UserService;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User registerUser(User user) throws Exception {
        return userService.create(user);
    }

    @Override
    public User profile(Principal principal) {
        return userService.findByEmail(principal.getName());
    }
}
