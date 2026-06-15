package com.server.app.dto.planpago;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PlanPagoDto {
    private Long id;
    private Integer numeroCuota;
    private Double montoCapital;
    private Double montoInteres;
    private LocalDate fechaVencimiento;
    private String estado;
}
