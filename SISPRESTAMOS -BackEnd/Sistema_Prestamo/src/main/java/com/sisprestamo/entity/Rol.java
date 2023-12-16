package com.sisprestamo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_rol")
@Data
public class Rol {

    @Id
    @Column(name="id_rol", nullable = false, unique = true)
    private long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;    

}
