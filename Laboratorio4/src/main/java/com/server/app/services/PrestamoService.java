package com.server.app.services;

import com.server.app.dto.prestamo.PrestamoRequest;
import com.server.app.dto.prestamo.ResumenCreditoResponse;
import com.server.app.entities.PlanPago;
import com.server.app.entities.Prestamo;
import com.server.app.entities.enums.EstadoPago;
import com.server.app.entities.enums.EstadoPrestamo;
import com.server.app.repositories.PlanPagoRepository;
import com.server.app.repositories.PrestamoRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PrestamoService {
    private final PrestamoRepository prestamoRepository;
    private final PlanPagoRepository planPagoRepository;

    @Transactional
    public Page<Prestamo> listarMisPrestamos(Integer usuarioId, Pageable pageable) {
        return prestamoRepository.findAllByUsuarioId(usuarioId, pageable);
    }

    @Transactional
    public Prestamo solicitarPrestamo(Prestamo prestamo, PrestamoRequest request) {
        Prestamo savedPrestamo = prestamoRepository.save(prestamo);

        List<PlanPago> cuotas = calcularAmortizacion(savedPrestamo, request);
        planPagoRepository.saveAll(cuotas);

        return savedPrestamo;
    }

    private List<PlanPago> calcularAmortizacion(Prestamo p, PrestamoRequest req) {
        List<PlanPago> plan = new ArrayList<>();
        double capitalMensual = p.getCapitalSolicitado() / p.getPlazoMeses();
        double interesMensual = (p.getTasaInteresAnual() / 12) / 100;

        for (int i = 1; i <= p.getPlazoMeses(); i++) {
            plan.add(PlanPago.builder()
                    .numeroCuota(i)
                    .montoCapital(capitalMensual)
                    .montoInteres(p.getCapitalSolicitado() * interesMensual)
                    .fechaVencimiento(LocalDate.now().plusMonths(i))
                    .estado(EstadoPago.PENDIENTE)
                    .prestamo(p)
                    .build());
        }
        return plan;
    }

    @Transactional
    public ResumenCreditoResponse obtenerResumen(Integer usuarioId) {
        List<Prestamo> prestamos = prestamoRepository.findAllByUsuarioId(usuarioId);

        double totalPrestado = prestamos.stream()
                .mapToDouble(p -> p.getCapitalSolicitado() != null ? p.getCapitalSolicitado() : 0.0)
                .sum();

        double totalPagado = prestamos.stream()
                .filter(p -> p.getPlanesPago() != null)
                .flatMap(p -> p.getPlanesPago().stream())
                .filter(cuota -> cuota.getEstado() == EstadoPago.PAGADO)
                .mapToDouble(c -> (c.getMontoCapital() != null ? c.getMontoCapital() : 0.0) +
                        (c.getMontoInteres() != null ? c.getMontoInteres() : 0.0))
                .sum();

        return ResumenCreditoResponse.builder()
                .totalPrestado(totalPrestado)
                .totalPagado(totalPagado)
                .deudaPendiente(totalPrestado - totalPagado)
                .totalPrestamosActivos((int) prestamos.stream()
                        .filter(p -> p.getEstado() != EstadoPrestamo.PAGADO)
                        .count())
                .build();
    }
}