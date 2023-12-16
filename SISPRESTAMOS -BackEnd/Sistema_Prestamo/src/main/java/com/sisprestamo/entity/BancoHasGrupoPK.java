package com.sisprestamo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class BancoHasGrupoPK implements Serializable{

	private Long banco;
	private Long grupo;
	private static final long serialVersionUID = 1L;
}
