package com.breno.CTOManager.Service;


import com.breno.CTOManager.Entity.CTO;
import com.breno.CTOManager.Entity.Sensor;
import com.breno.CTOManager.Entity.StatusSensor;
import com.breno.CTOManager.Repository.CTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CTOService {

    @Autowired
    private CTORepository repository;

    public List<CTO> listarTodos() {
        return repository.findAll();
    }

    public CTO salvar(CTO cto) {
        if (cto.getSensor() == null) {
            Sensor novoSensor = new Sensor();
            novoSensor.setStatus(StatusSensor.NAO_ALARMADO);
            cto.setSensor(novoSensor);
        }

        if (repository.existsByNome(cto.getNome())) {
            throw new RuntimeException("Já existe uma CTO com esse nome.");
        }


        return repository.save(cto);
    }

    public void excluir(Long id) {
        CTO cto = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("CTO não encontrada"));

        if (cto.getSensor() != null && cto.getSensor().getStatus() == StatusSensor.ALARMADO) {
            throw new RuntimeException("Erro: Não é permitido excluir uma CTO com status ALARMADO.");
        }

        repository.delete(cto);
    }


    public void atualizarStatusSns(String nomeCto, StatusSensor novoStatus, String causa) {
        CTO cto = repository.findByNome(nomeCto)
                .orElseThrow(() -> new RuntimeException("CTO não encontrada para o alarme"));

        cto.getSensor().setStatus(novoStatus);
        cto.getSensor().setCausa(causa);
        cto.getSensor().setDataEvento(java.time.LocalDateTime.now());

        repository.save(cto);
    }



}


