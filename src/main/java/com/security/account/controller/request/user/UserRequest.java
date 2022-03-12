package com.security.account.controller.request.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRequest {
    @Email(message = "{validation.invalid.email}")
    @NotBlank(message = "{validation.blank}")
    private String email;

    @NotBlank(message = "{validation.blank}")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$",
            message = "{validation.invalid.password}"
    )
    private String password;

    @NotBlank(message = "{validation.blank}")
    private String nameClient;
}
