package com.acceleratetechnology.web.rest;

import com.acceleratetechnology.VariableGeneratorApp;
import com.acceleratetechnology.domain.ConnectionMgr;
import com.acceleratetechnology.repository.ConnectionMgrRepository;
import com.acceleratetechnology.repository.search.ConnectionMgrSearchRepository;
import com.acceleratetechnology.service.ConnectionMgrService;

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
 * Integration tests for the {@link ConnectionMgrResource} REST controller.
 */
@SpringBootTest(classes = VariableGeneratorApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConnectionMgrResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_U_RL = "AAAAAAAAAA";
    private static final String UPDATED_U_RL = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_IDENTITY_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_IDENTITY_DOMAIN = "BBBBBBBBBB";

    @Autowired
    private ConnectionMgrRepository connectionMgrRepository;

    @Autowired
    private ConnectionMgrService connectionMgrService;

    /**
     * This repository is mocked in the com.acceleratetechnology.repository.search test package.
     *
     * @see com.acceleratetechnology.repository.search.ConnectionMgrSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConnectionMgrSearchRepository mockConnectionMgrSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConnectionMgrMockMvc;

    private ConnectionMgr connectionMgr;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectionMgr createEntity(EntityManager em) {
        ConnectionMgr connectionMgr = new ConnectionMgr()
            .description(DEFAULT_DESCRIPTION)
            .uRL(DEFAULT_U_RL)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .identityDomain(DEFAULT_IDENTITY_DOMAIN);
        return connectionMgr;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectionMgr createUpdatedEntity(EntityManager em) {
        ConnectionMgr connectionMgr = new ConnectionMgr()
            .description(UPDATED_DESCRIPTION)
            .uRL(UPDATED_U_RL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .identityDomain(UPDATED_IDENTITY_DOMAIN);
        return connectionMgr;
    }

    @BeforeEach
    public void initTest() {
        connectionMgr = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnectionMgr() throws Exception {
        int databaseSizeBeforeCreate = connectionMgrRepository.findAll().size();
        // Create the ConnectionMgr
        restConnectionMgrMockMvc.perform(post("/api/connection-mgrs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectionMgr)))
            .andExpect(status().isCreated());

        // Validate the ConnectionMgr in the database
        List<ConnectionMgr> connectionMgrList = connectionMgrRepository.findAll();
        assertThat(connectionMgrList).hasSize(databaseSizeBeforeCreate + 1);
        ConnectionMgr testConnectionMgr = connectionMgrList.get(connectionMgrList.size() - 1);
        assertThat(testConnectionMgr.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConnectionMgr.getuRL()).isEqualTo(DEFAULT_U_RL);
        assertThat(testConnectionMgr.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testConnectionMgr.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testConnectionMgr.getIdentityDomain()).isEqualTo(DEFAULT_IDENTITY_DOMAIN);

        // Validate the ConnectionMgr in Elasticsearch
        verify(mockConnectionMgrSearchRepository, times(1)).save(testConnectionMgr);
    }

    @Test
    @Transactional
    public void createConnectionMgrWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connectionMgrRepository.findAll().size();

        // Create the ConnectionMgr with an existing ID
        connectionMgr.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectionMgrMockMvc.perform(post("/api/connection-mgrs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectionMgr)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectionMgr in the database
        List<ConnectionMgr> connectionMgrList = connectionMgrRepository.findAll();
        assertThat(connectionMgrList).hasSize(databaseSizeBeforeCreate);

        // Validate the ConnectionMgr in Elasticsearch
        verify(mockConnectionMgrSearchRepository, times(0)).save(connectionMgr);
    }


    @Test
    @Transactional
    public void getAllConnectionMgrs() throws Exception {
        // Initialize the database
        connectionMgrRepository.saveAndFlush(connectionMgr);

        // Get all the connectionMgrList
        restConnectionMgrMockMvc.perform(get("/api/connection-mgrs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connectionMgr.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uRL").value(hasItem(DEFAULT_U_RL)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].identityDomain").value(hasItem(DEFAULT_IDENTITY_DOMAIN)));
    }
    
    @Test
    @Transactional
    public void getConnectionMgr() throws Exception {
        // Initialize the database
        connectionMgrRepository.saveAndFlush(connectionMgr);

        // Get the connectionMgr
        restConnectionMgrMockMvc.perform(get("/api/connection-mgrs/{id}", connectionMgr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(connectionMgr.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uRL").value(DEFAULT_U_RL))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.identityDomain").value(DEFAULT_IDENTITY_DOMAIN));
    }
    @Test
    @Transactional
    public void getNonExistingConnectionMgr() throws Exception {
        // Get the connectionMgr
        restConnectionMgrMockMvc.perform(get("/api/connection-mgrs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnectionMgr() throws Exception {
        // Initialize the database
        connectionMgrService.save(connectionMgr);

        int databaseSizeBeforeUpdate = connectionMgrRepository.findAll().size();

        // Update the connectionMgr
        ConnectionMgr updatedConnectionMgr = connectionMgrRepository.findById(connectionMgr.getId()).get();
        // Disconnect from session so that the updates on updatedConnectionMgr are not directly saved in db
        em.detach(updatedConnectionMgr);
        updatedConnectionMgr
            .description(UPDATED_DESCRIPTION)
            .uRL(UPDATED_U_RL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .identityDomain(UPDATED_IDENTITY_DOMAIN);

        restConnectionMgrMockMvc.perform(put("/api/connection-mgrs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConnectionMgr)))
            .andExpect(status().isOk());

        // Validate the ConnectionMgr in the database
        List<ConnectionMgr> connectionMgrList = connectionMgrRepository.findAll();
        assertThat(connectionMgrList).hasSize(databaseSizeBeforeUpdate);
        ConnectionMgr testConnectionMgr = connectionMgrList.get(connectionMgrList.size() - 1);
        assertThat(testConnectionMgr.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConnectionMgr.getuRL()).isEqualTo(UPDATED_U_RL);
        assertThat(testConnectionMgr.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testConnectionMgr.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testConnectionMgr.getIdentityDomain()).isEqualTo(UPDATED_IDENTITY_DOMAIN);

        // Validate the ConnectionMgr in Elasticsearch
        verify(mockConnectionMgrSearchRepository, times(2)).save(testConnectionMgr);
    }

    @Test
    @Transactional
    public void updateNonExistingConnectionMgr() throws Exception {
        int databaseSizeBeforeUpdate = connectionMgrRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectionMgrMockMvc.perform(put("/api/connection-mgrs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectionMgr)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectionMgr in the database
        List<ConnectionMgr> connectionMgrList = connectionMgrRepository.findAll();
        assertThat(connectionMgrList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ConnectionMgr in Elasticsearch
        verify(mockConnectionMgrSearchRepository, times(0)).save(connectionMgr);
    }

    @Test
    @Transactional
    public void deleteConnectionMgr() throws Exception {
        // Initialize the database
        connectionMgrService.save(connectionMgr);

        int databaseSizeBeforeDelete = connectionMgrRepository.findAll().size();

        // Delete the connectionMgr
        restConnectionMgrMockMvc.perform(delete("/api/connection-mgrs/{id}", connectionMgr.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConnectionMgr> connectionMgrList = connectionMgrRepository.findAll();
        assertThat(connectionMgrList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ConnectionMgr in Elasticsearch
        verify(mockConnectionMgrSearchRepository, times(1)).deleteById(connectionMgr.getId());
    }

    @Test
    @Transactional
    public void searchConnectionMgr() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        connectionMgrService.save(connectionMgr);
        when(mockConnectionMgrSearchRepository.search(queryStringQuery("id:" + connectionMgr.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(connectionMgr), PageRequest.of(0, 1), 1));

        // Search the connectionMgr
        restConnectionMgrMockMvc.perform(get("/api/_search/connection-mgrs?query=id:" + connectionMgr.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connectionMgr.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uRL").value(hasItem(DEFAULT_U_RL)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].identityDomain").value(hasItem(DEFAULT_IDENTITY_DOMAIN)));
    }
}
