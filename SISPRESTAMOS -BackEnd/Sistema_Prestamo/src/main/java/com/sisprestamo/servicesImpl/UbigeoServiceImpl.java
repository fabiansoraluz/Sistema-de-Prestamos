package com.sisprestamo.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.Ubigeo;
import com.sisprestamo.repository.UbigeoRepository;
import com.sisprestamo.utils.DistritoDTO;

@Service
public class UbigeoServiceImpl extends ICRUDImpl<Ubigeo, String> {

	@Autowired
	private UbigeoRepository repo;

	@Override
	public JpaRepository<Ubigeo, String> getRepository() {
		return repo;
	}
	
	public List<String> listarDep() {
		return repo.listarDepartamentos();
	}

	public List<String> listarProvXDep(String departamento) {
		return repo.listarProvinciasXDepartamento(departamento);
	}

	public List<DistritoDTO> listarDisXProv(String provincia) {
		return repo.listarDistritosXProvincia(provincia);
	}



}
