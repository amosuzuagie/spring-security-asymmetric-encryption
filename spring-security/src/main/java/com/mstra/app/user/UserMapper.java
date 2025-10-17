package com.mstra.app.user;

import com.mstra.app.auth.request.RegistrationRequest;
import com.mstra.app.user.request.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public void mergeUserInfo(User user, ProfileUpdateRequest request) {
        if (StringUtils.isNoneBlank(request.getFirstName()) && !user.getFirstName().equals(request.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }
        if (StringUtils.isNoneBlank(request.getLastName()) && !user.getLastName().equals(request.getLastName())) {
            user.setLastName(request.getLastName());
        }
        if (request.getDateOfBirth() != null && !request.getDateOfBirth().equals(user.getDateOfBirth())) {
            user.setDateOfBirth(request.getDateOfBirth());
        }
    }

    public User toUser(RegistrationRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .locked(false)
                .credentialExpired(false)
                .emailVerified(false)
                .phoneVerified(false)
                .build();
    }
}
