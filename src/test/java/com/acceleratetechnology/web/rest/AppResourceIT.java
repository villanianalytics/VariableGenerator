package com.acceleratetechnology.web.rest;

import com.acceleratetechnology.VariableGeneratorApp;
import com.acceleratetechnology.domain.App;
import com.acceleratetechnology.repository.AppRepository;
import com.acceleratetechnology.repository.search.AppSearchRepository;
import com.acceleratetechnology.service.AppService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.acceleratetechnology.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AppResource} REST controller.
 */
@SpringBootTest(classes = VariableGeneratorApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AppResourceIT {

    private static final String DEFAULT_VARIABLE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VARIABLE_VALUE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EFF_DT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EFF_DT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AppRepository appRepository;

    @Autowired
    private AppService appService;

    /**
     * This repository is mocked in the com.acceleratetechnology.repository.search test package.
     *
     * @see com.acceleratetechnology.repository.search.AppSearchRepositoryMockConfiguration
     */
    @Autowired
    private AppSearchRepository mockAppSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppMockMvc;

    private App app;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static App createEntity(EntityManager em) {
        App app = new App()
            .variableValue(DEFAULT_VARIABLE_VALUE)
            .effDt(DEFAULT_EFF_DT);
        return app;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static App createUpdatedEntity(EntityManager em) {
        App app = new App()
            .variableValue(UPDATED_VARIABLE_VALUE)
            .effDt(UPDATED_EFF_DT);
        return app;
    }

    @BeforeEach
    public void initTest() {
        app = createEntity(em);
    }

    @Test
    @Transactional
    public void createApp() throws Exception {
        int databaseSizeBeforeCreate = appRepository.findAll().size();
        // Create the App
        restAppMockMvc.perform(post("/api/apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(app)))
            .andExpect(status().isCreated());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeCreate + 1);
        App testApp = appList.get(appList.size() - 1);
        assertThat(testApp.getVariableValue()).isEqualTo(DEFAULT_VARIABLE_VALUE);
        assertThat(testApp.getEffDt()).isEqualTo(DEFAULT_EFF_DT);

        // Validate the App in Elasticsearch
        verify(mockAppSearchRepository, times(1)).save(testApp);
    }

    @Test
    @Transactional
    public void createAppWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appRepository.findAll().size();

        // Create the App with an existing ID
        app.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppMockMvc.perform(post("/api/apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(app)))
            .andExpect(status().isBadRequest());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeCreate);

        // Validate the App in Elasticsearch
        verify(mockAppSearchRepository, times(0)).save(app);
    }


    @Test
    @Transactional
    public void getAllApps() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        // Get all the appList
        restAppMockMvc.perform(get("/api/apps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(app.getId().intValue())))
            .andExpect(jsonPath("$.[*].variableValue").value(hasItem(DEFAULT_VARIABLE_VALUE)))
            .andExpect(jsonPath("$.[*].effDt").value(hasItem(sameInstant(DEFAULT_EFF_DT))));
    }
    
    @Test
    @Transactional
    public void getApp() throws Exception {
        // Initialize the database
        appRepository.saveAndFlush(app);

        // Get the app
        restAppMockMvc.perform(get("/api/apps/{id}", app.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(app.getId().intValue()))
            .andExpect(jsonPath("$.variableValue").value(DEFAULT_VARIABLE_VALUE))
            .andExpect(jsonPath("$.effDt").value(sameInstant(DEFAULT_EFF_DT)));
    }
    @Test
    @Transactional
    public void getNonExistingApp() throws Exception {
        // Get the app
        restAppMockMvc.perform(get("/api/apps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApp() throws Exception {
        // Initialize the database
        appService.save(app);

        int databaseSizeBeforeUpdate = appRepository.findAll().size();

        // Update the app
        App updatedApp = appRepository.findById(app.getId()).get();
        // Disconnect from session so that the updates on updatedApp are not directly saved in db
        em.detach(updatedApp);
        updatedApp
            .variableValue(UPDATED_VARIABLE_VALUE)
            .effDt(UPDATED_EFF_DT);

        restAppMockMvc.perform(put("/api/apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedApp)))
            .andExpect(status().isOk());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeUpdate);
        App testApp = appList.get(appList.size() - 1);
        assertThat(testApp.getVariableValue()).isEqualTo(UPDATED_VARIABLE_VALUE);
        assertThat(testApp.getEffDt()).isEqualTo(UPDATED_EFF_DT);

        // Validate the App in Elasticsearch
        verify(mockAppSearchRepository, times(2)).save(testApp);
    }

    @Test
    @Transactional
    public void updateNonExistingApp() throws Exception {
        int databaseSizeBeforeUpdate = appRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppMockMvc.perform(put("/api/apps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(app)))
            .andExpect(status().isBadRequest());

        // Validate the App in the database
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeUpdate);

        // Validate the App in Elasticsearch
        verify(mockAppSearchRepository, times(0)).save(app);
    }

    @Test
    @Transactional
    public void deleteApp() throws Exception {
        // Initialize the database
        appService.save(app);

        int databaseSizeBeforeDelete = appRepository.findAll().size();

        // Delete the app
        restAppMockMvc.perform(delete("/api/apps/{id}", app.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<App> appList = appRepository.findAll();
        assertThat(appList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the App in Elasticsearch
        verify(mockAppSearchRepository, times(1)).deleteById(app.getId());
    }

    @Test
    @Transactional
    public void searchApp() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        appService.save(app);
        when(mockAppSearchRepository.search(queryStringQuery("id:" + app.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(app), PageRequest.of(0, 1), 1));

        // Search the app
        restAppMockMvc.perform(get("/api/_search/apps?query=id:" + app.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(app.getId().intValue())))
            .andExpect(jsonPath("$.[*].variableValue").value(hasItem(DEFAULT_VARIABLE_VALUE)))
            .andExpect(jsonPath("$.[*].effDt").value(hasItem(sameInstant(DEFAULT_EFF_DT))));
    }
}
