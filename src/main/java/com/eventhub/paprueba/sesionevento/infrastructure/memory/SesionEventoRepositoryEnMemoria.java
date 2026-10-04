package com.eventhub.paprueba.sesionevento.infrastructure.memory;

import com.eventhub.paprueba.sesionevento.domain.SesionEvento;
import com.eventhub.paprueba.sesionevento.domain.port.SesionEventoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Repository
public class SesionEventoRepositoryEnMemoria implements SesionEventoRepository {

    private final List<SesionEvento> sesiones = new ArrayList<>();

    private Long siguienteId = 1L;

    @Override
    public SesionEvento guardar(SesionEvento sesionEvento) {

        sesionEvento.setSesionEventoId(siguienteId++);

        sesiones.add(sesionEvento);

        return sesionEvento;
    }

    @Override
    public List<SesionEvento> listarTodos() {
        return new ArrayList<>(sesiones);
    }

    @Override
    public Optional<SesionEvento> buscarPorId(Long id) {

        return sesiones.stream()
                .filter(sesion -> sesion.getSesionEventoId().equals(id))
                .findFirst();
    }

    @Override
    public SesionEvento actualizar(SesionEvento sesionEvento) {

        for (int i = 0; i < sesiones.size(); i++) {

            if (sesiones.get(i)
                    .getSesionEventoId()
                    .equals(sesionEvento.getSesionEventoId())) {

                sesiones.set(i, sesionEvento);

                return sesionEvento;
            }
        }

        return sesionEvento;
    }

    @Override
    public void eliminar(Long id) {

        sesiones.removeIf(
                sesion -> sesion.getSesionEventoId().equals(id)
        );
    }
}