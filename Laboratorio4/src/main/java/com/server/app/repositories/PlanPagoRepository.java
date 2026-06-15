package com.server.app.repositories;

import com.server.app.entities.PlanPago;
import com.server.app.entities.enums.EstadoPago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanPagoRepository extends JpaRepository<PlanPago, Integer> {

    default Page<PlanPago> findByPrestamoIdOrderByNumeroCuotaAsc(Long prestamoId, Pageable page) {
        return null;
    }

    List<PlanPago> findByPrestamoIdAndEstado(Long prestamoId, EstadoPago estado);
}