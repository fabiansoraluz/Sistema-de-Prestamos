package com.sisprestamo.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JefePrestamistaProcedureServices {

	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JefePrestamistaProcedureServices(DataSource dataSources) {
		this.jdbcTemplate = new JdbcTemplate(dataSources);
	}
	
	
	public List<Map<String, Object>> listarGrupos() {
	    String sql = "CALL listar_grupos()";
	    return jdbcTemplate.queryForList(sql);
	}

	public void eliminarJefeGrupo(Long idUsuario) throws SQLException{
        String sql = "CALL eliminarJefeGrupo(?)";
        jdbcTemplate.update(sql, idUsuario);
    }	
	
	
	public List<String> obtenerIdUbigeoDeGrupos() {
	    List<Map<String, Object>> listaGrupos = listarGrupos();
	    List<String> listaIdUbigeo = new ArrayList<>();

	    for (Map<String, Object> grupo : listaGrupos) {
	        String idUbigeo = (String) grupo.get("id_ubigeo");
	        listaIdUbigeo.add(idUbigeo);
	    }

	    return listaIdUbigeo;
	}

	public List<Map<String,Object>> obtenerEstadistica(long idJefe){
		String sql = "CALL obtenerEstadistica(?)";
		return jdbcTemplate.queryForList(sql,idJefe);
	}
	
	
}
