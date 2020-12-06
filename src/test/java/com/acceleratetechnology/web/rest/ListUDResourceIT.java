package com.acceleratetechnology.web.rest;

import com.acceleratetechnology.VariableGeneratorApp;
import com.acceleratetechnology.domain.ListUD;
import com.acceleratetechnology.repository.ListUDRepository;
import com.acceleratetechnology.repository.search.ListUDSearchRepository;
import com.acceleratetechnology.service.ListUDService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ListUDResource} REST controller.
 */
@SpringBootTest(classes = VariableGeneratorApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ListUDResourceIT {

    private static final String DEFAULT_LIST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LIST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LIST_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_LIST_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListUDRepository listUDRepository;

    @Autowired
    private ListUDService listUDService;

    /**
     * This repository is mocked in the com.acceleratetechnology.repository.search test package.
     *
     * @see com.acceleratetechnology.repository.search.ListUDSearchRepositoryMockConfiguration
     */
    @Autowired
    private ListUDSearchRepository mockListUDSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListUDMockMvc;

    private ListUD listUD;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListUD createEntity(EntityManager em) {
        ListUD listUD = new ListUD()
            .listName(DEFAULT_LIST_NAME)
            .listValue(DEFAULT_LIST_VALUE);
        return listUD;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListUD createUpdatedEntity(EntityManager em) {
        ListUD listUD = new ListUD()
            .listName(UPDATED_LIST_NAME)
            .listValue(UPDATED_LIST_VALUE);
        return listUD;
    }

    @BeforeEach
    public void initTest() {
        listUD = createEntity(em);
    }

    @Test
    @Transactional
    public void createListUD() throws Exception {
        int databaseSizeBeforeCreate = listUDRepository.findAll().size();
        // Create the ListUD
        restListUDMockMvc.perform(post("/api/list-uds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listUD)))
            .andExpect(status().isCreated());

        // Validate the ListUD in the database
        List<ListUD> listUDList = listUDRepository.findAll();
        assertThat(listUDList).hasSize(databaseSizeBeforeCreate + 1);
        ListUD testListUD = listUDList.get(listUDList.size() - 1);
        assertThat(testListUD.getListName()).isEqualTo(DEFAULT_LIST_NAME);
        assertThat(testListUD.getListValue()).isEqualTo(DEFAULT_LIST_VALUE);

        // Validate the ListUD in Elasticsearch
        verify(mockListUDSearchRepository, times(1)).save(testListUD);
    }

    @Test
    @Transactional
    public void createListUDWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listUDRepository.findAll().size();

        // Create the ListUD with an existing ID
        listUD.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListUDMockMvc.perform(post("/api/list-uds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listUD)))
            .andExpect(status().isBadRequest());

        // Validate the ListUD in the database
        List<ListUD> listUDList = listUDRepository.findAll();
        assertThat(listUDList).hasSize(databaseSizeBeforeCreate);

        // Validate the ListUD in Elasticsearch
        verify(mockListUDSearchRepository, times(0)).save(listUD);
    }


    @Test
    @Transactional
    public void getAllListUDS() throws Exception {
        // Initialize the database
        listUDRepository.saveAndFlush(listUD);

        // Get all the listUDList
        restListUDMockMvc.perform(get("/api/list-uds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listUD.getId().intValue())))
            .andExpect(jsonPath("$.[*].listName").value(hasItem(DEFAULT_LIST_NAME)))
            .andExpect(jsonPath("$.[*].listValue").value(hasItem(DEFAULT_LIST_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListUD() throws Exception {
        // Initialize the database
        listUDRepository.saveAndFlush(listUD);

        // Get the listUD
        restListUDMockMvc.perform(get("/api/list-uds/{id}", listUD.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(listUD.getId().intValue()))
            .andExpect(jsonPath("$.listName").value(DEFAULT_LIST_NAME))
            .andExpect(jsonPath("$.listValue").value(DEFAULT_LIST_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingListUD() throws Exception {
        // Get the listUD
        restListUDMockMvc.perform(get("/api/list-uds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListUD() throws Exception {
        // Initialize the database
        listUDService.save(listUD);

        int databaseSizeBeforeUpdate = listUDRepository.findAll().size();

        // Update the listUD
        ListUD updatedListUD = listUDRepository.findById(listUD.getId()).get();
        // Disconnect from session so that the updates on updatedListUD are not directly saved in db
        em.detach(updatedListUD);
        updatedListUD
            .listName(UPDATED_LIST_NAME)
            .listValue(UPDATED_LIST_VALUE);

        restListUDMockMvc.perform(put("/api/list-uds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedListUD)))
            .andExpect(status().isOk());

        // Validate the ListUD in the database
        List<ListUD> listUDList = listUDRepository.findAll();
        assertThat(listUDList).hasSize(databaseSizeBeforeUpdate);
        ListUD testListUD = listUDList.get(listUDList.size() - 1);
        assertThat(testListUD.getListName()).isEqualTo(UPDATED_LIST_NAME);
        assertThat(testListUD.getListValue()).isEqualTo(UPDATED_LIST_VALUE);

        // Validate the ListUD in Elasticsearch
        verify(mockListUDSearchRepository, times(2)).save(testListUD);
    }

    @Test
    @Transactional
    public void updateNonExistingListUD() throws Exception {
        int databaseSizeBeforeUpdate = listUDRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListUDMockMvc.perform(put("/api/list-uds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listUD)))
            .andExpect(status().isBadRequest());

        // Validate the ListUD in the database
        List<ListUD> listUDList = listUDRepository.findAll();
        assertThat(listUDList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ListUD in Elasticsearch
        verify(mockListUDSearchRepository, times(0)).save(listUD);
    }

    @Test
    @Transactional
    public void deleteListUD() throws Exception {
        // Initialize the database
        listUDService.save(listUD);

        int databaseSizeBeforeDelete = listUDRepository.findAll().size();

        // Delete the listUD
        restListUDMockMvc.perform(delete("/api/list-uds/{id}", listUD.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListUD> listUDList = listUDRepository.findAll();
        assertThat(listUDList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ListUD in Elasticsearch
        verify(mockListUDSearchRepository, times(1)).deleteById(listUD.getId());
    }

    @Test
    @Transactional
    public void searchListUD() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        listUDService.save(listUD);
        when(mockListUDSearchRepository.search(queryStringQuery("id:" + listUD.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(listUD), PageRequest.of(0, 1), 1));

        // Search the listUD
        restListUDMockMvc.perform(get("/api/_search/list-uds?query=id:" + listUD.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listUD.getId().intValue())))
            .andExpect(jsonPath("$.[*].listName").value(hasItem(DEFAULT_LIST_NAME)))
            .andExpect(jsonPath("$.[*].listValue").value(hasItem(DEFAULT_LIST_VALUE)));
    }
}
