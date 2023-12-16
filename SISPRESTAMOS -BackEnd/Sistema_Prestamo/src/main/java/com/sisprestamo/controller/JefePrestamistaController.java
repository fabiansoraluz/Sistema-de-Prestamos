package com.sisprestamo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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

import com.sisprestamo.entity.Grupo;
import com.sisprestamo.entity.JefeDTO;
import com.sisprestamo.entity.Persona;
import com.sisprestamo.entity.Rol;
import com.sisprestamo.entity.Usuario;
import com.sisprestamo.services.JefePrestamistaProcedureServices;
import com.sisprestamo.servicesImpl.GrupoServicesImpl;
import com.sisprestamo.servicesImpl.PersonaServicesImpl;
import com.sisprestamo.servicesImpl.UsuarioServicesImpl;
import com.sisprestamo.utils.Mensaje;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/jefes")
@CrossOrigin(originPatterns = "*")
public class JefePrestamistaController {

	@Autowired
	private UsuarioServicesImpl SUsuario;

	@Autowired
	private PersonaServicesImpl SPersona;
	
	@Autowired
	private GrupoServicesImpl SGrupo;

	@Autowired
	private JefePrestamistaProcedureServices procJefePrestamista;

	@Autowired
	private PasswordEncoder encoder;

	@GetMapping("/listarTodos")
	public ResponseEntity<?> listarJefes(){
		List<Usuario> jefes = SUsuario.listarJefes();
		if(jefes.size()>0) {
			return new ResponseEntity<List<Usuario>>(jefes,HttpStatus.OK);
		}
		return new ResponseEntity<Mensaje>(new Mensaje("Jefes no registrados"),HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/registrar")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> registrarJefePrestamista(@Valid @RequestBody JefeDTO jefeDTO, BindingResult result) {

        Usuario usuario = jefeDTO.getUsuario();
        Grupo grupo = jefeDTO.getGrupo();

        if (result.hasErrors()) {
            return new ResponseEntity<Mensaje>(new Mensaje("Error de validación"), HttpStatus.BAD_REQUEST);
        }
        if (SUsuario.existeUsuario(usuario.getUsername())) {
            return new ResponseEntity<Mensaje>(new Mensaje("El username ya se encuentra registrado"), HttpStatus.BAD_REQUEST);
        }
        if (SUsuario.existeCorreo(usuario.getCorreo())) {
            return new ResponseEntity<Mensaje>(new Mensaje("El correo ya se encuentra registrado"), HttpStatus.BAD_REQUEST);
        }
        if (SPersona.existeCelular(usuario.getPersona().getCelular())) {
            return new ResponseEntity<Mensaje>(new Mensaje("El celular ya se encuentra registrado"), HttpStatus.BAD_REQUEST);
        }
        if (SPersona.existeDNI(usuario.getPersona().getDni())) {
            return new ResponseEntity<Mensaje>(new Mensaje("El DNI ya se encuentra registrado"), HttpStatus.BAD_REQUEST);
        }
        if (SGrupo.existeUbigeo(grupo.getUbigeo())) {
            return new ResponseEntity<Mensaje>(new Mensaje("El ubigeo ya se encuentra registrado en otro grupo"), HttpStatus.BAD_REQUEST);
        }

        try {
        	
        	usuario.setCreate_at(LocalDate.now());
        	usuario.setEstado(1);
        	
            // Registramos a la persona
            Persona p = SPersona.registrar(usuario.getPersona());
            usuario.setPersona(p);

            // Definimos el rol jefe
            Rol rol = new Rol();
            rol.setId(2);
            
            usuario.setRol(rol);

            // Encriptamos el password del usuario
            usuario.setPassword(encoder.encode(usuario.getPassword()));

            // Registramos al jefe prestamista
            Usuario user = SUsuario.registrar(usuario);

            grupo.setJefe(user);

            if (grupo.getUbigeo() == null) {
                throw new IllegalArgumentException("Debe ingresar el ubigeo del grupo");
            }

            // Registramos el grupo
            SGrupo.registrar(grupo);

            return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);

        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	return new ResponseEntity<Mensaje>(new Mensaje("Error al registrar: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	
	
	@PutMapping("/actualizar")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<?> actualizarJefePrestamista(@Valid @RequestBody JefeDTO JefeDTO, BindingResult result) {

	    Usuario usuario = JefeDTO.getUsuario();
	    Grupo grupo = JefeDTO.getGrupo();
	    Persona persona = JefeDTO.getUsuario().getPersona();

	    if (result.hasErrors()) {
	        return new ResponseEntity<Mensaje>(new Mensaje("Error de validación"), HttpStatus.BAD_REQUEST);
	    }

	    try {
	        // Validar si el usuario existe
	        Usuario userBD = SUsuario.buscarPorId(usuario.getId());
	        if (userBD == null) {
	            return new ResponseEntity<Mensaje>(new Mensaje("El usuario no existe"), HttpStatus.NOT_FOUND);
	        }
	        System.out.println("Username: " + usuario.getUsername());
	        System.out.println("Username BD: " + userBD.getUsername());
	        // Validaciones adicionales
	        if(SUsuario.existeUsuario(usuario.getUsername()) && !userBD.getUsername().equals(usuario.getUsername())) {
	            return new ResponseEntity<Mensaje>(new Mensaje("El username ya se encuentra registrado"),HttpStatus.BAD_REQUEST);
	        }
	        if(SUsuario.existeCorreo(usuario.getCorreo()) && !userBD.getCorreo().equals(usuario.getCorreo())) {
	            return new ResponseEntity<Mensaje>(new Mensaje("El correo ya se encuentra registrado"),HttpStatus.BAD_REQUEST);
	        }
	        if(SPersona.existeCelular(usuario.getPersona().getCelular()) && !userBD.getPersona().getCelular().equals(usuario.getPersona().getCelular())) {
	            return new ResponseEntity<Mensaje>(new Mensaje("El celular ya se encuentra registrado"),HttpStatus.BAD_REQUEST);
	        }
	        if(SPersona.existeDNI(usuario.getPersona().getDni()) && !userBD.getPersona().getDni().equals(usuario.getPersona().getDni())) {
	            return new ResponseEntity<Mensaje>(new Mensaje("El DNI ya se encuentra registrado"),HttpStatus.BAD_REQUEST);
	        }

	        Usuario usu = SUsuario.buscarPorId(usuario.getId());
	        usuario.setPassword(usu.getPassword());
	        usuario.setCreate_at(LocalDate.now());
	        usuario.setEstado(usu.getEstado());
	        usuario.setRol(usu.getRol());
	        
	        Persona per = SPersona.buscarPorId(usuario.getPersona().getId());

	        per.setNombre(persona.getNombre());
	        per.setPaterno(persona.getPaterno());
	        per.setMaterno(persona.getMaterno());

	        Usuario usuarioActualizado = SUsuario.actualizar(usuario);

	        // Obtener el ID del grupo del jefe
	        Long idGrupo = SGrupo.findIdGrupoByJefe(usuarioActualizado);

	        // Buscar el grupo del jefe
	        Grupo grupoJefe = SGrupo.buscarPorId(idGrupo);

	        if (grupoJefe == null) {
	            return new ResponseEntity<Mensaje>(new Mensaje("El grupo del jefe no se encontró"), HttpStatus.NOT_FOUND);
	        }

	        grupoJefe.setUbigeo(grupo.getUbigeo()); 

	        SGrupo.actualizar(grupoJefe); 

	        JefeDTO jefeActualizado = new JefeDTO();
	        jefeActualizado.setUsuario(usuarioActualizado);
	        jefeActualizado.setGrupo(grupoJefe);

	        return new ResponseEntity<JefeDTO>(jefeActualizado, HttpStatus.OK);

	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	        return new ResponseEntity<Mensaje>(new Mensaje("Error al actualizar: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}




	@GetMapping("/listar")
	public List<Map<String, Object>> listarGrupos() {
		return procJefePrestamista.listarGrupos();
	}
	@GetMapping("/obtenerEstadistica/{idJefe}")
	public ResponseEntity<?> ObtenerEstadistica(@PathVariable long idJefe) {
		
		List<Map<String,Object>> lista = procJefePrestamista.obtenerEstadistica(idJefe);
		if(lista.size()>0) {
			return new ResponseEntity<List<Map<String, Object>>>(lista,HttpStatus.OK);
		}
		return new ResponseEntity<Mensaje>(new Mensaje("Grupo sin prestamos"),HttpStatus.BAD_REQUEST);
	}
	
	
	@DeleteMapping("/eliminar/{idUsuario}")
	public ResponseEntity<?> eliminarJefeGrupo(@PathVariable Long idUsuario) {
	    try {
	        Usuario u = SUsuario.buscarPorId(idUsuario);
	        if (u == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
	        } else {
	            procJefePrestamista.eliminarJefeGrupo(idUsuario);
	            return new ResponseEntity<Mensaje>(new Mensaje("Jefe ha sido eliminado"),HttpStatus.OK);
	        }
	    } catch (Exception e) {
	    	return new ResponseEntity<Mensaje>(new Mensaje("Jefe tiene prestamistas a su cargo"),HttpStatus.BAD_REQUEST);
	    }
	}

   
  @GetMapping("/buscar/{id}")
  public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
      Usuario usuario = SUsuario.buscarPorId(id);
      if (usuario == null) {
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
      }

      Long idGrupo = SGrupo.findIdGrupoByJefe(usuario);
      Grupo grupo = SGrupo.buscarPorId(idGrupo);

      JefeDTO dto = new JefeDTO();
      dto.setUsuario(usuario);
      dto.setGrupo(grupo);

      return new ResponseEntity<>(dto, HttpStatus.OK);
  }
  
  @GetMapping("/idUsuario/{idPersona}")
  public ResponseEntity<Long> findIdUsuarioByIdPersona(@PathVariable Long idPersona) {
      Long idUsuario = SUsuario.findIdUsuarioByIdPersona(idPersona);
      if (idUsuario != null) {
          return ResponseEntity.ok(idUsuario);
      } else {
          return ResponseEntity.notFound().build();
      }
  }

}