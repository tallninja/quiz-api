package com.example.quizapi.controller;

import com.example.quizapi.domain.User;
import com.example.quizapi.dto.user.CreateUserDto;
import com.example.quizapi.dto.user.GetUserDto;
import com.example.quizapi.dto.user.UpdateUserDto;
import com.example.quizapi.mappers.UserMapper;
import com.example.quizapi.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<Page<GetUserDto>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "DESC", name = "sort_direction") String sortDirection
    ) {
        List<GetUserDto> users = userService.findAll(page, size, sort, sortDirection)
                .stream()
                .map(userMapper::userToGetUserDto)
                .toList();
        return ResponseEntity.ok(new PageImpl<>(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> findUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        GetUserDto getUserDto = userMapper.userToGetUserDto(user);
        return ResponseEntity.ok(getUserDto);
    }

    @PostMapping
    public ResponseEntity<GetUserDto> createUser(
            HttpServletRequest request,
            @Valid @RequestBody CreateUserDto createUserDto
    ) throws Exception {
        User user = userMapper.createUserDtoToUser(createUserDto);
        User _user = userService.create(user);
        GetUserDto getUserDto = userMapper.userToGetUserDto(_user);
        URI location = new URI(request.getRequestURL() + "/" + _user.getId());
        return ResponseEntity.created(location).body(getUserDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserDto updateUserDto
    ) {
        User user = userMapper.updateUserDtoToUser(updateUserDto);
        User _user = userService.update(id, user);
        GetUserDto getUserDto = userMapper.userToGetUserDto(_user);
        return ResponseEntity.ok(getUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GetUserDto> deleteUser(@PathVariable Long id) {
        User user = userService.delete(id);
        GetUserDto getUserDto = userMapper.userToGetUserDto(user);
        return ResponseEntity.ok(getUserDto);
    }
}
