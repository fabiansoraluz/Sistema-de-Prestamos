package com.sisprestamo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sisprestamo.entity.Cliente;
import com.sisprestamo.entity.Persona;
import com.sisprestamo.entity.Rol;
import com.sisprestamo.entity.Usuario;
import com.sisprestamo.security.JwtDto;
import com.sisprestamo.security.JwtProvider;
import com.sisprestamo.security.LoginUsuario;
import com.sisprestamo.security.RecuperarUsuario;
import com.sisprestamo.security.UsuarioPrincipal;
import com.sisprestamo.servicesImpl.ClienteServiceImpl;
import com.sisprestamo.servicesImpl.PersonaServicesImpl;
import com.sisprestamo.servicesImpl.UsuarioServicesImpl;
import com.sisprestamo.utils.Key;
import com.sisprestamo.utils.Mensaje;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JavaMailSender mail;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
    private JwtProvider jwtProvider;
	
	@Autowired
	private ClienteServiceImpl SCliente;
	
	@Autowired
	private UsuarioServicesImpl SUsuario;
	
	@Autowired
	private PersonaServicesImpl SPersona;
	
	@Value("{spring.mail.username}")
	private String correo_envio;
	
	@PostMapping("/mail/{email}")
	public ResponseEntity<?> enviarCorreo(@PathVariable String email){
		if(SUsuario.existeCorreo(email)) {
			
			Key.generateKey();  
			SimpleMailMessage correo_entidad = new SimpleMailMessage();
			correo_entidad.setTo(email);
			correo_entidad.setFrom(correo_envio);
			correo_entidad.setSubject("Clave de Recuperación");
			correo_entidad.setText("Estimado: \n\nSe le envia su clave secreta para la recuperación de su cuenta: " + Key.getSecret_key());
			
			mail.send(correo_entidad);
			return new ResponseEntity<Mensaje>(new Mensaje("Correo enviado"),HttpStatus.OK);
		}
		return new ResponseEntity<Mensaje>(new Mensaje("Correo no asociado a ninguna cuenta"),HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/recuperar")
	public ResponseEntity<?> recuperar(@RequestBody RecuperarUsuario bean ,HttpSession sesion){
		if(Key.getSecret_key()== null) {
			return new ResponseEntity<Mensaje>(new Mensaje("Debes enviar un correo de recuperación"),HttpStatus.BAD_REQUEST);
		}
		String KEY = Key.getSecret_key();
		if(!KEY.equals(bean.getKey())) {
			return new ResponseEntity<Mensaje>(new Mensaje("Key incorrecta"),HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario = SUsuario.buscarXCorreo(bean.getEmail());
		usuario.setPassword(encoder.encode(bean.getPassword()));
		SUsuario.registrar(usuario);
		
		Key.limpiar();
		return new ResponseEntity<Mensaje>(new Mensaje("Contraseña modificada"),HttpStatus.OK);
	}
	
	@PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario,BindingResult result){

        if(result.hasErrors()) {
            return new ResponseEntity<Mensaje>(new Mensaje("Error de validación"),HttpStatus.BAD_REQUEST);
        }
        if(SUsuario.existeUsuario(usuario.getUsername())) {
            return new ResponseEntity<Mensaje>(new Mensaje("El username ya se encuentra registrado"),HttpStatus.BAD_REQUEST);
        }
        if(SUsuario.existeCorreo(usuario.getCorreo())) {
            return new ResponseEntity<Mensaje>(new Mensaje("El correo ya se encuentra registrado"),HttpStatus.BAD_REQUEST);
        }
        if(SPersona.existeCelular(usuario.getPersona().getCelular())) {
            return new ResponseEntity<Mensaje>(new Mensaje("El celular ya se encuentra registrado"),HttpStatus.BAD_REQUEST);
        }
        if(SPersona.existeDNI(usuario.getPersona().getDni())) {
            return new ResponseEntity<Mensaje>(new Mensaje("El DNI ya se encuentra registrado"),HttpStatus.BAD_REQUEST);
        }

        //Registramos a la persona
        Persona p = SPersona.registrar(usuario.getPersona());
        usuario.setPersona(p);

        //Definimos el rol cliente
        Rol rol = new Rol();
        rol.setId(4);
        usuario.setRol(rol);

        //Encriptamos el password del usuario
        usuario.setPassword(encoder.encode(usuario.getPassword()));

        //Registramos al usuario
        Usuario user = SUsuario.registrar(usuario);
        
        //Registramos al cliente
        Cliente cliente = new Cliente();
        cliente.setPersona(p);
        SCliente.registrar(cliente);

        return new ResponseEntity<Usuario>(user,HttpStatus.CREATED);
    }
	
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@RequestBody LoginUsuario loginUsuario) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getUsername(), loginUsuario.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
		
        UsuarioPrincipal usuario = (UsuarioPrincipal)authentication.getPrincipal();
        
        JwtDto jwtDto = new JwtDto(jwt, usuario.getUsername(), usuario.getIdUsuario(), usuario.getAuthorities(), usuario.getEnlaces());        
        
        return new ResponseEntity<JwtDto>(jwtDto, HttpStatus.OK);
	}
	@GetMapping("/usuario/{id}")
	public ResponseEntity<?> buscar(@PathVariable Long id){
		Usuario user = SUsuario.buscarPorId(id);
		if(user == null) {
			return new ResponseEntity<Mensaje>(new Mensaje("Usuario no registrado"),HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Usuario>(user,HttpStatus.OK);
	}
}
