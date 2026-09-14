package com.eventhub.paprueba.evento.evento.infrastructure.adapter.in.web.dto;

import com.eventhub.paprueba.evento.evento.domain.EstadoEvento;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record ActualizarEventoRequest(

        @NotBlank(message = "El nombre del evento es obligatorio")
        @Size(max = 180, message = "El nombre no puede superar los 180 caracteres")
        String nombre,

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
        String descripcion,

        @NotBlank(message = "El responsable es obligatorio")
        String responsable,

        @NotBlank(message = "La modalidad es obligatoria")
        String modalidad,

        @NotNull(message = "La fecha de inicio es obligatoria")
        @Future(message = "La fecha de inicio debe ser futura")
        OffsetDateTime fechaInicio,

        @NotNull(message = "La fecha de fin es obligatoria")
        @Future(message = "La fecha de fin debe ser futura")
        OffsetDateTime fechaFin,

        @NotNull(message = "La capacidad es obligatoria")
        @Min(value = 1, message = "La capacidad debe ser mayor a 0")
        Integer capacidad,

        @NotNull(message = "El estado es obligatorio")
        EstadoEvento estado,

        @NotNull(message = "El usuarioId es obligatorio")
        Long usuarioId
) {
}