package com.acceleratetechnology.web.rest;

import com.acceleratetechnology.VariableGeneratorApp;
import com.acceleratetechnology.domain.VariableName;
import com.acceleratetechnology.repository.VariableNameRepository;
import com.acceleratetechnology.repository.search.VariableNameSearchRepository;
import com.acceleratetechnology.service.VariableNameService;

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
 * Integration tests for the {@link VariableNameResource} REST controller.
 */
@SpringBootTest(classes = VariableGeneratorApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class VariableNameResourceIT {

    private static final String DEFAULT_VARIABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VARIABLE_NAME = "BBBBBBBBBB";

    @Autowired
    private VariableNameRepository variableNameRepository;

    @Autowired
    private VariableNameService variableNameService;

    /**
     * This repository is mocked in the com.acceleratetechnology.repository.search test package.
     *
     * @see com.acceleratetechnology.repository.search.VariableNameSearchRepositoryMockConfiguration
     */
    @Autowired
    private VariableNameSearchRepository mockVariableNameSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVariableNameMockMvc;

    private VariableName variableName;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VariableName createEntity(EntityManager em) {
        VariableName variableName = new VariableName()
            .variableName(DEFAULT_VARIABLE_NAME);
        return variableName;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VariableName createUpdatedEntity(EntityManager em) {
        VariableName variableName = new VariableName()
            .variableName(UPDATED_VARIABLE_NAME);
        return variableName;
    }

    @BeforeEach
    public void initTest() {
        variableName = createEntity(em);
    }

    @Test
    @Transactional
    public void createVariableName() throws Exception {
        int databaseSizeBeforeCreate = variableNameRepository.findAll().size();
        // Create the VariableName
        restVariableNameMockMvc.perform(post("/api/variable-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variableName)))
            .andExpect(status().isCreated());

        // Validate the VariableName in the database
        List<VariableName> variableNameList = variableNameRepository.findAll();
        assertThat(variableNameList).hasSize(databaseSizeBeforeCreate + 1);
        VariableName testVariableName = variableNameList.get(variableNameList.size() - 1);
        assertThat(testVariableName.getVariableName()).isEqualTo(DEFAULT_VARIABLE_NAME);

        // Validate the VariableName in Elasticsearch
        verify(mockVariableNameSearchRepository, times(1)).save(testVariableName);
    }

    @Test
    @Transactional
    public void createVariableNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = variableNameRepository.findAll().size();

        // Create the VariableName with an existing ID
        variableName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVariableNameMockMvc.perform(post("/api/variable-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variableName)))
            .andExpect(status().isBadRequest());

        // Validate the VariableName in the database
        List<VariableName> variableNameList = variableNameRepository.findAll();
        assertThat(variableNameList).hasSize(databaseSizeBeforeCreate);

        // Validate the VariableName in Elasticsearch
        verify(mockVariableNameSearchRepository, times(0)).save(variableName);
    }


    @Test
    @Transactional
    public void getAllVariableNames() throws Exception {
        // Initialize the database
        variableNameRepository.saveAndFlush(variableName);

        // Get all the variableNameList
        restVariableNameMockMvc.perform(get("/api/variable-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variableName.getId().intValue())))
            .andExpect(jsonPath("$.[*].variableName").value(hasItem(DEFAULT_VARIABLE_NAME)));
    }
    
    @Test
    @Transactional
    public void getVariableName() throws Exception {
        // Initialize the database
        variableNameRepository.saveAndFlush(variableName);

        // Get the variableName
        restVariableNameMockMvc.perform(get("/api/variable-names/{id}", variableName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(variableName.getId().intValue()))
            .andExpect(jsonPath("$.variableName").value(DEFAULT_VARIABLE_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingVariableName() throws Exception {
        // Get the variableName
        restVariableNameMockMvc.perform(get("/api/variable-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariableName() throws Exception {
        // Initialize the database
        variableNameService.save(variableName);

        int databaseSizeBeforeUpdate = variableNameRepository.findAll().size();

        // Update the variableName
        VariableName updatedVariableName = variableNameRepository.findById(variableName.getId()).get();
        // Disconnect from session so that the updates on updatedVariableName are not directly saved in db
        em.detach(updatedVariableName);
        updatedVariableName
            .variableName(UPDATED_VARIABLE_NAME);

        restVariableNameMockMvc.perform(put("/api/variable-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVariableName)))
            .andExpect(status().isOk());

        // Validate the VariableName in the database
        List<VariableName> variableNameList = variableNameRepository.findAll();
        assertThat(variableNameList).hasSize(databaseSizeBeforeUpdate);
        VariableName testVariableName = variableNameList.get(variableNameList.size() - 1);
        assertThat(testVariableName.getVariableName()).isEqualTo(UPDATED_VARIABLE_NAME);

        // Validate the VariableName in Elasticsearch
        verify(mockVariableNameSearchRepository, times(2)).save(testVariableName);
    }

    @Test
    @Transactional
    public void updateNonExistingVariableName() throws Exception {
        int databaseSizeBeforeUpdate = variableNameRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVariableNameMockMvc.perform(put("/api/variable-names")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(variableName)))
            .andExpect(status().isBadRequest());

        // Validate the VariableName in the database
        List<VariableName> variableNameList = variableNameRepository.findAll();
        assertThat(variableNameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the VariableName in Elasticsearch
        verify(mockVariableNameSearchRepository, times(0)).save(variableName);
    }

    @Test
    @Transactional
    public void deleteVariableName() throws Exception {
        // Initialize the database
        variableNameService.save(variableName);

        int databaseSizeBeforeDelete = variableNameRepository.findAll().size();

        // Delete the variableName
        restVariableNameMockMvc.perform(delete("/api/variable-names/{id}", variableName.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VariableName> variableNameList = variableNameRepository.findAll();
        assertThat(variableNameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the VariableName in Elasticsearch
        verify(mockVariableNameSearchRepository, times(1)).deleteById(variableName.getId());
    }

    @Test
    @Transactional
    public void searchVariableName() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        variableNameService.save(variableName);
        when(mockVariableNameSearchRepository.search(queryStringQuery("id:" + variableName.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(variableName), PageRequest.of(0, 1), 1));

        // Search the variableName
        restVariableNameMockMvc.perform(get("/api/_search/variable-names?query=id:" + variableName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(variableName.getId().intValue())))
            .andExpect(jsonPath("$.[*].variableName").value(hasItem(DEFAULT_VARIABLE_NAME)));
    }
}
