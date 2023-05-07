package com.example.quizapi.service.auth;

import com.example.quizapi.domain.User;

import java.security.Principal;

public interface AuthService {
    User registerUser(User user) throws Exception;

    User profile(Principal principal);
}
