package com.sisprestamo.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.Prestamista;
import com.sisprestamo.repository.PrestamistaRepository;

@Service
public class PrestamistaServicesImpl extends ICRUDImpl<Prestamista, Long>{

	@Autowired
	private PrestamistaRepository repo;

	@Override
	public JpaRepository<Prestamista, Long> getRepository() {
		return repo;
	}
	
	public Long obtenerIdPrestamistaPorUsername(Long id) {
	    return repo.obtenerIdPrestamistaPorId(id);
	}
	
}
