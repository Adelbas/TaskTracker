package ru.adel.tasktracker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.adel.tasktracker.model.User;
import ru.adel.tasktracker.repository.UserRepository;
import ru.adel.tasktracker.security.JwtService;
import static ru.adel.tasktracker.model.enums.Role.ADMIN;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    @Value("${default.admin.username}")
    private String adminUsername;
    @Value("${default.admin.password}")
    private String adminPassword;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner createAdmin(){
        return args -> {
            var admin = userRepository.findByEmail(adminUsername);
            User newAdmin = null;
            if (admin.isPresent()) {
                admin.get().setEmail(adminUsername);
                admin.get().setPassword(passwordEncoder().encode(adminPassword));
            } else {
                newAdmin = User.builder()
                        .firstName("admin")
                        .lastName("admin")
                        .email(adminUsername)
                        .password(passwordEncoder().encode(adminPassword))
                        .active(true)
                        .role(ADMIN)
                        .build();
            }
            System.out.println("Admin token: "+jwtService.generateToken(userRepository.save(admin.orElse(newAdmin))));
        };
    }
}
