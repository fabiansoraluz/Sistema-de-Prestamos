package com.sisprestamo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.Cliente;
import com.sisprestamo.repository.ClienteRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente registrar(Cliente bean) {
		return repo.save(bean);
	}
}
