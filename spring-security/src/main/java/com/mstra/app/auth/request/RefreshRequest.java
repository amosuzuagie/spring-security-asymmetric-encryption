package com.mstra.app.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequest {
    @NotBlank(message = "VALIDATION.REFRESH.TOKEN.NOT_BLANK")
    private String refreshToken;
}
