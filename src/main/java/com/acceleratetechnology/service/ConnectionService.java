package com.acceleratetechnology.service;

import com.acceleratetechnology.domain.Connection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Connection}.
 */
public interface ConnectionService {

    /**
     * Save a connection.
     *
     * @param connection the entity to save.
     * @return the persisted entity.
     */
    Connection save(Connection connection);

    /**
     * Get all the connections.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Connection> findAll(Pageable pageable);


    /**
     * Get the "id" connection.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Connection> findOne(Long id);

    /**
     * Delete the "id" connection.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the connection corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Connection> search(String query, Pageable pageable);
}
