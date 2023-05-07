package com.example.quizapi.service.userdetails;

import com.example.quizapi.domain.SecurityUser;
import com.example.quizapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .map(SecurityUser::new)
                .orElseThrow(() -> {
                    log.error("User with email " + username + " was not found");
                    return new UsernameNotFoundException("User with email " + username + " was not found");
                });
    }
}
