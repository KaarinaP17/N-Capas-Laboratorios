package com.server.app.dto.prestamo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoRequest {
    @NotNull(message = "El capital es obligatorio")
    @Positive(message = "El capital debe ser mayor a cero")
    private Double capitalSolicitado;

    @NotNull(message = "La tasa de interés es obligatoria")
    @Positive(message = "La tasa debe ser mayor a cero")
    private Double tasaInteresAnual;

    @NotNull(message = "El plazo es obligatorio")
    @Min(value = 1, message = "El plazo debe ser de al menos 1 mes")
    private Integer plazoMeses;
}
