package com.acceleratetechnology.service;

import com.acceleratetechnology.domain.App;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link App}.
 */
public interface AppService {

    /**
     * Save a app.
     *
     * @param app the entity to save.
     * @return the persisted entity.
     */
    App save(App app);

    /**
     * Get all the apps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<App> findAll(Pageable pageable);


    /**
     * Get the "id" app.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<App> findOne(Long id);

    /**
     * Delete the "id" app.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the app corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<App> search(String query, Pageable pageable);
}
