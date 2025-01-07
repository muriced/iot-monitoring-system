package io.github.muriced.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.github.muriced.domain.entities.Device;
import io.github.muriced.domain.dto.request.DeviceRegistrationRequest;
import io.github.muriced.domain.dto.request.DeviceUpdateRequest;
import io.github.muriced.service.DeviceService;

@RestController
@RequestMapping("/api/devices")
@Tag(name = "Devices", description = "API para gerenciamento de dispositivos")
@RequiredArgsConstructor
public class DeviceController {
    
    private final DeviceService deviceService;

    @PostMapping
    @Operation(summary = "Registra um novo dispositivo")
    public ResponseEntity<Device> registerDevice(@RequestBody @Valid DeviceRegistrationRequest request) {
        Device device = deviceService.registerDevice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(device);
    }
    
    @GetMapping
    @Operation(summary = "Lista todos os dispositivos")
    public ResponseEntity<Page<Device>> listDevices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(deviceService.getAllDevices(PageRequest.of(page, size)));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Busca um dispositivo pelo ID")
    public ResponseEntity<Device> getDevice(@PathVariable String id) {
        return deviceService.getDevice(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um dispositivo")
    public ResponseEntity<Device> updateDevice(
            @PathVariable String id,
            @RequestBody @Valid DeviceUpdateRequest request) {
        return ResponseEntity.ok(deviceService.updateDevice(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um dispositivo")
    public ResponseEntity<Void> deleteDevice(@PathVariable String id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
