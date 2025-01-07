package io.github.muriced.simulation;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableScheduling
@Profile("simulation") // Para ativar apenas com profile de simulação //
public class SimulationConfig {
    // Se quiser por alguma configuração específica para simulação... //
}
