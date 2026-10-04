package com.eventhub.paprueba.evento.infrastructure.persistence;

import com.eventhub.paprueba.evento.domain.Evento;
import com.eventhub.paprueba.evento.domain.port.EventoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EventoRepositoryJpa implements EventoRepository {

    private final EventoJpaRepository eventoJpaRepository;

    public EventoRepositoryJpa(EventoJpaRepository eventoJpaRepository) {
        this.eventoJpaRepository = eventoJpaRepository;
    }

    @Override
    public Evento guardar(Evento evento) {

        EventoEntity entity = convertirAEntity(evento);

        EventoEntity guardado = eventoJpaRepository.save(entity);

        evento.setEventoId(guardado.getEventoId());

        return evento;
    }

    @Override
    public Optional<Evento> buscarPorId(Long id) {

        return eventoJpaRepository.findById(id)
                .map(this::convertirADominio);
    }

    @Override
    public List<Evento> listarTodos() {

        return eventoJpaRepository.findAll()
                .stream()
                .map(this::convertirADominio)
                .toList();
    }

    @Override
    public boolean existePorNombre(String nombre) {

        return eventoJpaRepository.existsByNombreIgnoreCase(nombre);
    }

    @Override
    public Evento actualizar(Evento evento) {

        EventoEntity entity = convertirAEntity(evento);

        EventoEntity actualizado = eventoJpaRepository.save(entity);

        return convertirADominio(actualizado);
    }

    @Override
    public boolean eliminar(Long id) {

        if (!eventoJpaRepository.existsById(id)) {
            return false;
        }

        eventoJpaRepository.deleteById(id);

        return true;
    }

    private EventoEntity convertirAEntity(Evento evento) {

        EventoEntity entity = new EventoEntity();

        entity.setEventoId(evento.getEventoId());
        entity.setNombre(evento.getNombre());
        entity.setDescripcion(evento.getDescripcion());
        entity.setResponsable(evento.getResponsable());
        entity.setModalidad(evento.getModalidad());
        entity.setFechaInicio(evento.getFechaInicio());
        entity.setFechaFin(evento.getFechaFin());
        entity.setCapacidad(evento.getCapacidad());
        entity.setEstado(evento.getEstado());
        entity.setUsuarioId(evento.getUsuarioId());
        entity.setCreatedAt(evento.getCreatedAt());
        entity.setUpdatedAt(evento.getUpdatedAt());

        return entity;
    }

    private Evento convertirADominio(EventoEntity entity) {

        return new Evento(
                entity.getEventoId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getResponsable(),
                entity.getModalidad(),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getCapacidad(),
                entity.getEstado(),
                entity.getUsuarioId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}