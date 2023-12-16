package com.sisprestamo.utils;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoResponse {

	private Mensaje mensaje;
	private PrestamoDTO prestamo;
	private List<CuotaDTO> cuotas;
	
}
