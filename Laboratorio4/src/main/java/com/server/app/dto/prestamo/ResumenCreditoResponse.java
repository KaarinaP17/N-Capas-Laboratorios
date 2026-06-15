package com.server.app.dto.prestamo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumenCreditoResponse {
    private Double totalPrestado;
    private Double totalPagado;
    private Double deudaPendiente;
    private Integer totalPrestamosActivos;
}