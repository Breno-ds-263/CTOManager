package com.breno.CTOManager.Controller;

import com.breno.CTOManager.Entity.CTO;
import com.breno.CTOManager.Entity.StatusSensor;
import com.breno.CTOManager.Service.CTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ctos")
@CrossOrigin("*")
public class CTOController {

    @Autowired
    private CTOService service;

    @GetMapping
    public List<CTO> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public CTO salvar(@RequestBody CTO cto) {
        return service.salvar(cto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping(value = "/sns", consumes = "*/*")
    public ResponseEntity<String> receberNotificacaoSns(@RequestBody String rawPayload) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> payload = mapper.readValue(rawPayload, Map.class);

            String type = (String) payload.get("Type");

            if ("SubscriptionConfirmation".equals(type)) {
                String subscribeURL = (String) payload.get("SubscribeURL");

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getForObject(subscribeURL, String.class);

                System.out.println("Subscription confirmada com sucesso!");
                return ResponseEntity.ok("Subscription confirmada");
            }

            if ("Notification".equals(type)) {

                String message = (String) payload.get("Message");

                if (message != null) {
                    System.out.println("Mensagem recebida: " + message);

                    String[] partes = message.split(":");
                    if (partes.length >= 2) {
                        String nomeCto = partes[0];
                        String statusStr = partes[1];
                        String causa = partes.length > 2 ? partes[2] : "Alarme via SNS";

                        StatusSensor status = statusStr.equalsIgnoreCase("ALARMADO")
                                ? StatusSensor.ALARMADO
                                : StatusSensor.NAO_ALARMADO;

                        service.atualizarStatusSns(nomeCto, status, causa);
                    }
                }

                return ResponseEntity.ok("Mensagem processada");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao processar");
        }

        return ResponseEntity.ok("Ignorado");
    }
}