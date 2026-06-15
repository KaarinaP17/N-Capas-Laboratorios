package com.server.app.repositories;

import com.server.app.entities.Prestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    @Query("SELECT p FROM Prestamo p WHERE p.usuario.id = :usuarioId")
    Page<Prestamo> findAllByUsuarioId(Integer usuarioId, Pageable pageable);

    @Query("SELECT p FROM Prestamo p WHERE p.usuario.id = :usuarioId")
    List<Prestamo> findAllByUsuarioId(Integer usuarioId);
}