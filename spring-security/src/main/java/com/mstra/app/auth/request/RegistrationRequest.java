package com.mstra.app.auth.request;

import com.mstra.app.validation.NonDisposableEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    @NotBlank(message = "VALIDATION.REGISTRATION.FIRSTNAME.NOT_BLANK")
    @Size(min = 3, max = 50, message = "VALIDATION.REGISTRATION.FIRSTNAME.SIZE")
    @Pattern(regexp = "^\\p{Lu}[\\p{L}' -]*$", message = "VALIDATION.REGISTRATION.FIRSTNAME.PATTERN")
    @Schema(example = "John")
    private String firstName;

    @NotBlank(message = "VALIDATION.REGISTRATION.LASTNAME.NOT_BLANK")
    @Size(min = 3, max = 50, message = "VALIDATION.REGISTRATION.LASTNAME.SIZE")
    @Pattern(regexp = "^\\p{Lu}[\\p{L}' -]*$", message = "VALIDATION.REGISTRATION.LASTNAME.PATTERN")
    @Schema(example = "Doe")
    private String lastName;

    @NotBlank(message = "VALIDATION.REGISTRATION.EMAIL.NOT_BLANK")
    @Email(message = "VALIDATION.REGISTRATION.EMAIL.FORMAT")
    @NonDisposableEmail(message = "VALIDATION.REGISTRATION.EMAIL.DISPOSABLE")
    @Schema(example = "johndoe@gmail.com")
    private String email;

    @NotBlank(message = "VALIDATION.REGISTRATION.PHONE.NOT_BLANK")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "VALIDATION.REGISTRATION.PHONE.FORMAT")
    @Schema(example = "+2341234567890")
    private String phoneNumber;

    @NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.NOT_BLANK")
    @Size(min = 8, max = 50, message = "VALIDATION.REGISTRATION.PASSWORD.SIZE")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.\\w).*", message = "VALIDATION.REGISTRATION.PASSWORD.WEEK")
    @Schema(example = "PassW0rd#")
    private String password;

    @NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.NOT_BLANK")
    @Size(min = 8, max = 50, message = "VALIDATION.REGISTRATION.PASSWORD.SIZE")
    @Schema(example = "PassW0rd#")
    private String confirmPassword;
}
