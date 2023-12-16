package com.sisprestamo.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.Grupo;
import com.sisprestamo.entity.Ubigeo;
import com.sisprestamo.entity.Usuario;
import com.sisprestamo.repository.GrupoRepository;

@Service
public class GrupoServicesImpl extends ICRUDImpl<Grupo, Long> {

	@Autowired
	private GrupoRepository repo;

	@Override
	public JpaRepository<Grupo, Long> getRepository() {
		return repo;
	}

	public Long findIdGrupoByJefe(Usuario usuario) {
		return repo.findIdByJefe(usuario);
	}

	public boolean existeUbigeo(Ubigeo ubigeo) {
		return repo.existsByUbigeo(ubigeo);
	}


}
