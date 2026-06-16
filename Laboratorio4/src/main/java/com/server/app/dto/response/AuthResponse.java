package com.server.app.dto.response;

import com.server.app.dto.auth.LoginResponseDTO;
import com.server.app.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String jwt;
    private User data;
}
