package com.sisprestamo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisprestamo.entity.Persona;
import com.sisprestamo.entity.Prestamista;
import com.sisprestamo.entity.Prestamo;
import com.sisprestamo.entity.Rol;
import com.sisprestamo.entity.Solicitud;
import com.sisprestamo.entity.Usuario;
import com.sisprestamo.services.CuotaService;
import com.sisprestamo.services.PrestamistaProcedureServices;
import com.sisprestamo.services.PrestamoService;
import com.sisprestamo.servicesImpl.PersonaServicesImpl;
import com.sisprestamo.servicesImpl.PrestamistaServicesImpl;
import com.sisprestamo.servicesImpl.SolicitudServicesImpl;
import com.sisprestamo.servicesImpl.UsuarioServicesImpl;
import com.sisprestamo.utils.Mensaje;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prestamista")

@CrossOrigin(originPatterns = "*")
public class PrestamistaController {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private PrestamistaProcedureServices procPrestamista;

	@Autowired
	private UsuarioServicesImpl SUsuario;

	@Autowired
	private PersonaServicesImpl SPersona;

	@Autowired
	private PrestamistaServicesImpl SPrestamista;

	@Autowired
	private SolicitudServicesImpl SSolicitud;
	
	@Autowired
	private PrestamoService SPrestamo;
	
	@Autowired
	private CuotaService SCuota;
	
	@PostMapping("/registrar")
	public ResponseEntity<?> registrarPrestamista(@Valid @RequestBody Prestamista prestamista, BindingResult result) {

		// VALIDACIONES

		if (result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Error de validación"), HttpStatus.BAD_REQUEST);
		}
		if (SUsuario.existeUsuario(prestamista.getUsuario().getUsername())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El username ya se encuentra registrado"),
					HttpStatus.BAD_REQUEST);
		}
		if (SUsuario.existeCorreo(prestamista.getUsuario().getCorreo())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El correo ya se encuentra registrado"),
					HttpStatus.BAD_REQUEST);
		}
		if (SPersona.existeCelular(prestamista.getUsuario().getPersona().getCelular())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El celular ya se encuentra registrado"),
					HttpStatus.BAD_REQUEST);
		}
		if (SPersona.existeDNI(prestamista.getUsuario().getPersona().getDni())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El DNI ya se encuentra registrado"),
					HttpStatus.BAD_REQUEST);
		}

		// Registramos a la persona
		Persona p = SPersona.registrar(prestamista.getUsuario().getPersona());

		// Construimos el usuario
		Usuario u = prestamista.getUsuario();
		u.setPersona(p);

		// Definimos el rol prestamista
		Rol rol = new Rol();
		rol.setId(3);
		u.setRol(rol);

		// Encriptamos el password del usuario
		u.setPassword(encoder.encode(u.getPassword()));

		// Registramos al cliente
		Usuario usuario = SUsuario.registrar(u);

		// Definimos el usuario en prestamista
		prestamista.setUsuario(usuario);

		// Registramos prestamista
		prestamista = SPrestamista.registrar(prestamista);

		return new ResponseEntity<Prestamista>(prestamista, HttpStatus.CREATED);
	}


  @PutMapping("/actualizar")
	public ResponseEntity<?> actualizarPrestamista(@Valid @RequestBody Prestamista prestamista, BindingResult result) {
	  
	  	Usuario obj = SPrestamista.buscarPorId(prestamista.getId()).getUsuario();
	  
	  	// VALIDACIONES

		if (result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Error de validación"), HttpStatus.BAD_REQUEST);
		}
		if (SUsuario.existeUsuario(prestamista.getUsuario().getUsername()) && !obj.getUsername().equals(prestamista.getUsuario().getUsername())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El username ya se encuentra registrado"),
					HttpStatus.BAD_REQUEST);
		}
		if (SUsuario.existeCorreo(prestamista.getUsuario().getCorreo()) && !obj.getCorreo().equals(prestamista.getUsuario().getCorreo())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El correo ya se encuentra registrado"),
					HttpStatus.BAD_REQUEST);
		}
		if (SPersona.existeCelular(prestamista.getUsuario().getPersona().getCelular()) && !obj.getPersona().getCelular().equals(prestamista.getUsuario().getPersona().getCelular())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El celular ya se encuentra registrado"),
					HttpStatus.BAD_REQUEST);
		}
		if (SPersona.existeDNI(prestamista.getUsuario().getPersona().getDni()) && !obj.getPersona().getDni().equals(prestamista.getUsuario().getPersona().getDni())) {
			return new ResponseEntity<Mensaje>(new Mensaje("El DNI ya se encuentra registrado"),
					HttpStatus.BAD_REQUEST);
		}
	  
		Prestamista prest = SPrestamista.buscarPorId(prestamista.getId());
		
	    Persona personaActualizada = SPersona.actualizar(prestamista.getUsuario().getPersona());

	    
	    Usuario usuarioActualizado = SUsuario.actualizar(prestamista.getUsuario());
	    usuarioActualizado.setPassword(prest.getUsuario().getPassword());
	    
	    usuarioActualizado.setPersona(personaActualizada); 

	    prestamista.getUsuario().setCreate_at(LocalDate.now());
	    prestamista.getUsuario().setRol(prest.getUsuario().getRol());
	    
	    
	    usuarioActualizado = SUsuario.actualizar(usuarioActualizado);

	   
	    prestamista.setUsuario(usuarioActualizado);

	    
	    Prestamista prestamistaActualizado = SPrestamista.actualizar(prestamista);

	    return new ResponseEntity<Prestamista>(prestamistaActualizado, HttpStatus.OK);
	}
  
  
	@GetMapping("/listarPrestamistas/{idGrupo}")
	public List<Map<String, Object>> obtenerInformacionGrupo(@PathVariable int idGrupo) {
		return procPrestamista.obtenerInformacionGrupo(idGrupo);
	}

	@DeleteMapping("/eliminar/{idPrestamista}")
	public ResponseEntity<?> eliminarPrestamista(@PathVariable Long idPrestamista) {
		try {
			procPrestamista.eliminarPrestamistaParaJefeGrupo(idPrestamista);
			return ResponseEntity.ok(HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al eliminar prestamista: " + e.getMessage());
		}
	}

	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscarPrestamistaPorId(@PathVariable Long id) {

		Prestamista prestamista = SPrestamista.buscarPorId(id);
		if (prestamista != null) {
			return new ResponseEntity<>(prestamista, HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prestamista no encontrado");
		}

	}

	@PutMapping("/aprobarSolicitud")
	public ResponseEntity<?> aprobarSolicitud(@RequestBody Prestamo prestamo) {
		
		// Obtenemos el ID de Solicitud
		String idSolicitud = prestamo.getSolicitud().getId();
		// Obtenemos la solicitud
		Solicitud solicitud = SSolicitud.buscarPorId(idSolicitud);
		// Validamos que solicitud sea pendiente
		switch(solicitud.getEstado()) {
			case 2: return new ResponseEntity<Mensaje>(new Mensaje("La solicitud ya ha sido aprobada"), HttpStatus.BAD_REQUEST);
			case 3: return new ResponseEntity<Mensaje>(new Mensaje("La solicitud ya ha sido rechazada"), HttpStatus.BAD_REQUEST);
		}
		int confirmacion = SSolicitud.aprobarSolicitud(idSolicitud);
		
		if (confirmacion > 0) {
			
			if(solicitud.getEstado()!=1) {
				Mensaje mensaje = new Mensaje("Error en la aprobación de la solicitud " + "'" + idSolicitud + "'.");
				return new ResponseEntity<Mensaje>(mensaje, HttpStatus.BAD_REQUEST); 
			}
			prestamo.setSolicitud(solicitud);
			prestamo.setMoraPagada(0.0);
			
			//Calculamos el interes
			int interes = obtenerInteres(solicitud.getDias());
			double montoPrestamo = prestamo.getSolicitud().getMonto();
			double montoInteres = (montoPrestamo * interes) / 100;
			prestamo.setInteresAgregado(montoInteres);
			
			Prestamo nuevo = SPrestamo.registrar(prestamo);
			
			SCuota.registrar(nuevo);
			
			Mensaje mensaje = new Mensaje("Solicitud " + "'" + idSolicitud + "' aprobada correctamente.");
			
			return new ResponseEntity<Mensaje>(mensaje, HttpStatus.OK);
			
		}else {
			Mensaje mensaje = new Mensaje("Error en la aprobación de la solicitud " + "'" + idSolicitud + "'.");
			return new ResponseEntity<Mensaje>(mensaje, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	private int obtenerInteres(int cantidadCuotas) {
		switch (cantidadCuotas) {
		case 10:
			return 5;
		case 15:
			return 7;
		case 20:
			return 10;
		case 25:
			return 12;
		default:
			return 15;
		}
	}
	
	@PutMapping("/rechazarSolicitud/{id}")
	public ResponseEntity<?> rechazarSolicitud(@PathVariable("id") String id) {
		// Obtenemos la solicitud
		Solicitud solicitud = SSolicitud.buscarPorId(id);
		// Validamos que solicitud sea pendiente
		switch(solicitud.getEstado()) {
			case 2: return new ResponseEntity<Mensaje>(new Mensaje("La solicitud ya ha sido aprobada"), HttpStatus.BAD_REQUEST);
			case 3: return new ResponseEntity<Mensaje>(new Mensaje("La solicitud ya ha sido rechazada"), HttpStatus.BAD_REQUEST);
		}
		int confirmacion = SSolicitud.rechazarSolicitud(id);
		
		if (confirmacion > 0) {
			return new ResponseEntity<Mensaje>(new Mensaje("Solicitud " + "'" + id + "' rechazada correctamente."), HttpStatus.OK);
		}
		
		return new ResponseEntity<Mensaje>(new Mensaje("Error en el rechazo de la solicitud " + "'" + id + "'."), HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/obtenerIdPrestamista/{id}")
	public ResponseEntity<?> obtenerIdPrestamista(@PathVariable Long id) {
	    Long idPrestamista = SPrestamista.obtenerIdPrestamistaPorUsername(id);

	    if (idPrestamista != null) {
	        return new ResponseEntity<>(idPrestamista, HttpStatus.OK);
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Prestamista no encontrado");
	    }
	}


	
	

}
