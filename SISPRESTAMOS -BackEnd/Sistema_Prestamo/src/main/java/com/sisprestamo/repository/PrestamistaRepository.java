package com.sisprestamo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisprestamo.entity.Prestamista;

public interface PrestamistaRepository extends JpaRepository<Prestamista, Long> {

	
	// Método para buscar el ID del prestamista por su nombre de usuario
	 @Query("SELECT p.id FROM Prestamista p WHERE p.usuario.id = :id")
	 Long obtenerIdPrestamistaPorId(@Param("id") Long id);
}
