package com.acceleratetechnology.web.rest;

import com.acceleratetechnology.domain.ListUD;
import com.acceleratetechnology.service.ListUDService;
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
 * REST controller for managing {@link com.acceleratetechnology.domain.ListUD}.
 */
@RestController
@RequestMapping("/api")
public class ListUDResource {

    private final Logger log = LoggerFactory.getLogger(ListUDResource.class);

    private static final String ENTITY_NAME = "listUD";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ListUDService listUDService;

    public ListUDResource(ListUDService listUDService) {
        this.listUDService = listUDService;
    }

    /**
     * {@code POST  /list-uds} : Create a new listUD.
     *
     * @param listUD the listUD to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listUD, or with status {@code 400 (Bad Request)} if the listUD has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/list-uds")
    public ResponseEntity<ListUD> createListUD(@RequestBody ListUD listUD) throws URISyntaxException {
        log.debug("REST request to save ListUD : {}", listUD);
        if (listUD.getId() != null) {
            throw new BadRequestAlertException("A new listUD cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ListUD result = listUDService.save(listUD);
        return ResponseEntity.created(new URI("/api/list-uds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /list-uds} : Updates an existing listUD.
     *
     * @param listUD the listUD to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listUD,
     * or with status {@code 400 (Bad Request)} if the listUD is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listUD couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/list-uds")
    public ResponseEntity<ListUD> updateListUD(@RequestBody ListUD listUD) throws URISyntaxException {
        log.debug("REST request to update ListUD : {}", listUD);
        if (listUD.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ListUD result = listUDService.save(listUD);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, listUD.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /list-uds} : get all the listUDS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listUDS in body.
     */
    @GetMapping("/list-uds")
    public ResponseEntity<List<ListUD>> getAllListUDS(Pageable pageable) {
        log.debug("REST request to get a page of ListUDS");
        Page<ListUD> page = listUDService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /list-uds/:id} : get the "id" listUD.
     *
     * @param id the id of the listUD to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listUD, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/list-uds/{id}")
    public ResponseEntity<ListUD> getListUD(@PathVariable Long id) {
        log.debug("REST request to get ListUD : {}", id);
        Optional<ListUD> listUD = listUDService.findOne(id);
        return ResponseUtil.wrapOrNotFound(listUD);
    }

    /**
     * {@code DELETE  /list-uds/:id} : delete the "id" listUD.
     *
     * @param id the id of the listUD to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/list-uds/{id}")
    public ResponseEntity<Void> deleteListUD(@PathVariable Long id) {
        log.debug("REST request to delete ListUD : {}", id);
        listUDService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/list-uds?query=:query} : search for the listUD corresponding
     * to the query.
     *
     * @param query the query of the listUD search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/list-uds")
    public ResponseEntity<List<ListUD>> searchListUDS(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ListUDS for query {}", query);
        Page<ListUD> page = listUDService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
