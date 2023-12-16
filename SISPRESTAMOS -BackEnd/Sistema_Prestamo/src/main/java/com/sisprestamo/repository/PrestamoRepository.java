package com.sisprestamo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import com.sisprestamo.entity.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
	
	@Procedure(name = "ObtenerInformacionPrestamos")
    List<Object[]> obtenerInformacionPrestamos(
        @Param("p_id_usuario") Long idUsuario,
        @Param("p_id_prestamo") Long idPrestamo
    );
    
    @Procedure(name = "ObtenerInformacionPrestamosJefePrestamista")
    List<Object[]> obtenerInformacionPrestamosJefePrestamista(
        @Param("p_id_prestamo") Long idPrestamo
    );
    
    @Procedure(name = "spListarPrestamos") 
    List<Object[]> spListarPrestamos(@Param("p_id_usuario") Long idUsuario);

    @Procedure(name = "spListarPrestamosJefePrestamista")
    List<Object[]> spListarPrestamosJefePrestamista(@Param("p_id_usuario") Long idUsuario);
}
