package com.example.quizapi.service.user;

import com.example.quizapi.domain.User;
import com.example.quizapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Page<User> findAll(int page, int size, String sort, String sortDirection) {
        PageRequest pageRequest = PageRequest
                .of(page, size, Sort.Direction.fromString(sortDirection), sort);
        return userRepository.findAll(pageRequest);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.error("User with id " + id + " was not found.");
            return new NoSuchElementException("User with id " + id + " was not found.");
        });
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("User with email " + email + " was not found.");
            return new NoSuchElementException("User with email " + email + " was not found.");
        });
    }

    @Override
    public User create(User user) throws Exception {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent())
            throw new Exception("User with email " + user.getEmail() + " already exists.");

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User _user = this.findById(id);
        _user.setFirstName(user.getFirstName());
        _user.setLastName(user.getLastName());
        _user.setEmail(user.getEmail());
        return userRepository.save(_user);
    }

    @Override
    public User delete(Long id) {
        User _user = this.findById(id);
        userRepository.delete(_user);
        return _user;
    }
}
