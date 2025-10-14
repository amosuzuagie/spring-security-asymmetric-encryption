package com.mstra.app.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequest {
    @NotBlank(message = "VALIDATION.PROFILE_UPDATE.FIRSTNAME.NOT_BLANK")
    @Size(min = 5, max = 50, message = "VALIDATION.PROFILE_UPDATE.FIRSTNAME.SIZE")
    @Pattern(regexp = "^\\p{Lu}[\\p{L}' -]*$", message = "VALIDATION.PROFILE_UPDATE.FIRSTNAME.PATTERN")
    @Schema(example = "Amos")
    private String firstName;

    @NotBlank(message = "VALIDATION.PROFILE_UPDATE.FIRSTNAME.NOT_BLANK")
    @Size(min = 5, max = 50, message = "VALIDATION.PROFILE_UPDATE.LASTNAME.SIZE")
    @Pattern(regexp = "^\\p{Lu}[\\p{L}' -]*$", message = "VALIDATION.PROFILE_UPDATE.LASTNAME.PATTERN")
    @Schema(example = "Amos")
    private String lastName;

    @Past(message = "VALIDATION.PROFILE_UPDATE.DATE_OF_BIRTH.PAST")
    private LocalDate dateOfBirth;
}
