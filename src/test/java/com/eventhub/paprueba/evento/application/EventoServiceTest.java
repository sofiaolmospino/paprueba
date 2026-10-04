package com.eventhub.paprueba.evento.application;

import com.eventhub.paprueba.evento.domain.EstadoEvento;
import com.eventhub.paprueba.evento.domain.Evento;
import com.eventhub.paprueba.evento.domain.exception.NombreEventoDuplicadoException;
import com.eventhub.paprueba.evento.domain.port.EventoRepository;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EventoServiceTest {

    @Test
    void noDebePermitirEventoConNombreDuplicado() {

        EventoRepository repository = mock(EventoRepository.class);

        when(repository.existePorNombre("Evento Test"))
                .thenReturn(true);

        EventoService service = new EventoService(repository);

        assertThrows(
                NombreEventoDuplicadoException.class,
                () -> service.registrar(
                        "Evento Test",
                        "Descripción",
                        "Responsable",
                        "Presencial",
                        OffsetDateTime.now().plusDays(5),
                        OffsetDateTime.now().plusDays(5).plusHours(2),
                        100,
                        EstadoEvento.PUBLICADO,
                        1L
                )
        );

        verify(repository, never()).guardar(any(Evento.class));
    }
}