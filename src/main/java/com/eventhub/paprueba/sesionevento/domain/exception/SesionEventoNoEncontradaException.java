package com.eventhub.paprueba.sesionevento.domain.exception;

public class SesionEventoNoEncontradaException extends RuntimeException {

    public SesionEventoNoEncontradaException(Long id) {
        super("Sesión de evento no encontrada con ID: " + id);
    }
}