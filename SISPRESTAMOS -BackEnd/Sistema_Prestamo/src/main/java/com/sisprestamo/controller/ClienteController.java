package com.sisprestamo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisprestamo.entity.Cliente;
import com.sisprestamo.servicesImpl.ClienteServiceImpl;
import com.sisprestamo.utils.Mensaje;


@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ClienteController {

	@Autowired
	private ClienteServiceImpl SCliente;
	
	@GetMapping("/usuario/{idUsuario}")
	public ResponseEntity<?> buscarClienteXIDUsuario(@PathVariable long idUsuario) {
		Cliente cliente = SCliente.buscarClienteXIDUsuario(idUsuario);
		if(cliente == null) {
			return new ResponseEntity<Mensaje>(new Mensaje("Cliente no encontrado"),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Cliente>(cliente,HttpStatus.OK);
	}
	
}
