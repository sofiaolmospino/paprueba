package com.eventhub.paprueba.sesionevento.application;

import com.eventhub.paprueba.evento.domain.port.EventoRepository;
import com.eventhub.paprueba.sesionevento.domain.exception.FechaSesionEventoInvalidaException;
import com.eventhub.paprueba.sesionevento.domain.port.SesionEventoRepository;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SesionEventoServiceTest {

    @Test
    void noDebePermitirFechaInicioPosteriorOIgualALaFechaFin() {

        SesionEventoRepository sesionRepository =
                mock(SesionEventoRepository.class);

        EventoRepository eventoRepository =
                mock(EventoRepository.class);

        when(eventoRepository.buscarPorId(1L))
                .thenReturn(java.util.Optional.of(mock(
                        com.eventhub.paprueba.evento.domain.Evento.class
                )));

        SesionEventoService service =
                new SesionEventoService(
                        sesionRepository,
                        eventoRepository
                );

        OffsetDateTime fechaInicio =
                OffsetDateTime.now().plusDays(5);

        OffsetDateTime fechaFin =
                OffsetDateTime.now().plusDays(4);

        assertThrows(
                FechaSesionEventoInvalidaException.class,
                () -> service.registrar(
                        1L,
                        "Sede Test",
                        "Sala Test",
                        fechaInicio,
                        fechaFin
                )
        );

        verify(sesionRepository, never()).guardar(any());
    }
}