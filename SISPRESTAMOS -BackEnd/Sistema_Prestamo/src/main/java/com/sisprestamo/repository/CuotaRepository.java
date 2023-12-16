package com.sisprestamo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisprestamo.entity.Cuota;
import com.sisprestamo.entity.Prestamo;

public interface CuotaRepository extends JpaRepository<Cuota, Long> {

    @Query("SELECT p FROM Prestamo p JOIN p.cuotas c WHERE c.id = :idCuota")
    Prestamo findPrestamoByCuotaId(@Param("idCuota") Long idCuota);
	
}
