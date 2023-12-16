package com.sisprestamo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tb_usuario")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario", nullable = false)
    private long id;

    @NotEmpty
    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @NotEmpty
    @Column(name = "password", length = 70, nullable = false)
    private String password;

    @NotEmpty
    @Column(name = "image", length = 50, nullable = false)
    private String image;

    @NotEmpty
    @Email
    @Column(name="correo", length = 50, nullable = false)
    private String correo;

    @Column(name = "create_at", nullable = false)
    private LocalDate create_at;

    @Column(name = "estado", nullable = false)
    private Integer estado;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Rol rol;
 
    @OneToOne
	@JoinColumn(name = "id_persona")
    @NotNull
    @Valid
	private Persona persona;

    @PrePersist
    private void prePersist() {
    	this.create_at = LocalDate.now();
    	this.estado = 1;
    }
}

