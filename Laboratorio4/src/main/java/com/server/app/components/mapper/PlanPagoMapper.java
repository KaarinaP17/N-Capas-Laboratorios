package com.server.app.components.mapper;

import com.server.app.dto.planpago.PlanPagoDto;
import com.server.app.entities.PlanPago;
import org.springframework.stereotype.Component;

@Component
public class PlanPagoMapper {
    public PlanPagoDto toResponse(PlanPago p) {
        return PlanPagoDto.builder()
                .id(p.getId())
                .numeroCuota(p.getNumeroCuota())
                .montoCapital(p.getMontoCapital())
                .montoInteres(p.getMontoInteres())
                .fechaVencimiento(p.getFechaVencimiento())
                .estado(p.getEstado().name())
                .build();
    }
}