package com.eventhub.paprueba.sesionevento.domain.exception;

public class FechaSesionEventoInvalidaException extends RuntimeException {

    public FechaSesionEventoInvalidaException() {
        super("La fecha de inicio debe ser anterior a la fecha de fin.");
    }
}