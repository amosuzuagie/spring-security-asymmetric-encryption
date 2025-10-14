package com.mstra.app.auth;

import com.mstra.app.auth.request.AuthenticationRequest;
import com.mstra.app.auth.request.RefreshRequest;
import com.mstra.app.auth.request.RegistrationRequest;
import com.mstra.app.auth.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);

    void register(RegistrationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest request);
}
