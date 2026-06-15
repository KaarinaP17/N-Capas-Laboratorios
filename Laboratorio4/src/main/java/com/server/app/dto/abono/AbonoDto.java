package com.server.app.dto.abono;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AbonoDto {
    private Long id;
    private Double monto;
    private LocalDateTime fechaPago;
    private Double recargoMora;
    private Long planPagoId;
}
