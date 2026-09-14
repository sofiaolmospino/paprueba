package com.eventhub.paprueba.evento.evento.domain.exception;

public class NombreEventoDuplicadoException extends RuntimeException {

    public NombreEventoDuplicadoException(String nombre) {
        super("Ya existe un evento con el nombre: " + nombre);
    }
}