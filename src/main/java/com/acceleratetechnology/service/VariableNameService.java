package com.acceleratetechnology.service;

import com.acceleratetechnology.domain.VariableName;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link VariableName}.
 */
public interface VariableNameService {

    /**
     * Save a variableName.
     *
     * @param variableName the entity to save.
     * @return the persisted entity.
     */
    VariableName save(VariableName variableName);

    /**
     * Get all the variableNames.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VariableName> findAll(Pageable pageable);


    /**
     * Get the "id" variableName.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VariableName> findOne(Long id);

    /**
     * Delete the "id" variableName.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the variableName corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VariableName> search(String query, Pageable pageable);
}
