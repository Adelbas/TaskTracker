package ru.adel.tasktracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationRequest {
    @Email(message = "Bad email address!")
    @NotBlank(message = "Empty email!")
    private String email;
    @NotBlank(message = "Empty password!")
    private String password;
}
