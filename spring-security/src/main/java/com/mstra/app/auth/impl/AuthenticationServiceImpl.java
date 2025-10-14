package com.mstra.app.auth.impl;

import com.mstra.app.auth.AuthenticationService;
import com.mstra.app.auth.request.AuthenticationRequest;
import com.mstra.app.auth.request.RefreshRequest;
import com.mstra.app.auth.request.RegistrationRequest;
import com.mstra.app.auth.response.AuthenticationResponse;
import com.mstra.app.exception.BusinessException;
import com.mstra.app.exception.ErrorCode;
import com.mstra.app.role.Role;
import com.mstra.app.role.RoleRepository;
import com.mstra.app.security.JwtService;
import com.mstra.app.user.User;
import com.mstra.app.user.UserMapper;
import com.mstra.app.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        final Authentication auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        final User user = (User) auth.getPrincipal();
        final String token = this.jwtService.generateAccessToken(user.getUsername());
        final String refreshToken = this.jwtService.generateRefreshToken(user.getUsername());

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        checkUserEmail(request.getEmail());
        checkUserPhoneNumber(request.getPhoneNumber());
        checkUserPassword(request.getPassword(), request.getConfirmPassword());

        final Role userRole = this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role USER does not exist"));

        final List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        final User user = this.userMapper.toUser(request);
        user.setRoles(roles);
        log.debug("Saving user {}", user);
        this.userRepository.save(user);

        final List<User> users = new ArrayList<>();
        users.add(user);
        userRole.setUsers(users);
        this.roleRepository.save(userRole);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        final String newAccessToken = this.jwtService.refreshAccessToken(request.getRefreshToken());
        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType("Bearer")
                .build();
    }

    private void checkUserEmail(String email) {
        final boolean emailExists = this.userRepository.existByEmailIgnoreCase(email);
        if (emailExists) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkUserPhoneNumber(String phoneNumber) {
        final boolean phoneNumberExists = this.userRepository.existByPhoneNumber(phoneNumber);
        if (phoneNumberExists) {
            throw new BusinessException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }
    }

    private void checkUserPassword(String password, String confirmPassword) {
        if (password == null || !password.equals(confirmPassword)) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }
    }
}
