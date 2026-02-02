package com.example.EventDiscoveryApp.config;

import com.example.EventDiscoveryApp.entity.User;
import com.example.EventDiscoveryApp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner loadInitialUsers() {
        return args -> {

            // ✅ Run only once
            if (userRepository.count() > 0) {
                return;
            }

            List<User> users = new ArrayList<>();

            // ✅ ADMINS
            users.add(new User(
                    null,
                    "admin1",
                    passwordEncoder.encode("admin123"),
                    "ROLE_ADMIN"
            ));

            users.add(new User(
                    null,
                    "admin2",
                    passwordEncoder.encode("admin123"),
                    "ROLE_ADMIN"
            ));

            // ✅ USERS
            for (int i = 1; i <= 8; i++) {
                users.add(new User(
                        null,
                        "user" + i,
                        passwordEncoder.encode("user123"),
                        "ROLE_USER"
                ));
            }

            userRepository.saveAll(users);
        };
    }
}
