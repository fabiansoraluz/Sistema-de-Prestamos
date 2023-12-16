package com.sisprestamo.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisprestamo.entity.Solicitud;
import com.sisprestamo.repository.SolicitudRepository;
import com.sisprestamo.services.GrupoService;
import com.sisprestamo.services.JefePrestamistaProcedureServices;

import com.sisprestamo.servicesImpl.SolicitudServicesImpl;
import com.sisprestamo.servicesImpl.UsuarioServicesImpl;
import com.sisprestamo.utils.Mensaje;
import com.sisprestamo.utils.SolicitudDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/solicitud")
@CrossOrigin(origins = "http://localhost:4200")
public class SolicitudController {

	@Autowired
	private SolicitudServicesImpl SSolicitud;

	@Autowired
	private JefePrestamistaProcedureServices SJefe;

	@Autowired
	private UsuarioServicesImpl SUsuario;

	@Autowired
	private GrupoService SGrupo;
	
	@Autowired
    private SolicitudRepository solicitudRepository;
	
	@GetMapping("/porUsuario/{idUsuario}")
    public ResponseEntity<?> obtenerSolicitudesPorUsuario(@PathVariable int idUsuario) {
        List<Solicitud> solicitudes = solicitudRepository.obtenerSolicitudesPorUsuario(idUsuario);

        if (solicitudes != null && !solicitudes.isEmpty()) {
            return new ResponseEntity<List<Solicitud>>(solicitudes, HttpStatus.OK);
        } else {
            return new ResponseEntity<Mensaje>(new Mensaje("No se encontraron solicitudes para el usuario"), HttpStatus.NOT_FOUND);
        }
    }

	@GetMapping("/lista")
	public ResponseEntity<?> listarSolicitudes() {
		return new ResponseEntity<List<Solicitud>>(SSolicitud.listarTodos(), HttpStatus.OK);
	}

	@PostMapping("/registrar")
	public ResponseEntity<?> registrarSolicitud(@Valid @RequestBody Solicitud solicitud, BindingResult result) {

		// VALIDACIONES
		if (result.hasErrors()) {
			return new ResponseEntity<Mensaje>(new Mensaje("Error de validación"), HttpStatus.BAD_REQUEST);
		}

		// VALIDAMOS POR SOLICITUD

		Long idCliente = solicitud.getCliente().getId();
		List<Solicitud> solicitudes = SSolicitud.obtenerSolicitudesPorCliente(idCliente);

		// VALIDAMOS QUE NO TENGA SOLICITUDES => PENDIENTES

		if (!validarPendientes(solicitudes)) {
			return new ResponseEntity<Mensaje>(new Mensaje("Cliente tiene una solicitud pendiente"),
					HttpStatus.BAD_REQUEST);
		}

		// VALIDAMOS QUE NO TENGA SOLICITUDES => ACEPTADAS DURANTE EL MES

		if (!validarAceptadas(solicitudes)) {
			return new ResponseEntity<Mensaje>(new Mensaje("El cliente tiene una solicitud aceptada en el mes"),
					HttpStatus.BAD_REQUEST);
		}
		
		// VALIDAMOS QUE NO TENGA SOLICITUDES => RECHAZADAS DURANTE EL MES

		if (!validarRechazadas(solicitudes)) {
			return new ResponseEntity<Mensaje>(new Mensaje("El cliente tiene una solicitud rechazada en el mes"),
					HttpStatus.BAD_REQUEST);
		}
		
		// GENERAMOS EL CODIGO
		String codigo = getCodigo();
		solicitud.setId(codigo);

		// REGISTRAMOS LA SOLICITUD
		Solicitud soli = SSolicitud.registrar(solicitud);

		// DEVOLVEMOS LA RESPUESTA
		return new ResponseEntity<Solicitud>(soli, HttpStatus.CREATED);
	}

	boolean validarPendientes(List<Solicitud> solicitudes) {
		for (Solicitud s : solicitudes) {
			if (s.getEstado() == 1) {
				return false;
			}
		}
		return true;
	}

	boolean validarAceptadas(List<Solicitud> solicitudes) {

		Month mes = LocalDate.now().getMonth();

		for (Solicitud s : solicitudes) {
			if (s.getEstado() == 2 && s.getFechaSolicitud().getMonth() == mes) {
				return false;
			}
		}
		return true;
	}
	boolean validarRechazadas(List<Solicitud> solicitudes) {

		Month mes = LocalDate.now().getMonth();

		for (Solicitud s : solicitudes) {
			if (s.getEstado() == 3 && s.getFechaSolicitud().getMonth() == mes) {
				return false;
			}
		}
		return true;
	}

	@GetMapping("/seleccionGrupo/{idUsuario}")
	public ResponseEntity<?> obtenerGrupoParaSolicitud(@PathVariable Long idUsuario) {
		List<String> UDGrupo = SJefe.obtenerIdUbigeoDeGrupos();
		String ubigeoUsuario = SUsuario.BuscarUbigeoPorIdUsuario(idUsuario);

		List<Long> idUbigeo = encontrarUbigeoCercano(ubigeoUsuario, UDGrupo);

		if (idUbigeo.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Long primerId = idUbigeo.get(0);

		return ResponseEntity.ok(primerId);
	}

	private List<Long> encontrarUbigeoCercano(String ubigeoUsuario, List<String> listaUbigeos) {
		int longitudUbigeoUsuario = ubigeoUsuario.length();
		String ubigeoCercano = null;

		for (String ubigeo : listaUbigeos) {
			int longitudUbigeo = ubigeo.length();

			if (longitudUbigeo == longitudUbigeoUsuario) {
				if (ubigeoCercano == null || Math.abs(Integer.parseInt(ubigeo) - Integer.parseInt(ubigeoUsuario)) < Math
						.abs(Integer.parseInt(ubigeoCercano) - Integer.parseInt(ubigeoUsuario))) {
					ubigeoCercano = ubigeo;
				}
			}
		}

		List<Long> IdGrupo = SGrupo.listarIdGrupoPorUbigeo(ubigeoCercano);

		return IdGrupo;
	}

	private String getCodigo() {

		String nuevoCodigo;
		String ultimoCodigo = SSolicitud.obtenerUltimoCodigo();

		if (ultimoCodigo != null) {

			int ultimoNumero = Integer.parseInt(ultimoCodigo.substring(1));
			int nuevoNumero = ultimoNumero + 1;
			nuevoCodigo = String.format("S%04d", nuevoNumero);

		} else {
			nuevoCodigo = "S0001";
		}

		return nuevoCodigo;
	}

	@GetMapping("/listaporGrupo/{idGrupo}")
	public List<Map<String, Object>> obtenerSolicitudesPorGrupo(@PathVariable int idGrupo) {
		return SSolicitud.obtenerSolicitudesPorGrupo(idGrupo);
	}

	@GetMapping("/obtenerIdGrupo/{idUsuario}")
	public ResponseEntity<Long> obtenerIdGrupo(@PathVariable Long idUsuario) {
		Long idGrupo = SGrupo.obtenerIdGrupo(idUsuario);
		return ResponseEntity.ok(idGrupo);
	}

	
	@GetMapping("/obtenerIdGrupoPrestamista/{idUsuario}")
	public ResponseEntity<Long> obtenerIdGrupoPrestamista(@PathVariable Long idUsuario) {
		Long idGrupo = SGrupo.obtenerIdGrupoPrestamista(idUsuario);
		return ResponseEntity.ok(idGrupo);
	}
	
	@GetMapping("/buscarSolicitud/{id}")
	public ResponseEntity<Solicitud> obtenerSolicitud(@PathVariable String id) {
		Solicitud s = SSolicitud.buscarPorId(id);
		 return ResponseEntity.ok(s);
	}
	
	 @GetMapping("/filtradas")
	    public ResponseEntity<List<Map<String, Object>>> obtenerSolicitudesPorGrupo(
	            @RequestParam int idGrupo,
	            @RequestParam String nombrePrestamista,
	            @RequestParam int estado) {

	        List<Map<String, Object>> solicitudes = SSolicitud.obtenerSolicitudesPorGrupo(idGrupo, nombrePrestamista, estado);

	        if (solicitudes.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        }

	        return new ResponseEntity<>(solicitudes, HttpStatus.OK);
	    }

	 @PostMapping("/filtrar")
	 @Transactional
	 public ResponseEntity<?> filtrar(@RequestBody SolicitudDTO bean){
		 List<Map<String,Object>> solicitudes = SSolicitud.filtrarSolicitud(bean.getIdGrupo(),bean.getNombre(),bean.getEstado());

	        if (solicitudes != null && !solicitudes.isEmpty()) {
	            return new ResponseEntity<List<Map<String,Object>>>(solicitudes, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<Mensaje>(new Mensaje("No se encontraron coincidencias"), HttpStatus.NOT_FOUND);
	        }
	 }


}
