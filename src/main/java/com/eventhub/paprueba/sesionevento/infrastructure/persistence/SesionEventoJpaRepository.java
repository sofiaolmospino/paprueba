package com.eventhub.paprueba.sesionevento.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SesionEventoJpaRepository extends JpaRepository<SesionEventoEntity, Long> {
}