package com.breno.CTOManager.Controller;

import com.breno.CTOManager.Entity.Modelo;
import com.breno.CTOManager.Service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@CrossOrigin("*")
public class ModeloController {

    @Autowired
    private ModeloService service;

    @GetMapping
    public List<Modelo> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public Modelo salvar(@RequestBody Modelo modelo) {
        return service.salvar(modelo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}