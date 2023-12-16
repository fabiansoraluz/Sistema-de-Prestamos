package com.sisprestamo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.CuentaBancaria;
import com.sisprestamo.entity.Solicitud;
import com.sisprestamo.repository.ClienteRepository;
import com.sisprestamo.repository.CuentaBancariaRepository;
import com.sisprestamo.repository.SolicitudRepository;

@Service
public class SolicitudService {
	
	@Autowired
	private SolicitudRepository ISolicitud;
	
	@Autowired
	private CuentaBancariaRepository ICuenta;
	
	@Autowired
	private ClienteRepository ICliente;
	
	//SERVICIOS DE LA SOLICITUD
	
	public List<Solicitud> listar(){
		return ISolicitud.findAll();
	}
	
	public Solicitud registrar(Solicitud bean) {
		return ISolicitud.save(bean);
	}
	
	public int modificarEstado(int estado, int id) {
		return ISolicitud.actualizarEstado(estado,id);
	}
	
	public int aprobarSolicitud(String id) {
		return ISolicitud.aprobarSolicitud(id);
	}
	
	public int rechazarSolicitud(String id) {
		return ISolicitud.rechazarSolicitud(id);
	}
	
	//SERVICIOS DE LA CUENTA
	public CuentaBancaria registrarCuenta(CuentaBancaria bean){
		return ICuenta.save(bean);
	}
	public List<CuentaBancaria> listarCuentasXClienteID(long id){
		return ICliente.listarCuentasXID(id);
	}
	
	
	
}
