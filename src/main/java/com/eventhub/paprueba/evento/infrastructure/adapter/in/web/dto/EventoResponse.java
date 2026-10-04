package com.eventhub.paprueba.evento.infrastructure.adapter.in.web.dto;

import com.eventhub.paprueba.evento.domain.EstadoEvento;

import java.time.OffsetDateTime;

public record EventoResponse(
        Long eventoId,
        String nombre,
        String descripcion,
        String responsable,
        String modalidad,
        OffsetDateTime fechaInicio,
        OffsetDateTime fechaFin,
        Integer capacidad,
        EstadoEvento estado,
        Long usuarioId,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}