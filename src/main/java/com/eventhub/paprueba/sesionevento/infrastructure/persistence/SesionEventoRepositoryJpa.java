package com.eventhub.paprueba.sesionevento.infrastructure.persistence;

import com.eventhub.paprueba.evento.infrastructure.persistence.EventoEntity;
import com.eventhub.paprueba.evento.infrastructure.persistence.EventoJpaRepository;
import com.eventhub.paprueba.sesionevento.domain.SesionEvento;
import com.eventhub.paprueba.sesionevento.domain.port.SesionEventoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SesionEventoRepositoryJpa implements SesionEventoRepository {

    private final SesionEventoJpaRepository sesionEventoJpaRepository;
    private final EventoJpaRepository eventoJpaRepository;

    public SesionEventoRepositoryJpa(
            SesionEventoJpaRepository sesionEventoJpaRepository,
            EventoJpaRepository eventoJpaRepository
    ) {
        this.sesionEventoJpaRepository = sesionEventoJpaRepository;
        this.eventoJpaRepository = eventoJpaRepository;
    }

    @Override
    public SesionEvento guardar(SesionEvento sesionEvento) {

        SesionEventoEntity entity = convertirAEntity(sesionEvento);

        SesionEventoEntity guardado = sesionEventoJpaRepository.save(entity);

        sesionEvento.setSesionEventoId(guardado.getSesionEventoId());

        return sesionEvento;
    }

    @Override
    public Optional<SesionEvento> buscarPorId(Long id) {

        return sesionEventoJpaRepository.findById(id)
                .map(this::convertirADominio);
    }

    @Override
    public List<SesionEvento> listarTodos() {

        return sesionEventoJpaRepository.findAll()
                .stream()
                .map(this::convertirADominio)
                .toList();
    }

    @Override
    public SesionEvento actualizar(SesionEvento sesionEvento) {

        SesionEventoEntity entity = convertirAEntity(sesionEvento);

        SesionEventoEntity actualizado =
                sesionEventoJpaRepository.save(entity);

        return convertirADominio(actualizado);
    }

    @Override
    public void eliminar(Long id) {

        sesionEventoJpaRepository.deleteById(id);
    }

    private SesionEventoEntity convertirAEntity(SesionEvento sesionEvento) {

        SesionEventoEntity entity = new SesionEventoEntity();

        entity.setSesionEventoId(sesionEvento.getSesionEventoId());

        EventoEntity evento = eventoJpaRepository.getReferenceById(
                sesionEvento.getEventoId()
        );

        entity.setEvento(evento);
        entity.setSede(sesionEvento.getSede());
        entity.setSala(sesionEvento.getSala());
        entity.setFechaInicio(sesionEvento.getFechaInicio());
        entity.setFechaFin(sesionEvento.getFechaFin());
        entity.setCreatedAt(sesionEvento.getCreatedAt());
        entity.setUpdatedAt(sesionEvento.getUpdatedAt());

        return entity;
    }

    private SesionEvento convertirADominio(SesionEventoEntity entity) {

        return new SesionEvento(
                entity.getSesionEventoId(),
                entity.getEvento().getEventoId(),
                entity.getSede(),
                entity.getSala(),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}