package com.server.app.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {
    @Size(min = 3, max = 20, message = "El username debe tener entre 3 y 20 caracteres")
    @NotBlank(message = "El username es obligatorio")
    private String username;

    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    @NotBlank(message = "El apellido es obligatorio")
    private String surname;

    @Email(message = "El email debe ser válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "La contraseña debe tener mínimo 8 caracteres, incluir mayúscula, minúscula, número y carácter especial")
    private String password;
}