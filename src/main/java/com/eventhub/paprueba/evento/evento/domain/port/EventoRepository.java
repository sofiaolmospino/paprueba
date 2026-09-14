package com.eventhub.paprueba.evento.evento.domain.port;

import com.eventhub.paprueba.evento.evento.domain.Evento;
import java.util.List;
import java.util.Optional;

public interface EventoRepository {
    Evento guardar(Evento evento);
    Optional<Evento> buscarPorId(Long id);
    List<Evento> listarTodos();
    boolean existePorNombre(String nombre);
    Evento actualizar(Evento evento);
    boolean eliminar(Long id);
}
