package com.eventhub.paprueba.evento.evento.infrastructure.memory;

import com.eventhub.paprueba.evento.evento.domain.Evento;
import com.eventhub.paprueba.evento.evento.domain.port.EventoRepository;

import java.util.*;

public class EventoRepositoryEnMemoria implements EventoRepository {

    private final Map<Long, Evento> datos = new LinkedHashMap<>();

    @Override
    public Evento guardar(Evento evento) {
        datos.put(evento.getEventoId(), evento);
        return evento;
    }

    @Override
    public Optional<Evento> buscarPorId(Long id) {
        return Optional.ofNullable(datos.get(id));
    }

    @Override
    public List<Evento> listarTodos() {
        return new ArrayList<>(datos.values());
    }

    @Override
    public boolean existePorNombre(String nombre) {
        return datos.values().stream()
                .anyMatch(e -> e.getNombre().equalsIgnoreCase(nombre));
    }

    @Override
    public Evento actualizar(Evento evento) {
        datos.put(evento.getEventoId(), evento);
        return evento;
    }

    @Override
    public boolean eliminar(Long id) {
        return datos.remove(id) != null;
    }
}