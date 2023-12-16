package com.sisprestamo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sisprestamo.entity.Cuota;
import com.sisprestamo.services.CuotaService;
import com.sisprestamo.servicesImpl.CuotaServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cuotas")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class CuotaController {

	@Autowired
    private CuotaService cuotaService;
	
	@Autowired
    private CuotaServiceImpl ScuotaImpl;
	
    @GetMapping("/listar")
    public List<Cuota> listarCuotas() {
        return ScuotaImpl.listarTodos();
    }

    @GetMapping("/obtenerInformacion")
    public Map<String, Object> obtenerInformacionCuotas(@RequestParam Long idCuota) {
        return cuotaService.obtenerInformacionCuotas(idCuota);
    }
    
    @PutMapping("/pagarCuota")
    public void pagarCuota(@RequestParam Long idCuota, @RequestParam Double nuevoMontoPagado, @RequestParam Integer nuevoEstado) {
        cuotaService.pagarCuota(idCuota, nuevoMontoPagado, nuevoEstado);
    }
    
    @GetMapping("/listarXIDCliente/{idCliente}")
    public List<Map<String, Object>> listarXIDCliente(@PathVariable long idCliente) {
        return cuotaService.listarXIDCliente(idCliente);
    }
}
