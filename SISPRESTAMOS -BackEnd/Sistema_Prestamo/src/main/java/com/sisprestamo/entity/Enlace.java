package com.sisprestamo.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_enlace")
@Data
public class Enlace {
	@Id
	@Column(name="id_enlace", nullable = false, unique = true)
	private long id;
	
	@Column(name = "descripcion", length = 50, nullable = false)
	private String descripcion;
	
	@Column(name = "ruta", length = 50, nullable = false)
	private String ruta;
	
	@Column(name = "estado", nullable = false)
	private Integer estado;
	

}
