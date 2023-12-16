package com.sisprestamo.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.Persona;
import com.sisprestamo.repository.PersonaRepository;

@Service
public class PersonaServicesImpl extends ICRUDImpl<Persona, Long>{

	@Autowired
	private PersonaRepository repo;

	@Override
	public JpaRepository<Persona, Long> getRepository() {
		return repo;
	}
	
	public boolean existeCelular(String celular) {
		return repo.existsByCelular(celular);
	}
	
	public boolean existeDNI(String dni) {
		return repo.existsByDni(dni);
	}
	
}


