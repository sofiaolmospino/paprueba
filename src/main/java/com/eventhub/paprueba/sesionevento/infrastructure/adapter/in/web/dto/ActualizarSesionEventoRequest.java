package com.eventhub.paprueba.sesionevento.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

public record ActualizarSesionEventoRequest(

        @NotNull(message = "El eventoId es obligatorio")
        @Positive(message = "El eventoId debe ser mayor a 0")
        Long eventoId,

        @NotBlank(message = "La sede es obligatoria")
        String sede,

        @NotBlank(message = "La sala es obligatoria")
        String sala,

        @NotNull(message = "La fecha de inicio es obligatoria")
        @Future(message = "La fecha de inicio debe ser futura")
        OffsetDateTime fechaInicio,

        @NotNull(message = "La fecha de fin es obligatoria")
        @Future(message = "La fecha de fin debe ser futura")
        OffsetDateTime fechaFin

) {
}