package ru.adel.tasktracker.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.adel.tasktracker.dto.AuthenticationRequest;
import ru.adel.tasktracker.dto.AuthenticationResponse;
import ru.adel.tasktracker.dto.UserRequest;
import ru.adel.tasktracker.exception.UserAlreadyExistException;
import ru.adel.tasktracker.model.User;
import ru.adel.tasktracker.model.enums.Role;
import ru.adel.tasktracker.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent())
            throw new UserAlreadyExistException(String.format("User %s already exists!",registerRequest.getEmail()));

        var user = User.builder()
                .firstName(registerRequest.getFirstname())
                .lastName(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .active(true)
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }
        var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(()->new UsernameNotFoundException(String.format("User %s not found",authenticationRequest.getEmail())));
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
