package com.server.app.components.mapper;

import com.server.app.dto.auth.LoginResponseDTO;
import com.server.app.dto.auth.UpdatePasswordRequest;
import com.server.app.dto.auth.UpdateProfileRequest;
import com.server.app.dto.response.AuthResponse;
import com.server.app.dto.user.UserUpdateDto;
import com.server.app.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public AuthResponse toAuthResponse(User user, String jwt) {
        if (user == null) return null;

        return AuthResponse.builder()
                .jwt(jwt)
                .data(user)
                .build();
    }

    public LoginResponseDTO toLoginResponseDTO(User user) {
        return LoginResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public UserUpdateDto toUserUpdateDto(UpdateProfileRequest request){
        return new UserUpdateDto(
                request.getUsername(),
                request.getName(),
                request.getSurname(),
                request.getEmail(),
                null, null
        );
    }
}