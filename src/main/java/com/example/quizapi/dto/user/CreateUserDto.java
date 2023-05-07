package com.example.quizapi.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
