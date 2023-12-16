package com.sisprestamo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sisprestamo.entity.Ubigeo;
import com.sisprestamo.servicesImpl.UbigeoServiceImpl;
import com.sisprestamo.utils.DistritoDTO;

@RestController
@RequestMapping("/api/ubigeo")
@CrossOrigin(originPatterns = "*")
public class UbigeoController {
	@Autowired
	private UbigeoServiceImpl SUbigeo;
	
	@GetMapping("/listar")
	@ResponseBody
	public List<Ubigeo> listarUbigeos(){
		return SUbigeo.listarTodos();	
	}
	
	@GetMapping("/departamento")
	public ResponseEntity<?> listarDepartamentos(){
		return new ResponseEntity<List<String>>(SUbigeo.listarDep(),HttpStatus.OK);
	}
	
	@GetMapping("/provincia/{departamento}")
	public ResponseEntity<?> listarProvincia(@PathVariable String departamento){
		return new ResponseEntity<List<String>>(SUbigeo.listarProvXDep(departamento),HttpStatus.OK);
	}
	
	@GetMapping("/distrito/{provincia}")
	public ResponseEntity<?> listarDistritos(@PathVariable String provincia){
		return new ResponseEntity<List<DistritoDTO>>(SUbigeo.listarDisXProv(provincia),HttpStatus.OK);
	}
}
