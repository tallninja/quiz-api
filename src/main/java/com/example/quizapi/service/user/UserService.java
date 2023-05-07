package com.example.quizapi.service.user;

import com.example.quizapi.domain.User;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<User> findAll(int page, int size, String sort, String sortDirection);
    User findById(Long id);
    User findByEmail(String email);
    User create(User user) throws Exception;
    User update(Long id, User user);
    User delete(Long id);
}
