package com.sisprestamo.entity;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "tb_prestamista")
@Data
public class Prestamista {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id_prestamista" )
	 private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "id_grupo")
	 @NotNull
	 private Grupo grupo;
	 
	 @ManyToOne
	 @JoinColumn(name = "id_usuario")
	 @Valid
	 private Usuario usuario;
	
}
