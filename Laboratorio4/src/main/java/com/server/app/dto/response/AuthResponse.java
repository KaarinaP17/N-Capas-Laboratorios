package com.server.app.dto.response;

import com.server.app.dto.auth.LoginResponseDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
    private String jwt;
    private LoginResponseDTO data;
}
