package com.eventhub.paprueba.sesionevento.infrastructure.adapter.in.web;

import com.eventhub.paprueba.sesionevento.application.SesionEventoService;
import com.eventhub.paprueba.sesionevento.domain.SesionEvento;
import com.eventhub.paprueba.sesionevento.infrastructure.adapter.in.web.dto.ActualizarSesionEventoRequest;
import com.eventhub.paprueba.sesionevento.infrastructure.adapter.in.web.dto.CrearSesionEventoRequest;
import com.eventhub.paprueba.sesionevento.infrastructure.adapter.in.web.dto.SesionEventoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sesion-evento")
public class SesionEventoController {

    private final SesionEventoService sesionEventoService;

    public SesionEventoController(SesionEventoService sesionEventoService) {
        this.sesionEventoService = sesionEventoService;
    }

    // POST - CREAR
    @PostMapping
    public ResponseEntity<SesionEventoResponse> crear(
            @Valid @RequestBody CrearSesionEventoRequest request
    ) {

        SesionEvento sesionEvento = sesionEventoService.registrar(
                request.eventoId(),
                request.sede(),
                request.sala(),
                request.fechaInicio(),
                request.fechaFin()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SesionEventoResponse.fromDomain(sesionEvento));
    }

    // GET - LISTAR
    @GetMapping
    public ResponseEntity<List<SesionEventoResponse>> listar() {

        List<SesionEventoResponse> respuesta =
                sesionEventoService.listar()
                        .stream()
                        .map(SesionEventoResponse::fromDomain)
                        .toList();

        return ResponseEntity.ok(respuesta);
    }

    // GET - BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<SesionEventoResponse> buscarPorId(
            @PathVariable Long id
    ) {

        SesionEvento sesionEvento =
                sesionEventoService.buscarPorIdObligatorio(id);

        return ResponseEntity.ok(
                SesionEventoResponse.fromDomain(sesionEvento)
        );
    }

    // PUT - ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<SesionEventoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarSesionEventoRequest request
    ) {

        SesionEvento sesionEvento =
                sesionEventoService.actualizar(
                        id,
                        request.eventoId(),
                        request.sede(),
                        request.sala(),
                        request.fechaInicio(),
                        request.fechaFin()
                );

        return ResponseEntity.ok(
                SesionEventoResponse.fromDomain(sesionEvento)
        );
    }

    // DELETE - ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id
    ) {

        sesionEventoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}