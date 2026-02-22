package com.breno.CTOManager.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataEvento;


    private String causa;

    @Enumerated(EnumType.STRING)
    private StatusSensor status;

    public Sensor() {
    }

    public Sensor(Long id, LocalDateTime dataEvento, String causa, StatusSensor status) {
        this.id = id;
        this.dataEvento = dataEvento;
        this.causa = causa;
        this.status = status;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDateTime dataEvento) {
        this.dataEvento = dataEvento;
    }

    public String getCausa() {

        return causa;
    }

    public void setCausa(String causa) {

        this.causa = causa;
    }

    public StatusSensor getStatus() {

        return status;
    }

    public void setStatus(StatusSensor status) {

        this.status = status;
    }
}
