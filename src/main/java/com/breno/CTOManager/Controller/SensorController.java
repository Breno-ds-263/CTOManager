package com.breno.CTOManager.Controller;

import com.breno.CTOManager.Entity.Sensor;
import com.breno.CTOManager.Service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensores")
@CrossOrigin("*")
public class SensorController {

    @Autowired
    private SensorService service;

    @GetMapping
    public List<Sensor> listar() {
        return service.Listar();
    }

    @PostMapping
    public Sensor salvar(@RequestBody Sensor sensor) {
        return service.salvar(sensor);
    }
}