package com.server.app.dto.prestamo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoDto {
    private int id;
    private Double capitalSolicitado;
    private Double tasaInteresAnual;
    private Integer plazoMeses;
    private String estado;
}