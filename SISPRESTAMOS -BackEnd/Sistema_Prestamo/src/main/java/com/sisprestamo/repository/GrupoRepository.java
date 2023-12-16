package com.sisprestamo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sisprestamo.entity.Grupo;
import com.sisprestamo.entity.Ubigeo;
import com.sisprestamo.entity.Usuario;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo,Long>{

	@Query("SELECT p.grupo.id FROM Prestamista p "
			+ "JOIN Usuario u ON u.id = p.usuario.id "
			+ "WHERE u.username=?1")
	public long findGrupoByPrestamista(String username); 
	
	//Buscar id de grupo de jefe
	@Query("SELECT g.id FROM Grupo g "
			+ "JOIN Usuario u on g.jefe.id=u.id "
			+ "where u.username=?1 ")
	public long findGrupoByJefe(String username);
	
	@Query("SELECT g.id FROM Grupo g WHERE g.jefe = :usuario")
    public Long findIdByJefe(@Param("usuario") Usuario usuario);
	
	@Query("SELECT g.id FROM Grupo g")
	public List<Long> listarGrupos();

	@Query("SELECT g.id FROM Grupo g WHERE g.ubigeo.id= :ubigeo")
	public List<Long> listarIdGrupoPorUbigeo(String ubigeo);

	 public boolean existsByUbigeo(Ubigeo ubigeo);


	 @Query("Select g.id From Grupo g where g.jefe.id = :idusuario")
	 public Long obtenerIdGrupo(@Param("idusuario") Long idUsuario);
	 
	 @Query("SELECT p.grupo.id FROM Prestamista p WHERE p.usuario.id = :idusuario")
	 public Long obtenerIdGrupoPrestamista(@Param("idusuario") Long idUsuario);

	 
}
