package com.fishingLog.spring.security;

import com.fishingLog.spring.repository.AnglerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AnglerRepository anglerRepository;

    public SecurityConfig(AnglerRepository anglerRepository) {
        this.anglerRepository = anglerRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var angler = anglerRepository.findByUsername(username);
            if (angler.isEmpty()) {
                throw new IllegalArgumentException("User " + username + "not found");
            }
            return new AnglerUserDetails(angler.get());
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/angler/**").hasRole("ANGLER")
                        .requestMatchers("/hello").hasAnyRole("ANGLER", "ADMIN")
                        .requestMatchers("/", "/home", "/login", "/knockKnock", "/signup").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }
}
