package io.github.muriced.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import io.github.muriced.domain.dto.response.DashboardSummary;
import io.github.muriced.domain.dto.response.DashboardMetrics;
import io.github.muriced.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "Dashboard", description = "API para dados do dashboard")
@RequiredArgsConstructor
public class DashboardController {
    
    private final DashboardService dashboardService;

    @GetMapping("/summary")
    @Operation(summary = "Obtém resumo geral do sistema")
    public ResponseEntity<DashboardSummary> getDashboardSummary() {
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }

    @GetMapping("/metrics")
    @Operation(summary = "Obtém métricas em tempo real")
    public Flux<DashboardMetrics> streamMetrics() {
        return dashboardService.streamMetrics();
    }
}
