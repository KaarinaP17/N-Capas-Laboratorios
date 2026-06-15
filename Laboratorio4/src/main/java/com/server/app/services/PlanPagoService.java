package com.server.app.services;

import com.server.app.entities.PlanPago;
import com.server.app.repositories.PlanPagoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class PlanPagoService {
    private final PlanPagoRepository planPagoRepository;

    @Transactional
    public Page<PlanPago> listarCuotasPorPrestamo(Long prestamoId, Pageable page) {
        return planPagoRepository.findByPrestamoIdOrderByNumeroCuotaAsc(prestamoId, page);
    }
}