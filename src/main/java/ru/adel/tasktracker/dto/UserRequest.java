package ru.adel.tasktracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "Empty firstname!")
    private String firstname;
    @NotBlank(message = "Empty lastname!")
    private String lastname;
    @Email(message = "Bad email address!")
    @NotBlank(message = "Empty email!")
    private String email;
    @NotBlank(message = "Empty password!")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15})",
            message = "Password should be 8 to 15 characters with at least one numeric, one small case and one upper-case letter!")
    private String password;
}
