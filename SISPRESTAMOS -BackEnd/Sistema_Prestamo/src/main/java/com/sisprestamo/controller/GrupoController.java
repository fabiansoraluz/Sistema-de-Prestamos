package com.sisprestamo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisprestamo.services.GrupoService;

@RestController
@RequestMapping("/api/grupo")
@CrossOrigin(originPatterns = "*")
public class GrupoController {
	
	@Autowired
	private GrupoService SGrupo;
	
	@GetMapping("/prestamista/{username}")
	public ResponseEntity<?> prestamista(@PathVariable String username){
		return new ResponseEntity<Long>(SGrupo.findIdByPrestamista(username),HttpStatus.OK);
	}
	@GetMapping("/jefe/{username}")
	public ResponseEntity<?> jefe(@PathVariable String username){
		return new ResponseEntity<Long>(SGrupo.findIdByJefe(username),HttpStatus.OK);
	}
	@GetMapping
	public ResponseEntity<?> listar(){
		return new ResponseEntity<List<Long>>(SGrupo.listarGrupos(),HttpStatus.OK);
	}
}
