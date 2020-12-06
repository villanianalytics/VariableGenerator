package com.acceleratetechnology.web.rest;

import com.acceleratetechnology.domain.Cubes;
import com.acceleratetechnology.service.CubesService;
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
 * REST controller for managing {@link com.acceleratetechnology.domain.Cubes}.
 */
@RestController
@RequestMapping("/api")
public class CubesResource {

    private final Logger log = LoggerFactory.getLogger(CubesResource.class);

    private static final String ENTITY_NAME = "cubes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CubesService cubesService;

    public CubesResource(CubesService cubesService) {
        this.cubesService = cubesService;
    }

    /**
     * {@code POST  /cubes} : Create a new cubes.
     *
     * @param cubes the cubes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cubes, or with status {@code 400 (Bad Request)} if the cubes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cubes")
    public ResponseEntity<Cubes> createCubes(@RequestBody Cubes cubes) throws URISyntaxException {
        log.debug("REST request to save Cubes : {}", cubes);
        if (cubes.getId() != null) {
            throw new BadRequestAlertException("A new cubes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cubes result = cubesService.save(cubes);
        return ResponseEntity.created(new URI("/api/cubes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cubes} : Updates an existing cubes.
     *
     * @param cubes the cubes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cubes,
     * or with status {@code 400 (Bad Request)} if the cubes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cubes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cubes")
    public ResponseEntity<Cubes> updateCubes(@RequestBody Cubes cubes) throws URISyntaxException {
        log.debug("REST request to update Cubes : {}", cubes);
        if (cubes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cubes result = cubesService.save(cubes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cubes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cubes} : get all the cubes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cubes in body.
     */
    @GetMapping("/cubes")
    public ResponseEntity<List<Cubes>> getAllCubes(Pageable pageable) {
        log.debug("REST request to get a page of Cubes");
        Page<Cubes> page = cubesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cubes/:id} : get the "id" cubes.
     *
     * @param id the id of the cubes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cubes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cubes/{id}")
    public ResponseEntity<Cubes> getCubes(@PathVariable Long id) {
        log.debug("REST request to get Cubes : {}", id);
        Optional<Cubes> cubes = cubesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cubes);
    }

    /**
     * {@code DELETE  /cubes/:id} : delete the "id" cubes.
     *
     * @param id the id of the cubes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cubes/{id}")
    public ResponseEntity<Void> deleteCubes(@PathVariable Long id) {
        log.debug("REST request to delete Cubes : {}", id);
        cubesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cubes?query=:query} : search for the cubes corresponding
     * to the query.
     *
     * @param query the query of the cubes search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cubes")
    public ResponseEntity<List<Cubes>> searchCubes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Cubes for query {}", query);
        Page<Cubes> page = cubesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
