package com.rutaia.backend.exception;

// Se lanza cuando se viola una regla de negocio (correo duplicado, curso inactivo, etc.)
public class BusinessException extends RuntimeException {
    public BusinessException(String mensaje) {
        super(mensaje);
    }
}
