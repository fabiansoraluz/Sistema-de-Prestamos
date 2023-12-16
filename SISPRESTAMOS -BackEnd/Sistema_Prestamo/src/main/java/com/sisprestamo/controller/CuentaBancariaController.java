package com.sisprestamo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisprestamo.entity.CuentaBancaria;
import com.sisprestamo.entity.TipoCuenta;
import com.sisprestamo.servicesImpl.CuentaBancariaServiceImpl;
import com.sisprestamo.utils.Mensaje;

@RestController
@RequestMapping("/api/cuenta")
@CrossOrigin(originPatterns = "*")
public class CuentaBancariaController {

	@Autowired
	private CuentaBancariaServiceImpl SCuenta;

	// Método para obtener todas las cuentas bancarias
	@GetMapping("/listar")
	public List<CuentaBancaria> listarCuentasBancarias() {
		return SCuenta.listarTodos();
	}
	
	// Método para obtener todas las cuentas bancarias por ID Usuario
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> listarXIDUsuario(@PathVariable long id) {
		List<CuentaBancaria> cuentas = SCuenta.listarXIDUsuario(id);

		if (cuentas.size()>0) {
			return new ResponseEntity<List<CuentaBancaria>>(cuentas,HttpStatus.OK);
		}
		return new ResponseEntity<Mensaje>(new Mensaje("Usuario no tiene cuentas registradas"), HttpStatus.BAD_REQUEST);
	}
	
	// Método para obtener todas los tipos de cuentas
	@GetMapping("/tipo")
	public List<TipoCuenta> listarTipos() {
		return SCuenta.listarTipos();
	}
	
	// Método para registrar una cuenta bancaria con validación
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarCuentaBancaria(@RequestBody CuentaBancaria cuentaBancaria) {
	    // Verifica si ya existe una cuenta con el mismo número de cuenta o nombre de cuenta
	    if (SCuenta.numeroCuentaExiste(cuentaBancaria.getNumero())) {
	        return new ResponseEntity<>(new Mensaje("El número de cuenta ya está registrado"), HttpStatus.BAD_REQUEST);
	    } else if (SCuenta.nombreCuentaExiste(cuentaBancaria.getNombre())) {
	        return new ResponseEntity<>(new Mensaje("Esta cuenta ya esta registrada"), HttpStatus.BAD_REQUEST);
	    }

	    // Si no existe una cuenta con el mismo número ni nombre, procede a registrarla
	    CuentaBancaria cuenta = SCuenta.registrar(cuentaBancaria);
	    return new ResponseEntity<>(cuenta, HttpStatus.CREATED);
	}



	@PutMapping("/actualizar")
	public ResponseEntity<?> actualizarCuentaBancaria(@RequestBody CuentaBancaria cuenta) {

		CuentaBancaria cuentaRegistrada = SCuenta.buscarPorId(cuenta.getId());
		
		// Verifica si el nuevo número de cuenta ya existe en otra cuenta bancaria
		if (SCuenta.numeroCuentaExiste(cuenta.getNumero()) && !cuentaRegistrada.getNumero().equals(cuenta.getNumero())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El numero de cuenta ya está registrado"), HttpStatus.BAD_REQUEST);
		}
		
		// Actualiza la cuenta bancaria en la base de datos
		CuentaBancaria cuentaActualizada = SCuenta.actualizar(cuenta);
		return new ResponseEntity<CuentaBancaria>(cuentaActualizada, HttpStatus.OK);
	}

	// Método para obtener una cuenta bancaria por su ID
	@GetMapping("/buscar/{id}")
	public ResponseEntity<CuentaBancaria> buscarCuentaBancariaPorId(@PathVariable int id) {
		CuentaBancaria cuentaBancaria = SCuenta.buscarPorId(id);
		if (cuentaBancaria != null) {
			return new ResponseEntity<>(cuentaBancaria, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Método para eliminar una cuenta bancaria por su ID
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarCuentaBancaria(@PathVariable int id) {
		CuentaBancaria cuentaBancaria = SCuenta.buscarPorId(id);
		if (cuentaBancaria != null) {
			SCuenta.eliminarPorId(id);
			return new ResponseEntity<Mensaje>(new Mensaje("Cuenta Eliminada"),HttpStatus.OK);
		} else {
			return new ResponseEntity<Mensaje>(new Mensaje("Error al encontrar la cuenta"),HttpStatus.NOT_FOUND);
		}
	}
}
