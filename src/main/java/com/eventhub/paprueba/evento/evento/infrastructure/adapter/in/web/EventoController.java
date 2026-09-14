package com.eventhub.paprueba.evento.evento.infrastructure.adapter.in.web;

import com.eventhub.paprueba.evento.evento.application.EventoService;
import com.eventhub.paprueba.evento.evento.domain.Evento;
import com.eventhub.paprueba.evento.evento.infrastructure.adapter.in.web.dto.ActualizarEventoRequest;
import com.eventhub.paprueba.evento.evento.infrastructure.adapter.in.web.dto.CrearEventoRequest;
import com.eventhub.paprueba.evento.evento.infrastructure.adapter.in.web.dto.EventoResponse;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/evento")
public class EventoController {

    private final EventoService eventoService;

    public EventoController(EventoService eventoService) {
        this.eventoService = eventoService;
    }


    // POST /api/evento
    @PostMapping
    public ResponseEntity<EventoResponse> crear(
            @Valid @RequestBody CrearEventoRequest request) {

        Evento evento = eventoService.registrar(
                request.nombre(),
                request.descripcion(),
                request.responsable(),
                request.modalidad(),
                request.fechaInicio(),
                request.fechaFin(),
                request.capacidad(),
                request.estado(),
                request.usuarioId()
        );

        EventoResponse response = toResponse(evento);

        return ResponseEntity
                .created(URI.create("/api/evento/" + evento.getEventoId()))
                .body(response);
    }

    // GET /api/evento
    @GetMapping
    public ResponseEntity<List<EventoResponse>> listar(
            @RequestParam(required = false) String nombre) {

        List<EventoResponse> eventos = eventoService.listar()
                .stream()
                .filter(evento -> nombre == null
                        || evento.getNombre() == null
                        || evento.getNombre()
                        .toLowerCase()
                        .contains(nombre.toLowerCase()))
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(eventos);
    }


    // GET /api/evento/{id}
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> buscarPorId(
            @PathVariable Long id) {

        return eventoService.buscarPorId(id)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT /api/evento/{id}
    @PutMapping("/{id}")
    public ResponseEntity<EventoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarEventoRequest request) {

        Evento evento = eventoService.actualizar(
                id,
                request.nombre(),
                request.descripcion(),
                request.responsable(),
                request.modalidad(),
                request.fechaInicio(),
                request.fechaFin(),
                request.capacidad(),
                request.estado(),
                request.usuarioId()
        );

        return ResponseEntity.ok(toResponse(evento));
    }

    // DELETE /api/evento/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        eventoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    // MAPPER
    private EventoResponse toResponse(Evento evento) {

        return new EventoResponse(
                evento.getEventoId(),
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getResponsable(),
                evento.getModalidad(),
                evento.getFechaInicio(),
                evento.getFechaFin(),
                evento.getCapacidad(),
                evento.getEstado(),
                evento.getUsuarioId(),
                evento.getCreatedAt(),
                evento.getUpdatedAt()
        );
    }
}