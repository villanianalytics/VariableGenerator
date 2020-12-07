package com.acceleratetechnology.service;

import com.acceleratetechnology.domain.ConnectionMgr;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ConnectionMgr}.
 */
public interface ConnectionMgrService {

    /**
     * Save a connectionMgr.
     *
     * @param connectionMgr the entity to save.
     * @return the persisted entity.
     */
    ConnectionMgr save(ConnectionMgr connectionMgr);

    /**
     * Get all the connectionMgrs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConnectionMgr> findAll(Pageable pageable);


    /**
     * Get the "id" connectionMgr.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConnectionMgr> findOne(Long id);

    /**
     * Delete the "id" connectionMgr.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the connectionMgr corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConnectionMgr> search(String query, Pageable pageable);
}
