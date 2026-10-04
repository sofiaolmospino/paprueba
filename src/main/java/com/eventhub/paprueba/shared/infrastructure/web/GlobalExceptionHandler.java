package com.eventhub.paprueba.shared.infrastructure.web;

import com.eventhub.paprueba.evento.domain.exception.EventoNoEncontradoException;
import com.eventhub.paprueba.evento.domain.exception.NombreEventoDuplicadoException;
import com.eventhub.paprueba.sesionevento.domain.exception.EventoIdInvalidoException;
import com.eventhub.paprueba.sesionevento.domain.exception.FechaSesionEventoInvalidaException;
import com.eventhub.paprueba.sesionevento.domain.exception.SesionEventoNoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventoNoEncontradoException.class)
    public ResponseEntity<String> manejarEventoNoEncontrado(
            EventoNoEncontradoException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(SesionEventoNoEncontradaException.class)
    public ResponseEntity<String> manejarSesionEventoNoEncontrada(
            SesionEventoNoEncontradaException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(NombreEventoDuplicadoException.class)
    public ResponseEntity<String> manejarNombreEventoDuplicado(
            NombreEventoDuplicadoException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(EventoIdInvalidoException.class)
    public ResponseEntity<String> manejarEventoIdInvalido(
            EventoIdInvalidoException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(FechaSesionEventoInvalidaException.class)
    public ResponseEntity<String> manejarFechaSesionEventoInvalida(
            FechaSesionEventoInvalidaException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> manejarErroresDeValidacion(
            MethodArgumentNotValidException exception) {

        String errores = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errores);
    }
}