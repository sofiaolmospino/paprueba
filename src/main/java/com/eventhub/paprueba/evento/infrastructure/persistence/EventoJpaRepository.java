package com.eventhub.paprueba.evento.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoJpaRepository extends JpaRepository<EventoEntity, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
}