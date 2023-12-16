package com.sisprestamo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sisprestamo.entity.Cliente;
import com.sisprestamo.entity.Solicitud;

import jakarta.transaction.Transactional;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud,String>{
	
	//Cambiar estado de la solicitud
	@Transactional
	@Modifying
	@Query("UPDATE Solicitud S SET S.estado = ?1 WHERE S.id = ?2")
	public int actualizarEstado(int estado,long id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Solicitud S SET S.estado = 2 WHERE S.id = ?1")
	public int aprobarSolicitud(String id);
	
	@Transactional
	@Modifying
	@Query("UPDATE Solicitud S SET S.estado = 3 WHERE S.id = ?1")
	public int rechazarSolicitud(String id);
	
	List<Solicitud> findByCliente(Cliente cliente);


	@Query(value = "CALL ObtenerSolicitudesPorUsuario(:idUsuario)", nativeQuery = true)
	List<Solicitud> obtenerSolicitudesPorUsuario(@Param("idUsuario") int idUsuario);

	@Procedure(name = "FiltrarSolicitudes")
    List<Object[]> FiltrarSolicitudes(@Param("id_grupo") long idGrupo,@Param("nombre") String nombre, @Param("estado") int estado);
	
}
