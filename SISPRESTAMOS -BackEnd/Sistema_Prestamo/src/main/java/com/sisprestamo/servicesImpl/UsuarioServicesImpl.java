package com.sisprestamo.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.Grupo;
import com.sisprestamo.entity.Usuario;
import com.sisprestamo.repository.UsuarioRepository;

@Service
public class UsuarioServicesImpl extends ICRUDImpl<Usuario, Long>{

	@Autowired
	private UsuarioRepository repo;

	@Override
	public JpaRepository<Usuario, Long> getRepository() {
		return repo;
	}
	
	public Grupo findGroupIdsByJefeId(Long idUsuario) {
	    return repo.findIdsByJefeId(idUsuario);
	}
	
	public boolean existeUsuario(String username) {
		return repo.existsByUsername(username);
	}
	
	public boolean existeCorreo(String email) {
		return repo.existsByCorreo(email);
	}
	
	public Usuario buscarXCorreo(String correo) {
		return repo.findByCorreo(correo);
	}
	public Usuario buscarXId(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public Long findIdUsuarioByIdPersona(Long idPersona) {
	    return repo.findIdUsuarioByIdPersona(idPersona);
	}

	public String BuscarUbigeoPorIdUsuario(Long idUsuario) {
		return repo.BuscarUbigeoPorIdUsuario(idUsuario);
	}
	
	public List<Usuario> listarJefes(){
		return repo.listarJefes();
	}

}

