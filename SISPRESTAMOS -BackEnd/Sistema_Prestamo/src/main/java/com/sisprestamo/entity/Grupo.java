package com.sisprestamo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_grupo")
@Data
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo", nullable = false)
    private Long id;

    @Column(name = "Estado")
    private int estado;

    @Column(name = "Create_at")
    private LocalDate create_at;

    @ManyToOne
    @JoinColumn(name = "id_jefe")
    private Usuario jefe;

    @PrePersist
    private void prePersist() {
    	this.create_at=LocalDate.now();
    	this.estado=1;
    }

    @OneToOne
    @JoinColumn(name = "id_ubigeo", unique = true)
    private Ubigeo ubigeo;
}
