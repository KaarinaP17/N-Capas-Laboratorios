package com.server.app.dto.abono;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AbonoRequest {
    @NotNull(message = "El capital es obligatorio")
    @Positive(message = "El capital debe ser mayor a cero")
    public int planPagoId;

    @NotNull(message = "El capital es obligatorio")
    @Positive(message = "El capital debe ser mayor a cero")
    private Double monto;
}
