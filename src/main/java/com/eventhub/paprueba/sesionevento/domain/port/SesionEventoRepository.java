package com.eventhub.paprueba.sesionevento.domain.port;

import com.eventhub.paprueba.sesionevento.domain.SesionEvento;

import java.util.List;
import java.util.Optional;

public interface SesionEventoRepository {

    SesionEvento guardar(SesionEvento sesionEvento);

    List<SesionEvento> listarTodos();

    Optional<SesionEvento> buscarPorId(Long id);

    SesionEvento actualizar(SesionEvento sesionEvento);

    void eliminar(Long id);
}