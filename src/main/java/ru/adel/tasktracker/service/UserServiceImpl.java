package ru.adel.tasktracker.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.adel.tasktracker.dto.UserRequest;
import ru.adel.tasktracker.exception.UserAlreadyExistException;
import ru.adel.tasktracker.exception.UserNotFoundException;
import ru.adel.tasktracker.model.User;
import ru.adel.tasktracker.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getCurrentUser(){
        return getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(
                ()->new UserNotFoundException("User not found with id "+id)
        );
    }

    @Override
    public User getUser(String email){
        return (User) userDetailsService.loadUserByUsername(email);
    }

    @Override
    public User disableUser(Long id) {
        User user = getUser(id);
        user.setActive(false);
        return userRepository.save(user);
    }

    @Override
    public User enableUser(Long id) {
        User user = getUser(id);
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        User user = getUser(id);
        userRepository.deleteById(id);
    }

    @Override
    public User updateCurrentUser(UserRequest userRequest) {
        User existedUser = getCurrentUser();

        if (!existedUser.getEmail().equals(userRequest.getEmail()) && userRepository.findByEmail(userRequest.getEmail()).isPresent())
            throw new UserAlreadyExistException(String.format("User %s already exists!",userRequest.getEmail()));

        existedUser.setEmail(userRequest.getEmail());
        existedUser.setFirstName(userRequest.getFirstname());
        existedUser.setLastName(userRequest.getLastname());
        existedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return userRepository.save(existedUser);
    }
}
