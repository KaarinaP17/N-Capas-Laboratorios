package com.server.app.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UpdatePasswordRequest {
    @NotBlank(message = "La contraseña es obligatorio")
    private String oldPassword;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "La nueva contraseña no cumple con los requisitos de seguridad")
    @NotBlank(message = "La contraseña es obligatorio")
    private String newPassword;

    @NotBlank(message = "La contraseña es obligatorio")
    private String confirmPassword;
}
