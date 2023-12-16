package com.sisprestamo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sisprestamo.repository.GrupoRepository;

@Service
public class GrupoService {
	
	@Autowired
	private GrupoRepository repo;
	
	public Long findIdByPrestamista(String username) {
		return repo.findGrupoByPrestamista(username);
	}
	
	public Long findIdByJefe(String username) {
		return repo.findGrupoByJefe(username);
	}
	
	public List<Long> listarGrupos(){
		return repo.listarGrupos();
	}
	
	public List<Long> listarIdGrupoPorUbigeo(String ubigeo){
		return repo.listarIdGrupoPorUbigeo(ubigeo);
	}

	public Long obtenerIdGrupo(Long idUsuario) {
		return repo.obtenerIdGrupo(idUsuario);
	}

	 public Long obtenerIdGrupoPrestamista(Long idUsuario) {
		 return repo.obtenerIdGrupoPrestamista(idUsuario);
	 }
	
	
}
