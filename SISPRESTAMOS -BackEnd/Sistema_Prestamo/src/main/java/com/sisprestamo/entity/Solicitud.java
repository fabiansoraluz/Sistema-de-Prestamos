package com.sisprestamo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "tb_solicitud")
@Data
public class Solicitud {

	@Id
    @Column(name = "id_solicitud")
    private String id;

	@NotNull
    @Column(name = "dias",  nullable = false )
    private Integer dias;

	@NotNull
    @Column(name = "monto", nullable = false)
    private Double monto;

	@Column(name = "observacion")
	private String observacion;
	
    @Column(name = "fec_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "estado", length = 30, nullable = false)
    private int estado;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_cuenta")
    private CuentaBancaria cuenta;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    @PrePersist
    private void prePersist() {
		//1 = pendiente
		//2 = aprobado
		//3 = rechazado (puede nueva solicitud para la siguiente fecha)
    	this.fechaSolicitud = LocalDate.now();
    	this.estado = 1;
    }
    
}

