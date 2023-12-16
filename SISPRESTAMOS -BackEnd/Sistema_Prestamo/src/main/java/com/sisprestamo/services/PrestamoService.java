package com.sisprestamo.services;

import java.util.List;
import java.util.Map;

import com.sisprestamo.entity.Prestamo;

public interface PrestamoService {
	
	public Prestamo registrar(Prestamo prestamo);
	
	List<Map<String, Object>> obtenerInformacionPrestamos(Long idUsuario, Long idPrestamo);
	List<Map<String, Object>> obtenerInformacionPrestamosJefePrestamista(Long idPrestamo);
	List<Map<String, Object>> spListarPrestamos(Long idUsuario);
	List<Map<String, Object>> spListarPrestamosJefePrestamista(Long idUsuario);
	void actualizarMontoPagado(Long idPrestamo , Double montoIngresado);
	void actualizarMoraPagada(Long idPrestamo , Double moraIngresada);
}
