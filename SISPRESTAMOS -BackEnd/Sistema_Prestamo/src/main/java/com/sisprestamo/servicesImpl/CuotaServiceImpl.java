package com.sisprestamo.servicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.Cuota;
import com.sisprestamo.entity.Prestamo;
import com.sisprestamo.repository.CuotaRepository;
import com.sisprestamo.services.CuotaService;

@Service
public class CuotaServiceImpl implements CuotaService {

	@Autowired
	private CuotaRepository repository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Map<String, Object> obtenerInformacionCuotas(Long idCuota) {
		String sql = "CALL ObtenerInformacionCuotas(?)";
		return jdbcTemplate.queryForMap(sql, idCuota);
	}

	@Override
	public void pagarCuota(Long idCuota, Double nuevoMontoPagado, Integer nuevoEstado) {
		String sql = "CALL registrar_pago_cuota(?, ?, ?)";
		jdbcTemplate.update(sql, idCuota, nuevoMontoPagado, nuevoEstado);
	}

	@Override
	public List<Cuota> registrar(Prestamo prestamo) {
		List<Cuota> listaCuotas = new ArrayList<>();
		int numeroCuotas = prestamo.getSolicitud().getDias();
		LocalDate fechaInicio = prestamo.getSolicitud().getFechaSolicitud();

		int interes = obtenerInteres(numeroCuotas);
		double montoPrestamo = prestamo.getSolicitud().getMonto();
		double montoInteres = (montoPrestamo * interes) / 100;
		double totalPrestamo = montoInteres + montoPrestamo;
		double montoPorCuota = totalPrestamo / numeroCuotas;

		for (int x = 1; x <= numeroCuotas; x++) {
			Cuota nueva = new Cuota();
			nueva.setNumero(String.valueOf(x));
			nueva.setPrestamo(prestamo);
			nueva.setEstado(0); // pendiente
			nueva.setMonto(montoPorCuota);
			nueva.setFecPago(fechaInicio.plusDays(x));
			nueva.setMontoPagado(0.0);
			nueva = repository.save(nueva);
			listaCuotas.add(nueva);
		}

		return listaCuotas;
	}

	private int obtenerInteres(int cantidadCuotas) {
		switch (cantidadCuotas) {
		case 10:
			return 5;
		case 15:
			return 7;
		case 20:
			return 10;
		case 25:
			return 12;
		default:
			return 15;
		}
	}

	@Override
	public List<Cuota> listarTodos() {
		return repository.findAll();
	}

	@Override
	public Long findPrestamoIdByCuotaId(Long idCuota) {
		Prestamo prestamo = repository.findPrestamoByCuotaId(idCuota);
		return prestamo != null ? prestamo.getId() : null;
	}
	
	@Override
	public List<Map<String, Object>> listarXIDCliente(long IdCliente) {
        String sql = "CALL ObtenerCuotasXClienteID(?)";
        return jdbcTemplate.queryForList(sql,IdCliente);
    }

}
