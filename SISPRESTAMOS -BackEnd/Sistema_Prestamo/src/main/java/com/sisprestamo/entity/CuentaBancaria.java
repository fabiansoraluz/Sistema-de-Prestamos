package com.sisprestamo.entity;

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
@Table(name = "tb_cuentas_bancarias")
@Data
public class CuentaBancaria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cuenta")
	private int id;

	@Column(name = "nombre",nullable = false)
	private String nombre;
	
	@Column(name = "moneda",nullable = false, length = 30)
	private String moneda;
	
	@Column(name = "banco",nullable = false, length = 45)
	private String banco;
	
	@Column(name = "numero_cuenta", unique = true, length = 20)
	private String numero;
	
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="id_tipo_cuenta")
	private TipoCuenta tipo;
}
