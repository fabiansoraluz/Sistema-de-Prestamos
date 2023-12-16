package com.sisprestamo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sisprestamo.entity.Cliente;
import com.sisprestamo.entity.CuentaBancaria;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query("SELECT C.cuentasBancarias FROM Cliente C WHERE C.id=?1")
	public List<CuentaBancaria> listarCuentasXID(long id);
	
	@Query("SELECT C FROM Cliente C "
			+ "INNER JOIN Usuario U ON C.persona.id=U.persona.id "
			+ "WHERE U.id=?1 ")
	public Cliente buscarClienteXIDUsuario(long id);
	
}
