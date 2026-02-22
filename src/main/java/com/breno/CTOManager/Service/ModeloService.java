package com.breno.CTOManager.Service;

import com.breno.CTOManager.Entity.Modelo;
import com.breno.CTOManager.Repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository repository;

    public List<Modelo> listarTodos(){

        return repository.findAll();
    }

    public  Modelo salvar(Modelo modelo){

        return repository.save(modelo);
    }

    public void excluir(long id){

        repository.deleteById(id);
    }




}
