package com.sisprestamo.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.sisprestamo.entity.CuentaBancaria;
import com.sisprestamo.entity.TipoCuenta;
import com.sisprestamo.repository.CuentaBancariaRepository;
import com.sisprestamo.repository.TipoCuentaRepository;

@Service
public class CuentaBancariaServiceImpl extends ICRUDImpl<CuentaBancaria, Integer>{

	@Autowired
	private CuentaBancariaRepository repo;
	@Autowired
	private TipoCuentaRepository RTipo;
	
	@Override
	public JpaRepository<CuentaBancaria, Integer> getRepository() {
		return repo;
	}
	
    public boolean existsByNumeroCuenta(String numeroCuenta) {
        return repo.existsByNumero(numeroCuenta);
    }

    public boolean existsByNombre(String nombre) {
        return repo.existsByNombre(nombre);
    }

    public boolean numeroCuentaExiste(String numeroCuenta) {
        return existsByNumeroCuenta(numeroCuenta);
    }

    public boolean nombreCuentaExiste(String nombreCuenta) {
        return existsByNombre(nombreCuenta);
    }
    
	public List<CuentaBancaria> listarXIDUsuario(long id){
		return repo.listarXIDUsuario(id);
	}
	
	public List<TipoCuenta> listarTipos(){
		return RTipo.findAll();
	}

}
