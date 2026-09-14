package com.eventhub.paprueba.evento.evento.application;

import com.eventhub.paprueba.evento.evento.domain.EstadoEvento;
import com.eventhub.paprueba.evento.evento.domain.Evento;
import com.eventhub.paprueba.evento.evento.domain.exception.EventoNoEncontradoException;
import com.eventhub.paprueba.evento.evento.domain.exception.NombreEventoDuplicadoException;
import com.eventhub.paprueba.evento.evento.domain.port.EventoRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }


    // POST - CREAR
    public Evento registrar(
            String nombre,
            String descripcion,
            String responsable,
            String modalidad,
            OffsetDateTime fechaInicio,
            OffsetDateTime fechaFin,
            Integer capacidad,
            EstadoEvento estado,
            Long usuarioId
    ) {

        if (eventoRepository.existePorNombre(nombre)) {
            throw new NombreEventoDuplicadoException(nombre);
        }

        OffsetDateTime ahora = OffsetDateTime.now();

        Evento evento = new Evento(
                null,
                nombre,
                descripcion,
                responsable,
                modalidad,
                fechaInicio,
                fechaFin,
                capacidad,
                estado,
                usuarioId,
                ahora,
                ahora
        );

        return eventoRepository.guardar(evento);
    }


    // GET - LISTAR
    public List<Evento> listar() {
        return eventoRepository.listarTodos();
    }


    // GET - BUSCAR POR ID
    public Optional<Evento> buscarPorId(Long id) {
        return eventoRepository.buscarPorId(id);
    }

    public Evento buscarPorIdObligatorio(Long id) {
        return eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new EventoNoEncontradoException(id));
    }


    // PUT - ACTUALIZAR
    public Evento actualizar(
            Long id,
            String nombre,
            String descripcion,
            String responsable,
            String modalidad,
            OffsetDateTime fechaInicio,
            OffsetDateTime fechaFin,
            Integer capacidad,
            EstadoEvento estado,
            Long usuarioId
    ) {

        Evento eventoExistente = eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new EventoNoEncontradoException(id));

        boolean cambioNombre =
                !eventoExistente.getNombre().equalsIgnoreCase(nombre);

        if (cambioNombre && eventoRepository.existePorNombre(nombre)) {
            throw new NombreEventoDuplicadoException(nombre);
        }

        eventoExistente.setNombre(nombre);
        eventoExistente.setDescripcion(descripcion);
        eventoExistente.setResponsable(responsable);
        eventoExistente.setModalidad(modalidad);
        eventoExistente.setFechaInicio(fechaInicio);
        eventoExistente.setFechaFin(fechaFin);
        eventoExistente.setCapacidad(capacidad);
        eventoExistente.setEstado(estado);
        eventoExistente.setUsuarioId(usuarioId);
        eventoExistente.setUpdatedAt(OffsetDateTime.now());

        return eventoRepository.actualizar(eventoExistente);
    }

    // DELETE - ELIMINAR
    public void eliminar(Long id) {

        eventoRepository.buscarPorId(id)
                .orElseThrow(() -> new EventoNoEncontradoException(id));

        eventoRepository.eliminar(id);
    }
}