package com.acceleratetechnology.service;

import com.acceleratetechnology.domain.Cubes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Cubes}.
 */
public interface CubesService {

    /**
     * Save a cubes.
     *
     * @param cubes the entity to save.
     * @return the persisted entity.
     */
    Cubes save(Cubes cubes);

    /**
     * Get all the cubes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cubes> findAll(Pageable pageable);


    /**
     * Get the "id" cubes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cubes> findOne(Long id);

    /**
     * Delete the "id" cubes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the cubes corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cubes> search(String query, Pageable pageable);
}
