package com.sisprestamo.services;

import java.util.List;
import java.util.Map;

import com.sisprestamo.entity.Cuota;
import com.sisprestamo.entity.Prestamo;

public interface CuotaService {

	public List<Cuota> registrar(Prestamo prestamo);
	
	List<Cuota> listarTodos();
	
	Map<String, Object> obtenerInformacionCuotas(Long idCuota);
	
	void pagarCuota(Long idCuota, Double nuevoMontoPagado, Integer nuevoEstado);

	Long findPrestamoIdByCuotaId(Long idCuota);

	List<Map<String, Object>> listarXIDCliente(long idCliente);
}
