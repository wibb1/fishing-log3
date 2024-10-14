package com.fishingLog.config;

import com.fishingLog.spring.model.Angler;
import com.fishingLog.spring.repository.AnglerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AnglerRepository anglerRepository;

    public SecurityConfig(AnglerRepository anglerRepository) {
        this.anglerRepository = anglerRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        final UserDetails[] userDetails = {null};
        return username -> {
            Optional<Angler> angler = anglerRepository.findByUsername(username);
            if (angler == null) {
                throw new UsernameNotFoundException("Angler not found");
            }
            angler.ifPresent(user -> userDetails[0] = org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getEncryptedPassword())
                        .roles(user.getRole())
                        .build());

            return userDetails[0];
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/angler/**").hasRole("ANGLER")
                    .requestMatchers("/", "/home").permitAll()
                    .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .permitAll()
                );

        return http.build();
    }
}
