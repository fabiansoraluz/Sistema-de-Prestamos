package com.sisprestamo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisprestamo.entity.Ubigeo;
import com.sisprestamo.utils.DistritoDTO;

public interface UbigeoRepository extends JpaRepository<Ubigeo, String>{

	//Listar departamentos
	@Query("SELECT DISTINCT u.departamento FROM Ubigeo u")
	List<String> listarDepartamentos();
	
	//Listar provincias por departamento
	@Query("SELECT DISTINCT u.provincia FROM Ubigeo u WHERE u.departamento=?1")
	List<String> listarProvinciasXDepartamento(String departamento);
		
	//Listar distritos por provincia
	@Query("SELECT new com.sisprestamo.utils.DistritoDTO(u.id, u.distrito) FROM Ubigeo u WHERE u.provincia=?1")
	List<DistritoDTO> listarDistritosXProvincia(String provincia);
}
