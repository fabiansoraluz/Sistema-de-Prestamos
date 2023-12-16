package com.sisprestamo.servicesImpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.Cliente;
import com.sisprestamo.entity.Solicitud;
import com.sisprestamo.repository.ClienteRepository;
import com.sisprestamo.repository.SolicitudRepository;

import jakarta.transaction.Transactional;

@Service
public class SolicitudServicesImpl extends ICRUDImpl<Solicitud, String>{
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public SolicitudServicesImpl(DataSource dataSources) {
		this.jdbcTemplate = new JdbcTemplate(dataSources);
	}
	
	@Autowired
	private SolicitudRepository repo;
	
	@Autowired
	private ClienteRepository repoCliente;

	@Override
	public JpaRepository<Solicitud, String> getRepository() {
		return repo;
	}

	public String obtenerUltimoCodigo() {
        String sql = "CALL idUltimaSolicitud()";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);

        if (!result.isEmpty()) {
            Map<String, Object> row = result.get(0);
            return (String) row.get("id_solicitud");
        }

        return null;
    }
	
	public List<Map<String, Object>> obtenerSolicitudesPorGrupo(int idGrupo) {
	    String sql = "CALL ObtenerSolicitudesPorGrupo(?)";
	    return jdbcTemplate.queryForList(sql, idGrupo);
	}
	
	public List<Map<String, Object>> obtenerSolicitudesPorGrupo(int idGrupo, String nombrePrestamista, int estado) {
	    String sql = "CALL ObtenerSolicitudesFiltradas(?, ?, ?)";
	    return jdbcTemplate.queryForList(sql, idGrupo, nombrePrestamista, estado);
	}

	
	
	public List<Solicitud> obtenerSolicitudesPorCliente(Long idCliente) {
	    Cliente cliente = repoCliente.findById(idCliente).orElse(null);
        return repo.findByCliente(cliente);
	}

	public int aprobarSolicitud(String id) {
		return repo.aprobarSolicitud(id);
	}
	
	public int rechazarSolicitud(String id) {
		return repo.rechazarSolicitud(id);
	}
	
	public List<Map<String, Object>> filtrarSolicitud(long idGrupo, String nombre, int estado) {
		List<Object[]> lista = repo.FiltrarSolicitudes(idGrupo, nombre, estado);
		return convertirAResultadosMap(lista);
	}
	
	private List<Map<String, Object>> convertirAResultadosMap(List<Object[]> resultados) {
        return resultados.stream()
                .map(this::convertirObjetoArrayAMap)
                .collect(Collectors.toList());
    }
	private Map<String, Object> convertirObjetoArrayAMap(Object[] objetoArray) {
        Map<String, Object> resultadoMap = new LinkedHashMap<>();

        // Supongamos que el primer elemento en el array es el id_prestamo, el segundo es el nombre, etc.
        resultadoMap.put("id_solicitud", objetoArray[0]);
        resultadoMap.put("prestatario", objetoArray[1]);
        resultadoMap.put("monto", objetoArray[2]);
        resultadoMap.put("fec_solicitud", objetoArray[3]);
        resultadoMap.put("banco", objetoArray[4]);
        resultadoMap.put("estado", objetoArray[5]);
        

        // Agrega más mapeos según sea necesario

        return resultadoMap;
    }
}
