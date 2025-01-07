package io.github.muriced.domain.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String id) {
        super("Dispositivo não encontrado com o id: " + id);
    }
}
