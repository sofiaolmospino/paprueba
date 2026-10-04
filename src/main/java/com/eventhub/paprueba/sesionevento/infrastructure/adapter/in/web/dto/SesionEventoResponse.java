package com.eventhub.paprueba.sesionevento.infrastructure.adapter.in.web.dto;

import com.eventhub.paprueba.sesionevento.domain.SesionEvento;

import java.time.OffsetDateTime;

public record SesionEventoResponse(
        Long sesionEventoId,
        Long eventoId,
        String sede,
        String sala,
        OffsetDateTime fechaInicio,
        OffsetDateTime fechaFin,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {

    public static SesionEventoResponse fromDomain(SesionEvento sesionEvento) {
        return new SesionEventoResponse(
                sesionEvento.getSesionEventoId(),
                sesionEvento.getEventoId(),
                sesionEvento.getSede(),
                sesionEvento.getSala(),
                sesionEvento.getFechaInicio(),
                sesionEvento.getFechaFin(),
                sesionEvento.getCreatedAt(),
                sesionEvento.getUpdatedAt()
        );
    }
}