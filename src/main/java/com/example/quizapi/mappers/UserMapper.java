package com.example.quizapi.mappers;

import com.example.quizapi.domain.User;
import com.example.quizapi.dto.auth.RegisterUserDto;
import com.example.quizapi.dto.user.CreateUserDto;
import com.example.quizapi.dto.user.GetUserDto;
import com.example.quizapi.dto.user.UpdateUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User createUserDtoToUser(CreateUserDto createUserDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User registerUserDtoToUser(RegisterUserDto registerUserDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User updateUserDtoToUser(UpdateUserDto updateUserDto);

    GetUserDto userToGetUserDto(User user);
}
