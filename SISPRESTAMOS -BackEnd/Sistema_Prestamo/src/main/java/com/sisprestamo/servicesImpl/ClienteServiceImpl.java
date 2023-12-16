package com.sisprestamo.servicesImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.Cliente;
import com.sisprestamo.repository.ClienteRepository;

@Service
public class ClienteServiceImpl extends ICRUDImpl<Cliente, Long>{

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public JpaRepository<Cliente, Long> getRepository() {
		return repo;
	}	
	public Cliente buscarClienteXIDUsuario(long id) {
		return repo.buscarClienteXIDUsuario(id);
	}

}
