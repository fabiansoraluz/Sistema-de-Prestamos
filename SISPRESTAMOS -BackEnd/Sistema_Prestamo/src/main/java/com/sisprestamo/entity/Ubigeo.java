package com.sisprestamo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "tb_ubigeo")
@Data
public class Ubigeo {
	@Id
	@Column(name="id_ubigeo", length = 6, nullable = false, unique = true)
	@NotEmpty
	private String id;
	
	@Column(name="departamento", length = 50, nullable = false)
	private String departamento;
	
	@Column(name="provincia", length = 50, nullable = false)
	private String provincia;
	
	@Column(name="distrito", length = 50, nullable = false)
	private String distrito;
	
	
	@OneToOne(mappedBy = "ubigeo")
	@JsonIgnore
    private Grupo grupo;
}
