package com.acceleratetechnology.web.rest;

import com.acceleratetechnology.domain.VariableName;
import com.acceleratetechnology.service.VariableNameService;
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
 * REST controller for managing {@link com.acceleratetechnology.domain.VariableName}.
 */
@RestController
@RequestMapping("/api")
public class VariableNameResource {

    private final Logger log = LoggerFactory.getLogger(VariableNameResource.class);

    private static final String ENTITY_NAME = "variableName";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VariableNameService variableNameService;

    public VariableNameResource(VariableNameService variableNameService) {
        this.variableNameService = variableNameService;
    }

    /**
     * {@code POST  /variable-names} : Create a new variableName.
     *
     * @param variableName the variableName to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new variableName, or with status {@code 400 (Bad Request)} if the variableName has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/variable-names")
    public ResponseEntity<VariableName> createVariableName(@RequestBody VariableName variableName) throws URISyntaxException {
        log.debug("REST request to save VariableName : {}", variableName);
        if (variableName.getId() != null) {
            throw new BadRequestAlertException("A new variableName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VariableName result = variableNameService.save(variableName);
        return ResponseEntity.created(new URI("/api/variable-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /variable-names} : Updates an existing variableName.
     *
     * @param variableName the variableName to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated variableName,
     * or with status {@code 400 (Bad Request)} if the variableName is not valid,
     * or with status {@code 500 (Internal Server Error)} if the variableName couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/variable-names")
    public ResponseEntity<VariableName> updateVariableName(@RequestBody VariableName variableName) throws URISyntaxException {
        log.debug("REST request to update VariableName : {}", variableName);
        if (variableName.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VariableName result = variableNameService.save(variableName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, variableName.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /variable-names} : get all the variableNames.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of variableNames in body.
     */
    @GetMapping("/variable-names")
    public ResponseEntity<List<VariableName>> getAllVariableNames(Pageable pageable) {
        log.debug("REST request to get a page of VariableNames");
        Page<VariableName> page = variableNameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /variable-names/:id} : get the "id" variableName.
     *
     * @param id the id of the variableName to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the variableName, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/variable-names/{id}")
    public ResponseEntity<VariableName> getVariableName(@PathVariable Long id) {
        log.debug("REST request to get VariableName : {}", id);
        Optional<VariableName> variableName = variableNameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(variableName);
    }

    /**
     * {@code DELETE  /variable-names/:id} : delete the "id" variableName.
     *
     * @param id the id of the variableName to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/variable-names/{id}")
    public ResponseEntity<Void> deleteVariableName(@PathVariable Long id) {
        log.debug("REST request to delete VariableName : {}", id);
        variableNameService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/variable-names?query=:query} : search for the variableName corresponding
     * to the query.
     *
     * @param query the query of the variableName search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/variable-names")
    public ResponseEntity<List<VariableName>> searchVariableNames(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of VariableNames for query {}", query);
        Page<VariableName> page = variableNameService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
