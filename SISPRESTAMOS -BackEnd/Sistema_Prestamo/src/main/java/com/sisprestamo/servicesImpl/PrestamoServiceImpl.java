package com.sisprestamo.servicesImpl;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sisprestamo.entity.Prestamo;
import com.sisprestamo.repository.PrestamoRepository;
import com.sisprestamo.services.PrestamoService;

@Service
public class PrestamoServiceImpl implements PrestamoService {

	@Autowired
	private PrestamoRepository repository;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
    public void actualizarMontoPagado(Long idPrestamo , Double montoIngresado) {
        String sql = "CALL actualizarMontoPagado(?, ?)";
        jdbcTemplate.update(sql, idPrestamo, montoIngresado);
    }
	
	@Override
    public void actualizarMoraPagada(Long idPrestamo , Double moraIngresada) {
        String sql = "CALL actualizarMoraPagada(?, ?)";
        jdbcTemplate.update(sql, idPrestamo, moraIngresada);
    }
	
	
	@Override
    @Transactional
    public List<Map<String, Object>> spListarPrestamos(Long idUsuario) {
        List<Object[]> resultados = repository.spListarPrestamos(idUsuario);
        return convertirAResultadosMapPrestamo(resultados);
    }
	
	@Override
    @Transactional
    public List<Map<String, Object>> spListarPrestamosJefePrestamista(Long idUsuario) {
        List<Object[]> resultados = repository.spListarPrestamosJefePrestamista(idUsuario);
        return convertirAResultadosMapPrestamoJefePrestamista(resultados);
    }
	
	private List<Map<String, Object>> convertirAResultadosMapPrestamo(List<Object[]> resultados) {
        return resultados.stream()
                .map(this::convertirObjetoArrayAMapPrestamo)
                .collect(Collectors.toList());
    }
	
	private List<Map<String, Object>> convertirAResultadosMapPrestamoJefePrestamista(List<Object[]> resultados) {
        return resultados.stream()
                .map(this::convertirObjetoArrayAMapPrestamoJefePrestamista)
                .collect(Collectors.toList());
    }
	
	private Map<String, Object> convertirObjetoArrayAMapPrestamo(Object[] objetoArray) {
        Map<String, Object> resultadoMap = new LinkedHashMap<>();

        // Supongamos que el primer elemento en el array es el id_prestamo, el segundo es el nombre, etc.
        resultadoMap.put("id_prestamo", objetoArray[0]);
        resultadoMap.put("nombre_cliente", objetoArray[1]);
        resultadoMap.put("monto_prestamo", objetoArray[2]);
        resultadoMap.put("numero_cuotas", objetoArray[3]);
        resultadoMap.put("estado_prestamo", objetoArray[4]);
        

        // Agrega más mapeos según sea necesario

        return resultadoMap;
    }
	
	private Map<String, Object> convertirObjetoArrayAMapPrestamoJefePrestamista(Object[] objetoArray) {
        Map<String, Object> resultadoMap = new LinkedHashMap<>();

        // Supongamos que el primer elemento en el array es el id_prestamo, el segundo es el nombre, etc.
        resultadoMap.put("id_prestamo", objetoArray[0]);
        resultadoMap.put("nombre_cliente", objetoArray[1]);
        resultadoMap.put("monto_prestamo", objetoArray[2]);
        resultadoMap.put("monto_interes", objetoArray[3]);
        resultadoMap.put("numero_cuotas", objetoArray[4]);
        resultadoMap.put("estado_prestamo", objetoArray[5]);
        

        // Agrega más mapeos según sea necesario

        return resultadoMap;
    }
	
	@Override
    @Transactional
    public List<Map<String, Object>> obtenerInformacionPrestamos(Long idUsuario, Long idPrestamo) {
        List<Object[]> resultados = repository.obtenerInformacionPrestamos(idUsuario, idPrestamo);
        return convertirAResultadosMap(resultados);
    }
	
	@Override
	@Transactional
	public List<Map<String, Object>> obtenerInformacionPrestamosJefePrestamista(Long idPrestamo) {
		List<Object[]> resultados = repository.obtenerInformacionPrestamosJefePrestamista(idPrestamo);
        return convertirAResultadosMap(resultados);
	}
	
	private List<Map<String, Object>> convertirAResultadosMap(List<Object[]> resultados) {
        return resultados.stream()
                .map(this::convertirObjetoArrayAMap)
                .collect(Collectors.toList());
    }
	
	private Map<String, Object> convertirObjetoArrayAMap(Object[] objetoArray) {
        Map<String, Object> resultadoMap = new LinkedHashMap<>();

        // Supongamos que el primer elemento en el array es el id_prestamo, el segundo es el nombre, etc.
        resultadoMap.put("id_prestamo", objetoArray[0]);
        resultadoMap.put("id_cuota", objetoArray[1]);
        resultadoMap.put("nombre", objetoArray[2]);
        resultadoMap.put("numero", objetoArray[3]);
        resultadoMap.put("monto", objetoArray[4]);
        resultadoMap.put("monto_pagado", objetoArray[5]);
        resultadoMap.put("fecha_vencimiento", objetoArray[6]);
        resultadoMap.put("estado", objetoArray[7]);
        

        // Agrega más mapeos según sea necesario

        return resultadoMap;
    }
	
	@Override
	public Prestamo registrar(Prestamo prestamo) {
		prestamo.setEstado(1); // aprobado
		
		LocalDate fechaInicio = LocalDate.now();
		LocalDate fechaFin = fechaInicio.plusDays(prestamo.getSolicitud().getDias()).minusDays(1);
		
		prestamo.setFecha_Inicio_prestamo(fechaInicio);
		prestamo.setFecha_Fin_prestamo(fechaFin);
		
		return repository.save(prestamo);
	}

}
