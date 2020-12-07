package com.acceleratetechnology.web.rest;

import com.acceleratetechnology.domain.ConnectionMgr;
import com.acceleratetechnology.service.ConnectionMgrService;
import com.acceleratetechnology.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.acceleratetechnology.domain.ConnectionMgr}.
 */
@RestController
@RequestMapping("/api")
public class ConnectionMgrResource {

    private final Logger log = LoggerFactory.getLogger(ConnectionMgrResource.class);

    private static final String ENTITY_NAME = "connectionMgr";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnectionMgrService connectionMgrService;

    public ConnectionMgrResource(ConnectionMgrService connectionMgrService) {
        this.connectionMgrService = connectionMgrService;
    }

    /**
     * {@code POST  /connection-mgrs} : Create a new connectionMgr.
     *
     * @param connectionMgr the connectionMgr to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connectionMgr, or with status {@code 400 (Bad Request)} if the connectionMgr has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/connection-mgrs")
    public ResponseEntity<ConnectionMgr> createConnectionMgr(@RequestBody ConnectionMgr connectionMgr) throws URISyntaxException {
        log.debug("REST request to save ConnectionMgr : {}", connectionMgr);
        if (connectionMgr.getId() != null) {
            throw new BadRequestAlertException("A new connectionMgr cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConnectionMgr result = connectionMgrService.save(connectionMgr);
        return ResponseEntity.created(new URI("/api/connection-mgrs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /connection-mgrs} : Updates an existing connectionMgr.
     *
     * @param connectionMgr the connectionMgr to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connectionMgr,
     * or with status {@code 400 (Bad Request)} if the connectionMgr is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connectionMgr couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/connection-mgrs")
    public ResponseEntity<ConnectionMgr> updateConnectionMgr(@RequestBody ConnectionMgr connectionMgr) throws URISyntaxException {
        log.debug("REST request to update ConnectionMgr : {}", connectionMgr);
        if (connectionMgr.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConnectionMgr result = connectionMgrService.save(connectionMgr);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, connectionMgr.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /connection-mgrs} : get all the connectionMgrs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connectionMgrs in body.
     */
    @GetMapping("/connection-mgrs")
    public ResponseEntity<List<ConnectionMgr>> getAllConnectionMgrs(Pageable pageable) {
        log.debug("REST request to get a page of ConnectionMgrs");
        Page<ConnectionMgr> page = connectionMgrService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /connection-mgrs/:id} : get the "id" connectionMgr.
     *
     * @param id the id of the connectionMgr to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connectionMgr, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/connection-mgrs/{id}")
    public ResponseEntity<ConnectionMgr> getConnectionMgr(@PathVariable Long id) {
        log.debug("REST request to get ConnectionMgr : {}", id);
        Optional<ConnectionMgr> connectionMgr = connectionMgrService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connectionMgr);
    }

    /**
     * {@code DELETE  /connection-mgrs/:id} : delete the "id" connectionMgr.
     *
     * @param id the id of the connectionMgr to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/connection-mgrs/{id}")
    public ResponseEntity<Void> deleteConnectionMgr(@PathVariable Long id) {
        log.debug("REST request to delete ConnectionMgr : {}", id);
        connectionMgrService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/connection-mgrs?query=:query} : search for the connectionMgr corresponding
     * to the query.
     *
     * @param query the query of the connectionMgr search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/connection-mgrs")
    public ResponseEntity<List<ConnectionMgr>> searchConnectionMgrs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConnectionMgrs for query {}", query);
        Page<ConnectionMgr> page = connectionMgrService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
