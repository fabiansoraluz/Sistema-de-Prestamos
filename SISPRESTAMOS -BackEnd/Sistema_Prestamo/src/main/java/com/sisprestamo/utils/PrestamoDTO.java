package com.sisprestamo.utils;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoDTO {

	private Long id;
	
	private LocalDate fechaInicio;
	
	private LocalDate fechaFin;
	
	private int estado;
}
