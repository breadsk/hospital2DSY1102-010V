package com.example.hospital_vm.model;

import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "atencion")
@AllArgsConstructor
@Data
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date fecha_atencion;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date hora_atencion;

    @Column(nullable = false)
    private Double costo;

    @ManyToOne
    @JoinColumn(name="id",nullable = false)
    private Paciente paciente;

    @Column(nullable = false)
    private String comentario;

}
