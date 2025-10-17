package com.mstra.app.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "VALIDATION.CHANGE_PASSWORD.PASSWORD.NOT_BLANK")
    @Size(min = 8, max = 50, message = "VALIDATION.CHANGE_PASSWORD.PASSWORD.SIZE")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.\\w).*", message = "VALIDATION.CHANGE_PASSWORD.PASSWORD.WEEK")
    @Schema(example = "PassW0rd#")
    private String currentPassword;

    @NotBlank(message = "VALIDATION.CHANGE_PASSWORD.PASSWORD.NOT_BLANK")
    @Size(min = 8, max = 50, message = "VALIDATION.CHANGE_PASSWORD.PASSWORD.SIZE")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.\\w).*", message = "VALIDATION.CHANGE_PASSWORD.PASSWORD.WEEK")
    @Schema(example = "PassW0rd#")
    private String newPassword;

    @NotBlank(message = "VALIDATION.CHANGE_PASSWORD.PASSWORD.NOT_BLANK")
    @Size(min = 8, max = 50, message = "VALIDATION.CHANGE_PASSWORD.PASSWORD.SIZE")
    @Schema(example = "PassW0rd#")
    private String confirmNewPassword;
}
