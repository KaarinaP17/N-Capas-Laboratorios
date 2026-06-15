package com.server.app.services;

import com.server.app.components.mapper.AbonoMapper;
import com.server.app.dto.abono.AbonoDto;
import com.server.app.dto.abono.AbonoRequest;
import com.server.app.entities.Abono;
import com.server.app.entities.PlanPago;
import com.server.app.entities.enums.EstadoPago;
import com.server.app.exceptions.ConfictException;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.AbonoRepository;
import com.server.app.repositories.PlanPagoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AbonoService {
    private final AbonoRepository abonoRepository;
    private final AbonoMapper abonoMapper;
    private final PlanPagoRepository planPagoRepository;

    @Transactional
    public AbonoDto registrarAbono(AbonoRequest request) {
        PlanPago cuota = planPagoRepository.findById(request.getPlanPagoId())
                .orElseThrow(() -> new NotFoundException("Cuota no encontrada"));

        if (cuota.getEstado() == EstadoPago.PAGADO) {
            throw new ConfictException("La cuota ya está pagada");
        }

        double recargo = 0.0;
        if (LocalDate.now().isAfter(cuota.getFechaVencimiento())) {
            recargo = cuota.getMontoCapital() * 0.05;
        }

        Abono abono = Abono.builder()
                .monto(request.getMonto())
                .fechaPago(LocalDateTime.now())
                .recargoMora(recargo)
                .planPago(cuota)
                .build();

        if (request.getMonto() >= (cuota.getMontoCapital() + cuota.getMontoInteres())) {
            cuota.setEstado(EstadoPago.PAGADO);
            planPagoRepository.save(cuota);
        }

        return abonoMapper.toResponse(abonoRepository.save(abono));
    }
}