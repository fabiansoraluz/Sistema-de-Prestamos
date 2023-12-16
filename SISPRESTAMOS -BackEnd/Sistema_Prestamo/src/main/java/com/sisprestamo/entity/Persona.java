package com.sisprestamo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "tb_persona")
@Data
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_persona", nullable = false)
	private long id;

	@NotEmpty
	@Column(name = "nombre", length = 50, nullable = false)
	private String nombre;

	@NotEmpty
	@Column(name = "paterno", length = 50, nullable = false)
	private String paterno;

	@Column(name = "materno", length = 50, nullable = false)
	private String materno;

	@NotEmpty
	@Column(name = "celular", length = 9, nullable = false)
	private String celular;

	@NotEmpty
	@Column(name = "dni", length = 8, nullable = false, unique = true)
	private String dni;

	@ManyToOne
	@JoinColumn(name = "id_ubigeo")
	@NotNull
	@Valid
	private Ubigeo ubigeo;
}
