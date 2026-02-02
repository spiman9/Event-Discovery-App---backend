package com.example.EventDiscoveryApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
public class SecurityConfig {


    //whenever we need to add the security filter chain we need to create this bean
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable) // disabling csrf for postman testing purpose
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/events").hasRole("ADMIN") // admin only can post the events
                        .requestMatchers("/api/events/rsvp").hasAnyRole("USER" , "ADMIN") // any role can book tickets
                                .requestMatchers(HttpMethod.DELETE, "/api/events/**").hasRole("ADMIN") // admins only can delete
                        .anyRequest() // every request need to be authenticated
                        .authenticated()
                )

                .httpBasic(Customizer.withDefaults()); // using basic authentication

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // it is used for encoding the password
    }
}
