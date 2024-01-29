package com.nhg.account.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAccountDto {

    @NotEmpty(message = "Username cannot be empty.")
    @Size(min = 4, message = "Username require a length of minimum {min}")
    private String username;

    @NotEmpty(message = "Email cannot be empty.")
    @Email(message = "Email must be a well-formed email address.")
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$",
            message = "Password require: minimum 6 characters, at least one uppercase letter, " +
                      "one lowercase letter, one number and one special character.")
    private String password;
}
