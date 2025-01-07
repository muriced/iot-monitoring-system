package io.github.muriced.domain.exception;

public class InvalidSensorReadingException extends RuntimeException {
    public InvalidSensorReadingException(String message) {
        super(message);
    }
}
