package com.rutaia.backend.exception;

// Se lanza cuando se busca un recurso (estudiante, curso, etc.) que no existe
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}
