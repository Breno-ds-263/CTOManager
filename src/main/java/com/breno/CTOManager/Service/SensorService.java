package com.breno.CTOManager.Service;

import com.breno.CTOManager.Entity.Sensor;
import com.breno.CTOManager.Entity.StatusSensor;
import com.breno.CTOManager.Repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SensorService {

    @Autowired
    private SensorRepository repository;

    public List<Sensor> Listar(){

        return repository.findAll();
    }

    public Sensor salvar(Sensor sensor){
        return repository.save(sensor);
    }

    public Sensor atualizarStatus(Long id, StatusSensor novoStatus, String causa){
        Sensor sensor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sensor não encontrado"));

        sensor.setStatus(novoStatus);
        sensor.setCausa(causa);
        sensor.setDataEvento(LocalDateTime.now());

        return repository.save(sensor);

    }

    public void excluir(Long id){
        repository.deleteById(id);
    }
}
