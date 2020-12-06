package com.acceleratetechnology.web.rest;

import com.acceleratetechnology.VariableGeneratorApp;
import com.acceleratetechnology.domain.Connection;
import com.acceleratetechnology.repository.ConnectionRepository;
import com.acceleratetechnology.repository.search.ConnectionSearchRepository;
import com.acceleratetechnology.service.ConnectionService;

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
 * Integration tests for the {@link ConnectionResource} REST controller.
 */
@SpringBootTest(classes = VariableGeneratorApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConnectionResourceIT {

    private static final String DEFAULT_CONNECTION = "AAAAAAAAAA";
    private static final String UPDATED_CONNECTION = "BBBBBBBBBB";

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
    private ConnectionRepository connectionRepository;

    @Autowired
    private ConnectionService connectionService;

    /**
     * This repository is mocked in the com.acceleratetechnology.repository.search test package.
     *
     * @see com.acceleratetechnology.repository.search.ConnectionSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConnectionSearchRepository mockConnectionSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConnectionMockMvc;

    private Connection connection;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Connection createEntity(EntityManager em) {
        Connection connection = new Connection()
            .connection(DEFAULT_CONNECTION)
            .description(DEFAULT_DESCRIPTION)
            .uRL(DEFAULT_U_RL)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .identityDomain(DEFAULT_IDENTITY_DOMAIN);
        return connection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Connection createUpdatedEntity(EntityManager em) {
        Connection connection = new Connection()
            .connection(UPDATED_CONNECTION)
            .description(UPDATED_DESCRIPTION)
            .uRL(UPDATED_U_RL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .identityDomain(UPDATED_IDENTITY_DOMAIN);
        return connection;
    }

    @BeforeEach
    public void initTest() {
        connection = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnection() throws Exception {
        int databaseSizeBeforeCreate = connectionRepository.findAll().size();
        // Create the Connection
        restConnectionMockMvc.perform(post("/api/connections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connection)))
            .andExpect(status().isCreated());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeCreate + 1);
        Connection testConnection = connectionList.get(connectionList.size() - 1);
        assertThat(testConnection.getConnection()).isEqualTo(DEFAULT_CONNECTION);
        assertThat(testConnection.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConnection.getuRL()).isEqualTo(DEFAULT_U_RL);
        assertThat(testConnection.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testConnection.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testConnection.getIdentityDomain()).isEqualTo(DEFAULT_IDENTITY_DOMAIN);

        // Validate the Connection in Elasticsearch
        verify(mockConnectionSearchRepository, times(1)).save(testConnection);
    }

    @Test
    @Transactional
    public void createConnectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connectionRepository.findAll().size();

        // Create the Connection with an existing ID
        connection.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectionMockMvc.perform(post("/api/connections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connection)))
            .andExpect(status().isBadRequest());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Connection in Elasticsearch
        verify(mockConnectionSearchRepository, times(0)).save(connection);
    }


    @Test
    @Transactional
    public void getAllConnections() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get all the connectionList
        restConnectionMockMvc.perform(get("/api/connections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connection.getId().intValue())))
            .andExpect(jsonPath("$.[*].connection").value(hasItem(DEFAULT_CONNECTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uRL").value(hasItem(DEFAULT_U_RL)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].identityDomain").value(hasItem(DEFAULT_IDENTITY_DOMAIN)));
    }
    
    @Test
    @Transactional
    public void getConnection() throws Exception {
        // Initialize the database
        connectionRepository.saveAndFlush(connection);

        // Get the connection
        restConnectionMockMvc.perform(get("/api/connections/{id}", connection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(connection.getId().intValue()))
            .andExpect(jsonPath("$.connection").value(DEFAULT_CONNECTION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uRL").value(DEFAULT_U_RL))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.identityDomain").value(DEFAULT_IDENTITY_DOMAIN));
    }
    @Test
    @Transactional
    public void getNonExistingConnection() throws Exception {
        // Get the connection
        restConnectionMockMvc.perform(get("/api/connections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnection() throws Exception {
        // Initialize the database
        connectionService.save(connection);

        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();

        // Update the connection
        Connection updatedConnection = connectionRepository.findById(connection.getId()).get();
        // Disconnect from session so that the updates on updatedConnection are not directly saved in db
        em.detach(updatedConnection);
        updatedConnection
            .connection(UPDATED_CONNECTION)
            .description(UPDATED_DESCRIPTION)
            .uRL(UPDATED_U_RL)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .identityDomain(UPDATED_IDENTITY_DOMAIN);

        restConnectionMockMvc.perform(put("/api/connections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConnection)))
            .andExpect(status().isOk());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);
        Connection testConnection = connectionList.get(connectionList.size() - 1);
        assertThat(testConnection.getConnection()).isEqualTo(UPDATED_CONNECTION);
        assertThat(testConnection.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConnection.getuRL()).isEqualTo(UPDATED_U_RL);
        assertThat(testConnection.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testConnection.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testConnection.getIdentityDomain()).isEqualTo(UPDATED_IDENTITY_DOMAIN);

        // Validate the Connection in Elasticsearch
        verify(mockConnectionSearchRepository, times(2)).save(testConnection);
    }

    @Test
    @Transactional
    public void updateNonExistingConnection() throws Exception {
        int databaseSizeBeforeUpdate = connectionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectionMockMvc.perform(put("/api/connections")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connection)))
            .andExpect(status().isBadRequest());

        // Validate the Connection in the database
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Connection in Elasticsearch
        verify(mockConnectionSearchRepository, times(0)).save(connection);
    }

    @Test
    @Transactional
    public void deleteConnection() throws Exception {
        // Initialize the database
        connectionService.save(connection);

        int databaseSizeBeforeDelete = connectionRepository.findAll().size();

        // Delete the connection
        restConnectionMockMvc.perform(delete("/api/connections/{id}", connection.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Connection> connectionList = connectionRepository.findAll();
        assertThat(connectionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Connection in Elasticsearch
        verify(mockConnectionSearchRepository, times(1)).deleteById(connection.getId());
    }

    @Test
    @Transactional
    public void searchConnection() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        connectionService.save(connection);
        when(mockConnectionSearchRepository.search(queryStringQuery("id:" + connection.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(connection), PageRequest.of(0, 1), 1));

        // Search the connection
        restConnectionMockMvc.perform(get("/api/_search/connections?query=id:" + connection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connection.getId().intValue())))
            .andExpect(jsonPath("$.[*].connection").value(hasItem(DEFAULT_CONNECTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uRL").value(hasItem(DEFAULT_U_RL)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].identityDomain").value(hasItem(DEFAULT_IDENTITY_DOMAIN)));
    }
}
