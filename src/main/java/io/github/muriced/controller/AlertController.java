package io.github.muriced.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import io.github.muriced.domain.entities.Alert;
import io.github.muriced.service.AlertService;
import io.github.muriced.domain.event.AlertEvent;

@RestController
@RequestMapping("/api/alerts")
@Tag(name = "Alerts", description = "API para gerenciamento de alertas")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @PostMapping
    @Operation(summary = "Cria um novo alerta")
    public ResponseEntity<Alert> createAlert(AlertEvent event) {
        return ResponseEntity.ok(alertService.createAlert(event));
    }

    @GetMapping("/active")
    @Operation(summary = "Lista alertas ativos")
    public ResponseEntity<List<Alert>> getActiveAlerts() {
        return ResponseEntity.ok(alertService.getActiveAlerts());
    }

    @PostMapping("/{alertId}/acknowledge")
    @Operation(summary = "Confirma recebimento de um alerta")
    public ResponseEntity<Alert> acknowledgeAlert(@PathVariable String alertId) {
        return ResponseEntity.ok(alertService.acknowledgeAlert(alertId));
    }
}