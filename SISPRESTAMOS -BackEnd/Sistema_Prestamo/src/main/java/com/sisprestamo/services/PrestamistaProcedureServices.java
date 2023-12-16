package com.sisprestamo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

@Service
public class PrestamistaProcedureServices {

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public PrestamistaProcedureServices(DataSource dataSources) {
		this.jdbcTemplate = new JdbcTemplate(dataSources);
	}
	
	public List<Map<String, Object>> obtenerInformacionGrupo(int idGrupo) {
	    String sql = "CALL ObtenerInformacionGrupo(?)";
	    return jdbcTemplate.queryForList(sql, idGrupo);
	}

	
	public void eliminarPrestamistaParaJefeGrupo(Long idPrestamista) {
	    String sql = "CALL EliminarPrestamistaParaJefeGrupo(?)";
	    jdbcTemplate.update(sql, idPrestamista);
	}

	
	/*
	public Map<String, Object> buscarPorIdPrestamista(Long idPrestamista) {
	    String sql = "CALL buscarPorIdPrestamista(?)";
	    return jdbcTemplate.queryForMap(sql, idPrestamista);
	}
	 */
	
	
}
