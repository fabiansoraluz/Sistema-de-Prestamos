package com.sisprestamo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_banco_has_grupo")
@IdClass(value = BancoHasGrupoPK.class)
@Data
public class BancoHasGrupo {

	@Id
	@ManyToOne
	@JoinColumn(name = "id_banco", referencedColumnName = "id_banco")    
	private Banco banco;

	@Id
	@ManyToOne
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")
    private Grupo grupo;
	
	@Column(name = "cuentasoles", nullable = false)
	private String soles;
	
	@Column(name = "cuentadolares", nullable = false)
	private String dolares;
	
	@Column(name = "cuentasolescci", nullable = false)
	private String solescci;
	
	@Column(name = "cuentadolarescci", nullable = false)
	private String dolarescci;
	
}
