package com.breno.CTOManager.Entity;

import jakarta.persistence.*;


@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome", "fabricante"})
})
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String fabricante;

    public Modelo() {
    }

    public Modelo(Long id, String nome, String fabricante) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }


}
