package com.mstra.app.auth.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
}
