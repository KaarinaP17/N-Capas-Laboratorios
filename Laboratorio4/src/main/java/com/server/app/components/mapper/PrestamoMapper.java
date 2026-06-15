package com.server.app.components.mapper;

import com.server.app.dto.prestamo.PrestamoDto;
import com.server.app.entities.Prestamo;
import org.springframework.stereotype.Component;

@Component
public class PrestamoMapper {
    public PrestamoDto toResponse(Prestamo p) {
        return PrestamoDto.builder()
                .id(p.getId())
                .capitalSolicitado(p.getCapitalSolicitado())
                .tasaInteresAnual(p.getTasaInteresAnual())
                .plazoMeses(p.getPlazoMeses())
                .estado(p.getEstado().name()) // Enum a String
                .build();
    }
}