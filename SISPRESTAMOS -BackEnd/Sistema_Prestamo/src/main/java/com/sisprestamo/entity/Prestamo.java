package com.sisprestamo.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_prestamo")
@Data
public class Prestamo {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id_prestamo", nullable = false, unique = true)
    private Long id;

	@Column(name = "monto", nullable = false)
	private Double monto;
	
	@Column(name = "monto_pagado", nullable = false)
	private Double montoPagado;
	
	@Column(name = "interes_agregado", nullable = false)
	private Double interesAgregado;
	
	@Column(name = "mora_pagada", nullable = false)
	private Double moraPagada;
	
    //Implementar una logica, si estado es rechazado, que inicio y fin prestamos muestre rechazado
    @Column(name = "fecha_inicio_prestamo", nullable = false)
    private LocalDate fecha_Inicio_prestamo;

    @Column(name = "fecha_fin_prestamo", nullable = false)
    private LocalDate fecha_Fin_prestamo;

    @Column(name = "estado", length = 30, nullable = false)
    private int estado;

    //Aqui implementar logica, cobro de L-V, L-S, L-M-V, M-J-S, segun la cantidad de cuotas, 
    //se le cobra una tarifa, esa se asignara aqui

    @ManyToOne
    @JoinColumn(name = "id_prestamista")
    private Prestamista prestamista;

    @OneToOne
    @JoinColumn(name = "id_solicitud")
    private Solicitud solicitud;

    @OneToMany(mappedBy = "prestamo")
    @JsonIgnore
    private List<Cuota> cuotas;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;
}
