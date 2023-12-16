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
public class CuotaDTO {

	private Long id;
	
	private String numero;
	
	private Double monto;
	
	private LocalDate fechaPago;
	
	private Integer estado;
	
}
