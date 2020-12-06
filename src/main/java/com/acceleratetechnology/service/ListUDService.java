package com.acceleratetechnology.service;

import com.acceleratetechnology.domain.ListUD;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ListUD}.
 */
public interface ListUDService {

    /**
     * Save a listUD.
     *
     * @param listUD the entity to save.
     * @return the persisted entity.
     */
    ListUD save(ListUD listUD);

    /**
     * Get all the listUDS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ListUD> findAll(Pageable pageable);


    /**
     * Get the "id" listUD.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ListUD> findOne(Long id);

    /**
     * Delete the "id" listUD.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the listUD corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ListUD> search(String query, Pageable pageable);
}
