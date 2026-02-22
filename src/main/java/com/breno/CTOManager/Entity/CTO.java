package com.breno.CTOManager.Entity;

import jakarta.persistence.*;

@Entity
@Table
public class CTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    private Double latitude;
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "modelo_id")
    private Modelo modelo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    public CTO() {
    }

    public CTO(Long id, String nome, Double latitude, Double longitude, Modelo modelo, Sensor sensor) {
        this.id = id;
        this.nome = nome;
        this.latitude = latitude;
        this.longitude = longitude;
        this.modelo = modelo;
        this.sensor = sensor;
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

    public Double getLatitude() {

        return latitude;
    }

    public void setLatitude(Double latitude) {

        this.latitude = latitude;
    }

    public Double getLongitude() {

        return longitude;
    }

    public void setLongitude(Double longitude) {

        this.longitude = longitude;
    }

    public Modelo getModelo() {

        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Sensor getSensor() {

        return sensor;
    }

    public void setSensor(Sensor sensor) {

        this.sensor = sensor;
    }
}