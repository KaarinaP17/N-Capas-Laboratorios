package com.server.app.controllers;

import com.server.app.components.mapper.UserMapper;
import com.server.app.config.JsonWebToken;
import com.server.app.dto.auth.*;
import com.server.app.dto.response.AuthResponse;
import com.server.app.entities.User;
import com.server.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JsonWebToken jwtUtil;
    private final UserMapper userMapper;

    public AuthController(UserService userService, JsonWebToken jwtUtil, UserMapper userMapper) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = userService.login(request.getUsername(), request.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtUtil.createToken(user);

        AuthResponse response = userMapper.toAuthResponse(user, token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request) {
        User user = userService.signUp(request);

        String token = jwtUtil.createToken(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.toAuthResponse(user, token));
    }

    @GetMapping("/profile")
    public ResponseEntity<LoginResponseDTO> getProfile() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LoginResponseDTO profileDto = userMapper.toLoginResponseDTO(currentUser);

        return ResponseEntity.ok(profileDto);
    }

    @PutMapping("/update/profile")
    public ResponseEntity<AuthResponse> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User updatedUser = userService.updateUser(currentUser.getId(), userMapper.toUserUpdateDto(request));

        String newToken = jwtUtil.createToken(updatedUser);

        return ResponseEntity.ok(userMapper.toAuthResponse(updatedUser, newToken));
    }

    @PutMapping("/update/password")
    public ResponseEntity<LoginResponseDTO> updatePassword(@RequestBody UpdatePasswordRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User updatedData = userService.updatePassword(currentUser.getId(), request);

        return ResponseEntity.ok(userMapper.toLoginResponseDTO(updatedData));
    }
}
