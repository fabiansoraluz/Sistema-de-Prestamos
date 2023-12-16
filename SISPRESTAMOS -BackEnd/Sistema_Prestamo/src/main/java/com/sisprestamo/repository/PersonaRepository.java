package com.sisprestamo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sisprestamo.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long>{

	public boolean existsByCelular(String celular);
	public boolean existsByDni(String dni);
	
}
