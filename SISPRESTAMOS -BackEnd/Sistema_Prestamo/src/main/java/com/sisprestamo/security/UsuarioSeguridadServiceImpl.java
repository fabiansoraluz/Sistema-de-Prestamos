package com.sisprestamo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.Enlace;
import com.sisprestamo.entity.Rol;
import com.sisprestamo.entity.Usuario;
import com.sisprestamo.repository.UsuarioRepository;

@Service
public class UsuarioSeguridadServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		UserDetails userDetails = null;
		try {
			Usuario objUsuario = usuarioRepository.findByUsername(login);
			if (objUsuario != null) {
				List<Rol> lstRol = usuarioRepository.traerRolesDeUsuario((int) objUsuario.getId());
				List<Enlace> lstOpciones = usuarioRepository.traerEnlacesDeUsuario((int) lstRol.get(0).getId());

				userDetails = UsuarioPrincipal.build(objUsuario, lstRol, lstOpciones);
			}
		} catch (IndexOutOfBoundsException e) {
			throw new UsernameNotFoundException("Wrong username");
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Database Error");
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Unknown Error");
		}
		return userDetails;
	}
}
