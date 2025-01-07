package io.github.muriced.config;

import org.springframework.context.annotation.Configuration;
import java.util.Map;

@Configuration
public class ThresholdConfig {
    
    public Map<String, Double> criticalThresholds() {
        return Map.of(
            "temperature", 35.0,
            "humidity", 20.0
        );
    }

    public Map<String, Double> warningThresholds() {
        return Map.of(
            "temperature", 30.0,
            "humidity", 30.0
        );
    }
}
