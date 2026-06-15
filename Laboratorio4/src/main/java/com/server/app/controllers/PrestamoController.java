package com.server.app.controllers;

import com.server.app.components.mapper.PlanPagoMapper;
import com.server.app.components.mapper.PrestamoMapper;
import com.server.app.dto.abono.AbonoDto;
import com.server.app.dto.abono.AbonoRequest;
import com.server.app.dto.planpago.PlanPagoDto;
import com.server.app.dto.prestamo.PrestamoDto;
import com.server.app.dto.prestamo.PrestamoRequest;
import com.server.app.dto.prestamo.ResumenCreditoResponse;
import com.server.app.dto.response.Pagination;
import com.server.app.dto.response.PaginationMeta;
import com.server.app.entities.PlanPago;
import com.server.app.entities.Prestamo;
import com.server.app.entities.User;
import com.server.app.entities.enums.EstadoPrestamo;
import com.server.app.services.AbonoService;
import com.server.app.services.PlanPagoService;
import com.server.app.services.PrestamoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/finanzas")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final PlanPagoService planPagoService;
    private final AbonoService abonoService;
    private final PrestamoMapper prestamoMapper;
    private final PlanPagoMapper planPagoMapper;

    @GetMapping("/prestamos")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Pagination<PrestamoDto>> getMisPrestamos(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Page<Prestamo> page = prestamoService.listarMisPrestamos(currentUser.getId(), pageable);

        List<PrestamoDto> data = page.getContent().stream()
                .map(prestamoMapper::toResponse)
                .toList();

        PaginationMeta meta = new PaginationMeta(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );

        return ResponseEntity.ok(new Pagination<>(data, meta));
    }

    @PostMapping("/prestamos")
    public ResponseEntity<PrestamoDto> solicitar(@Valid @RequestBody PrestamoRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Prestamo prestamo = Prestamo.builder()
                .capitalSolicitado(request.getCapitalSolicitado())
                .tasaInteresAnual(request.getTasaInteresAnual())
                .plazoMeses(request.getPlazoMeses())
                .estado(EstadoPrestamo.PENDIENTE)
                .usuario(user)
                .build();

        Prestamo creado = prestamoService.solicitarPrestamo(prestamo, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prestamoMapper.toResponse(creado)); // Usamos Response
    }

    @GetMapping("/prestamos/{id}/planes-pago")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Pagination<PlanPagoDto>> getPlanesPago(
            @PathVariable Long id,
            @PageableDefault(size = 20, sort = "numeroCuota") Pageable pageable) {

        Page<PlanPago> paginaCuotas = planPagoService.listarCuotasPorPrestamo(id, pageable);

        List<PlanPagoDto> data = paginaCuotas.getContent().stream()
                .map(planPagoMapper::toResponse)
                .toList();

        PaginationMeta meta = new PaginationMeta(
                paginaCuotas.getNumber(),
                paginaCuotas.getSize(),
                paginaCuotas.getTotalPages(),
                paginaCuotas.getTotalElements()
        );

        return ResponseEntity.ok(new Pagination<>(data, meta));
    }

    @PostMapping("/abonos")
    public ResponseEntity<AbonoDto> registrar(@Valid @RequestBody AbonoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(abonoService.registrarAbono(request));
    }

    @GetMapping("/resumen-credito")
    public ResponseEntity<ResumenCreditoResponse> getResumen() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(prestamoService.obtenerResumen(user.getId()));
    }
}