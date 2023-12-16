package com.sisprestamo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisprestamo.entity.CuentaBancaria;

public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria, Integer>{
	
	public boolean existsByNumero(String numero);
    public boolean existsByNombre(String nombre);
    
	@Query("SELECT CB FROM CuentaBancaria CB "
			+ "INNER JOIN Cliente C on CB.cliente.id=C.id "
			+ "INNER JOIN Usuario U on U.persona.id=C.persona.id "
			+ "WHERE U.id = ?1")
	public List<CuentaBancaria> listarXIDUsuario(long id);
    
}
