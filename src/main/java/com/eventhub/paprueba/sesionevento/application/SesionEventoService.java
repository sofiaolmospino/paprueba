package com.eventhub.paprueba.sesionevento.application;

import com.eventhub.paprueba.evento.domain.port.EventoRepository;
import com.eventhub.paprueba.sesionevento.domain.SesionEvento;
import com.eventhub.paprueba.sesionevento.domain.exception.EventoIdInvalidoException;
import com.eventhub.paprueba.sesionevento.domain.exception.FechaSesionEventoInvalidaException;
import com.eventhub.paprueba.sesionevento.domain.exception.SesionEventoNoEncontradaException;
import com.eventhub.paprueba.sesionevento.domain.port.SesionEventoRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SesionEventoService {

    private final SesionEventoRepository sesionEventoRepository;
    private final EventoRepository eventoRepository;

    public SesionEventoService(
            SesionEventoRepository sesionEventoRepository,
            EventoRepository eventoRepository
    ) {
        this.sesionEventoRepository = sesionEventoRepository;
        this.eventoRepository = eventoRepository;
    }

    // POST - CREAR
    public SesionEvento registrar(
            Long eventoId,
            String sede,
            String sala,
            OffsetDateTime fechaInicio,
            OffsetDateTime fechaFin
    ) {

        validarEvento(eventoId);
        validarFechas(fechaInicio, fechaFin);

        OffsetDateTime ahora = OffsetDateTime.now();

        SesionEvento sesionEvento = new SesionEvento(
                null,
                eventoId,
                sede,
                sala,
                fechaInicio,
                fechaFin,
                ahora,
                ahora
        );

        return sesionEventoRepository.guardar(sesionEvento);
    }

    // GET - LISTAR
    public List<SesionEvento> listar() {
        return sesionEventoRepository.listarTodos();
    }

    // GET - BUSCAR POR ID
    public Optional<SesionEvento> buscarPorId(Long id) {
        return sesionEventoRepository.buscarPorId(id);
    }

    // GET - BUSCAR POR ID OBLIGATORIO
    public SesionEvento buscarPorIdObligatorio(Long id) {
        return sesionEventoRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new SesionEventoNoEncontradaException(id)
                );
    }

    // PUT - ACTUALIZAR
    public SesionEvento actualizar(
            Long id,
            Long eventoId,
            String sede,
            String sala,
            OffsetDateTime fechaInicio,
            OffsetDateTime fechaFin
    ) {

        SesionEvento sesionEventoExistente =
                sesionEventoRepository.buscarPorId(id)
                        .orElseThrow(() ->
                                new SesionEventoNoEncontradaException(id)
                        );

        validarEvento(eventoId);
        validarFechas(fechaInicio, fechaFin);

        sesionEventoExistente.setEventoId(eventoId);
        sesionEventoExistente.setSede(sede);
        sesionEventoExistente.setSala(sala);
        sesionEventoExistente.setFechaInicio(fechaInicio);
        sesionEventoExistente.setFechaFin(fechaFin);
        sesionEventoExistente.setUpdatedAt(OffsetDateTime.now());

        return sesionEventoRepository.actualizar(sesionEventoExistente);
    }

    // DELETE - ELIMINAR
    public void eliminar(Long id) {

        sesionEventoRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new SesionEventoNoEncontradaException(id)
                );

        sesionEventoRepository.eliminar(id);
    }

    // VALIDAR EVENTO
    private void validarEvento(Long eventoId) {

        if (eventoId == null) {
            throw new EventoIdInvalidoException(
                    "El ID del evento es obligatorio."
            );
        }

        if (eventoId <= 0) {
            throw new EventoIdInvalidoException(
                    "El ID del evento debe ser mayor que 0."
            );
        }

        boolean existe = eventoRepository.buscarPorId(eventoId).isPresent();

        if (!existe) {
            throw new EventoIdInvalidoException(
                    "No existe un evento con ID: " + eventoId
            );
        }
    }

    // VALIDAR FECHAS
    private void validarFechas(
            OffsetDateTime fechaInicio,
            OffsetDateTime fechaFin
    ) {

        if (fechaInicio == null ||
                fechaFin == null ||
                !fechaInicio.isBefore(fechaFin)) {

            throw new FechaSesionEventoInvalidaException();
        }
    }
}