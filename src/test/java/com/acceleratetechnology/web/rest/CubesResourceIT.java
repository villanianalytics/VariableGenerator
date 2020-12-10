package com.acceleratetechnology.web.rest;

import com.acceleratetechnology.VariableGeneratorApp;
import com.acceleratetechnology.domain.Cubes;
import com.acceleratetechnology.repository.CubesRepository;
import com.acceleratetechnology.repository.search.CubesSearchRepository;
import com.acceleratetechnology.service.CubesService;

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
 * Integration tests for the {@link CubesResource} REST controller.
 */
@SpringBootTest(classes = VariableGeneratorApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CubesResourceIT {

    @Autowired
    private CubesRepository cubesRepository;

    @Autowired
    private CubesService cubesService;

    /**
     * This repository is mocked in the com.acceleratetechnology.repository.search test package.
     *
     * @see com.acceleratetechnology.repository.search.CubesSearchRepositoryMockConfiguration
     */
    @Autowired
    private CubesSearchRepository mockCubesSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCubesMockMvc;

    private Cubes cubes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cubes createEntity(EntityManager em) {
        Cubes cubes = new Cubes();
        return cubes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cubes createUpdatedEntity(EntityManager em) {
        Cubes cubes = new Cubes();
        return cubes;
    }

    @BeforeEach
    public void initTest() {
        cubes = createEntity(em);
    }

    @Test
    @Transactional
    public void createCubes() throws Exception {
        int databaseSizeBeforeCreate = cubesRepository.findAll().size();
        // Create the Cubes
        restCubesMockMvc.perform(post("/api/cubes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cubes)))
            .andExpect(status().isCreated());

        // Validate the Cubes in the database
        List<Cubes> cubesList = cubesRepository.findAll();
        assertThat(cubesList).hasSize(databaseSizeBeforeCreate + 1);
        Cubes testCubes = cubesList.get(cubesList.size() - 1);

        // Validate the Cubes in Elasticsearch
        verify(mockCubesSearchRepository, times(1)).save(testCubes);
    }

    @Test
    @Transactional
    public void createCubesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cubesRepository.findAll().size();

        // Create the Cubes with an existing ID
        cubes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCubesMockMvc.perform(post("/api/cubes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cubes)))
            .andExpect(status().isBadRequest());

        // Validate the Cubes in the database
        List<Cubes> cubesList = cubesRepository.findAll();
        assertThat(cubesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Cubes in Elasticsearch
        verify(mockCubesSearchRepository, times(0)).save(cubes);
    }


    @Test
    @Transactional
    public void getAllCubes() throws Exception {
        // Initialize the database
        cubesRepository.saveAndFlush(cubes);

        // Get all the cubesList
        restCubesMockMvc.perform(get("/api/cubes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cubes.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCubes() throws Exception {
        // Initialize the database
        cubesRepository.saveAndFlush(cubes);

        // Get the cubes
        restCubesMockMvc.perform(get("/api/cubes/{id}", cubes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cubes.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCubes() throws Exception {
        // Get the cubes
        restCubesMockMvc.perform(get("/api/cubes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCubes() throws Exception {
        // Initialize the database
        cubesService.save(cubes);

        int databaseSizeBeforeUpdate = cubesRepository.findAll().size();

        // Update the cubes
        Cubes updatedCubes = cubesRepository.findById(cubes.getId()).get();
        // Disconnect from session so that the updates on updatedCubes are not directly saved in db
        em.detach(updatedCubes);

        restCubesMockMvc.perform(put("/api/cubes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCubes)))
            .andExpect(status().isOk());

        // Validate the Cubes in the database
        List<Cubes> cubesList = cubesRepository.findAll();
        assertThat(cubesList).hasSize(databaseSizeBeforeUpdate);
        Cubes testCubes = cubesList.get(cubesList.size() - 1);

        // Validate the Cubes in Elasticsearch
        verify(mockCubesSearchRepository, times(2)).save(testCubes);
    }

    @Test
    @Transactional
    public void updateNonExistingCubes() throws Exception {
        int databaseSizeBeforeUpdate = cubesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCubesMockMvc.perform(put("/api/cubes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cubes)))
            .andExpect(status().isBadRequest());

        // Validate the Cubes in the database
        List<Cubes> cubesList = cubesRepository.findAll();
        assertThat(cubesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cubes in Elasticsearch
        verify(mockCubesSearchRepository, times(0)).save(cubes);
    }

    @Test
    @Transactional
    public void deleteCubes() throws Exception {
        // Initialize the database
        cubesService.save(cubes);

        int databaseSizeBeforeDelete = cubesRepository.findAll().size();

        // Delete the cubes
        restCubesMockMvc.perform(delete("/api/cubes/{id}", cubes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cubes> cubesList = cubesRepository.findAll();
        assertThat(cubesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Cubes in Elasticsearch
        verify(mockCubesSearchRepository, times(1)).deleteById(cubes.getId());
    }

    @Test
    @Transactional
    public void searchCubes() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        cubesService.save(cubes);
        when(mockCubesSearchRepository.search(queryStringQuery("id:" + cubes.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cubes), PageRequest.of(0, 1), 1));

        // Search the cubes
        restCubesMockMvc.perform(get("/api/_search/cubes?query=id:" + cubes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cubes.getId().intValue())));
    }
}
