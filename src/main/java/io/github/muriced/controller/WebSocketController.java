package io.github.muriced.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;

import io.github.muriced.domain.entities.Alert;
import io.github.muriced.domain.entities.SensorReading;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    
    private final SimpMessagingTemplate messagingTemplate;
    
    @MessageMapping("/device-data")
    @SendTo("/topic/readings")
    public SensorReading handleDeviceData(SensorReading reading) {
        return reading;
    }
    
    public void sendAlert(Alert alert) {
        messagingTemplate.convertAndSend("/topic/alerts", alert);
    }
}
