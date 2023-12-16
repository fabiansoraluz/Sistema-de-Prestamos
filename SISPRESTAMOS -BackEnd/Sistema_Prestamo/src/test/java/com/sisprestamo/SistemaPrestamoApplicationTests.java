package com.sisprestamo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sisprestamo.repository.SolicitudRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
class SistemaPrestamoApplicationTests {
	
	@Autowired
	private SolicitudRepository RSolicitud;
	
	@Test
	@Transactional
	void contextLoads() {
		System.out.println(RSolicitud.FiltrarSolicitudes(1,"",2));
	}
	
	

}
