package com.server.app.components.mapper;

import com.server.app.dto.abono.AbonoDto;
import com.server.app.entities.Abono;
import org.springframework.stereotype.Component;

@Component
public class AbonoMapper {

    public AbonoDto toResponse(Abono abono) {
        return AbonoDto.builder()
                .id(abono.getId())
                .monto(abono.getMonto())
                .fechaPago(abono.getFechaPago())
                .recargoMora(abono.getRecargoMora())
                .planPagoId(abono.getPlanPago().getId())
                .build();
    }
}