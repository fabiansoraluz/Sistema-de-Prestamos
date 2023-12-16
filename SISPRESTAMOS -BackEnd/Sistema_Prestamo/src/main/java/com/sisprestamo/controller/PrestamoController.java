package com.sisprestamo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

import com.sisprestamo.services.PrestamoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(originPatterns = "*")
public class PrestamoController {
	@Autowired
    private PrestamoService prestamoService;

	@GetMapping("/obtenerInformacion")
    @Transactional
    public List<Map<String, Object>> obtenerInformacionPrestamos(
        @RequestParam Long idUsuario,
        @RequestParam(required = false) Long idPrestamo
    ) {
        return prestamoService.obtenerInformacionPrestamos(idUsuario, idPrestamo);
    }
	
	@GetMapping("/obtenerInformacionJefePrestamista")
    @Transactional
    public List<Map<String, Object>> obtenerInformacionPrestamosJefePrestamista(
        @RequestParam Long idPrestamo
    ) {
        return prestamoService.obtenerInformacionPrestamosJefePrestamista(idPrestamo);
    }
    
    @GetMapping("/listar")
    @Transactional
    public List<Map<String, Object>> listarPrestamos(@RequestParam Long idUsuario) {
    	return prestamoService.spListarPrestamos(idUsuario);
    }
    
    @GetMapping("/listarPorJefePrestamista")
    @Transactional
    public List<Map<String, Object>> listarPrestamosPorJefePrestamista(@RequestParam Long idUsuario) {
    	return prestamoService.spListarPrestamosJefePrestamista(idUsuario);
    }
    
    @PutMapping("/actualizarMontoPagado")
    public void actualizarMontoPagado(@RequestParam Long idPrestamo, @RequestParam Double montoIngresado) {
    	prestamoService.actualizarMontoPagado( idPrestamo ,  montoIngresado);
    }
    
    @PutMapping("/actualizarMoraPagada")
    public void actualizarMoraPagada(@RequestParam Long idPrestamo, @RequestParam Double moraIngresada) {
    	prestamoService.actualizarMoraPagada( idPrestamo ,  moraIngresada);
    }
}
