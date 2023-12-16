package com.sisprestamo.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_cuota")
@Data
public class Cuota {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cuota", nullable = false, unique = true)
	private Long id;

	@Column(name = "numero", length = 12, nullable = false)
	private String numero;

	@Column(name = "monto", nullable = false)
	private Double monto;

	@Column(name = "fec_pago", nullable = false)
	private LocalDate fecPago;

	@Column(name = "estado", nullable = false)
	private Integer estado;
	
	@ManyToOne
	@JoinColumn(name="id_prestamo")
	//@JsonIgnore
	private Prestamo prestamo;
	
	@Column(name = "monto_pagado", nullable = false)
	private Double montoPagado;

}
