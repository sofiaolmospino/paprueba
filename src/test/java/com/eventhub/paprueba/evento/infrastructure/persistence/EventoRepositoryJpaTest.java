package com.eventhub.paprueba.evento.infrastructure.persistence;

import com.eventhub.paprueba.evento.domain.EstadoEvento;
import com.eventhub.paprueba.evento.domain.Evento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventoRepositoryJpaTest {

    @Autowired
    private EventoRepositoryJpa eventoRepository;

    @Test
    void debeGuardarYBuscarEvento() {

        OffsetDateTime inicio =
                OffsetDateTime.now().plusDays(10);

        OffsetDateTime fin =
                inicio.plusHours(2);

        String nombreUnico =
                "Evento Test Integracion " + UUID.randomUUID();

        Evento evento = new Evento(
                null,
                nombreUnico,
                "Descripcion de prueba",
                "Responsable Test",
                "Presencial",
                inicio,
                fin,
                50,
                EstadoEvento.PUBLICADO,
                1L,
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );

        Evento guardado =
                eventoRepository.guardar(evento);

        assertNotNull(guardado.getEventoId());

        Evento encontrado =
                eventoRepository.buscarPorId(
                        guardado.getEventoId()
                ).orElse(null);

        assertNotNull(encontrado);
        assertEquals(
                nombreUnico,
                encontrado.getNombre()
        );
    }
}