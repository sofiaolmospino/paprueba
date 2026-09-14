package com.eventhub.paprueba.evento.evento.infrastructure.persistence;

import com.eventhub.paprueba.evento.evento.domain.EstadoEvento;
import com.eventhub.paprueba.evento.evento.domain.Evento;
import com.eventhub.paprueba.evento.evento.domain.port.EventoRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class EventoRepositoryPostgres implements EventoRepository {

    private final JdbcTemplate jdbcTemplate;

    public EventoRepositoryPostgres(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // POST - CREAR
    @Override
    public Evento guardar(Evento evento) {

        String sql = """
                INSERT INTO eventhub2026.evento (
                    nombre,
                    descripcion,
                    responsable,
                    modalidad,
                    fecha_inicio,
                    fecha_fin,
                    capacidad,
                    estado,
                    usuario_id,
                    created_at,
                    updated_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING evento_id
                """;

        Long id = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getResponsable(),
                evento.getModalidad(),
                evento.getFechaInicio(),
                evento.getFechaFin(),
                evento.getCapacidad(),
                evento.getEstado().name(),
                evento.getUsuarioId(),
                evento.getCreatedAt(),
                evento.getUpdatedAt()
        );

        evento.setEventoId(id);

        return evento;
    }

    // GET - BUSCAR POR ID
    @Override
    public Optional<Evento> buscarPorId(Long id) {

        String sql = """
                SELECT
                    evento_id,
                    nombre,
                    descripcion,
                    responsable,
                    modalidad,
                    fecha_inicio,
                    fecha_fin,
                    capacidad,
                    estado,
                    usuario_id,
                    created_at,
                    updated_at
                FROM eventhub2026.evento
                WHERE evento_id = ?
                """;

        List<Evento> resultados = jdbcTemplate.query(
                sql,
                this::mapearEvento,
                id
        );

        return resultados.stream().findFirst();
    }

    // GET - LISTAR
    @Override
    public List<Evento> listarTodos() {

        String sql = """
                SELECT
                    evento_id,
                    nombre,
                    descripcion,
                    responsable,
                    modalidad,
                    fecha_inicio,
                    fecha_fin,
                    capacidad,
                    estado,
                    usuario_id,
                    created_at,
                    updated_at
                FROM eventhub2026.evento
                ORDER BY evento_id
                """;

        return jdbcTemplate.query(
                sql,
                this::mapearEvento
        );
    }

    // VALIDAR NOMBRE
    @Override
    public boolean existePorNombre(String nombre) {

        String sql = """
                SELECT EXISTS (
                    SELECT 1
                    FROM eventhub2026.evento
                    WHERE LOWER(nombre) = LOWER(?)
                )
                """;

        Boolean existe = jdbcTemplate.queryForObject(
                sql,
                Boolean.class,
                nombre
        );

        return Boolean.TRUE.equals(existe);
    }

    // PUT - ACTUALIZAR
    @Override
    public Evento actualizar(Evento evento) {

        String sql = """
                UPDATE eventhub2026.evento
                SET
                    nombre = ?,
                    descripcion = ?,
                    responsable = ?,
                    modalidad = ?,
                    fecha_inicio = ?,
                    fecha_fin = ?,
                    capacidad = ?,
                    estado = ?,
                    usuario_id = ?,
                    updated_at = ?
                WHERE evento_id = ?
                """;

        jdbcTemplate.update(
                sql,
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getResponsable(),
                evento.getModalidad(),
                evento.getFechaInicio(),
                evento.getFechaFin(),
                evento.getCapacidad(),
                evento.getEstado().name(),
                evento.getUsuarioId(),
                evento.getUpdatedAt(),
                evento.getEventoId()
        );

        return evento;
    }

    // DELETE - ELIMINAR
    @Override
    public boolean eliminar(Long id) {

        String sql = """
                DELETE FROM eventhub2026.evento
                WHERE evento_id = ?
                """;

        int filasEliminadas = jdbcTemplate.update(sql, id);

        return filasEliminadas > 0;
    }

    // MAPEAR RESULTADO
    private Evento mapearEvento(ResultSet rs, int rowNum)
            throws SQLException {

        return new Evento(
                rs.getLong("evento_id"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("responsable"),
                rs.getString("modalidad"),
                rs.getObject("fecha_inicio", OffsetDateTime.class),
                rs.getObject("fecha_fin", OffsetDateTime.class),
                rs.getInt("capacidad"),
                EstadoEvento.valueOf(rs.getString("estado")),
                rs.getLong("usuario_id"),
                rs.getObject("created_at", OffsetDateTime.class),
                rs.getObject("updated_at", OffsetDateTime.class)
        );
    }
}