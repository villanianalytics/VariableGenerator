package com.acceleratetechnology.web.rest;

import com.acceleratetechnology.domain.Connection;
import com.acceleratetechnology.service.ConnectionService;
import com.acceleratetechnology.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.acceleratetechnology.domain.Connection}.
 */
@RestController
@RequestMapping("/api")
public class ConnectionResource {

    private final Logger log = LoggerFactory.getLogger(ConnectionResource.class);

    private static final String ENTITY_NAME = "connection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConnectionService connectionService;

    public ConnectionResource(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    /**
     * {@code POST  /connections} : Create a new connection.
     *
     * @param connection the connection to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new connection, or with status {@code 400 (Bad Request)} if the connection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/connections")
    public ResponseEntity<Connection> createConnection(@RequestBody Connection connection) throws URISyntaxException {
        log.debug("REST request to save Connection : {}", connection);
        if (connection.getId() != null) {
            throw new BadRequestAlertException("A new connection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Connection result = connectionService.save(connection);
        return ResponseEntity.created(new URI("/api/connections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /connections} : Updates an existing connection.
     *
     * @param connection the connection to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated connection,
     * or with status {@code 400 (Bad Request)} if the connection is not valid,
     * or with status {@code 500 (Internal Server Error)} if the connection couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/connections")
    public ResponseEntity<Connection> updateConnection(@RequestBody Connection connection) throws URISyntaxException {
        log.debug("REST request to update Connection : {}", connection);
        if (connection.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Connection result = connectionService.save(connection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, connection.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /connections} : get all the connections.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of connections in body.
     */
    @GetMapping("/connections")
    public List<Connection> getAllConnections() {
        log.debug("REST request to get all Connections");
        return connectionService.findAll();
    }

    /**
     * {@code GET  /connections/:id} : get the "id" connection.
     *
     * @param id the id of the connection to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the connection, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/connections/{id}")
    public ResponseEntity<Connection> getConnection(@PathVariable Long id) {
        log.debug("REST request to get Connection : {}", id);
        Optional<Connection> connection = connectionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(connection);
    }

    /**
     * {@code DELETE  /connections/:id} : delete the "id" connection.
     *
     * @param id the id of the connection to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/connections/{id}")
    public ResponseEntity<Void> deleteConnection(@PathVariable Long id) {
        log.debug("REST request to delete Connection : {}", id);
        connectionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/connections?query=:query} : search for the connection corresponding
     * to the query.
     *
     * @param query the query of the connection search.
     * @return the result of the search.
     */
    @GetMapping("/_search/connections")
    public List<Connection> searchConnections(@RequestParam String query) {
        log.debug("REST request to search Connections for query {}", query);
        return connectionService.search(query);
    }
}
