package com.acceleratetechnology.service.impl;

import com.acceleratetechnology.service.ConnectionService;
import com.acceleratetechnology.domain.Connection;
import com.acceleratetechnology.repository.ConnectionRepository;
import com.acceleratetechnology.repository.search.ConnectionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Connection}.
 */
@Service
@Transactional
public class ConnectionServiceImpl implements ConnectionService {

    private final Logger log = LoggerFactory.getLogger(ConnectionServiceImpl.class);

    private final ConnectionRepository connectionRepository;

    private final ConnectionSearchRepository connectionSearchRepository;

    public ConnectionServiceImpl(ConnectionRepository connectionRepository, ConnectionSearchRepository connectionSearchRepository) {
        this.connectionRepository = connectionRepository;
        this.connectionSearchRepository = connectionSearchRepository;
    }

    @Override
    public Connection save(Connection connection) {
        log.debug("Request to save Connection : {}", connection);
        Connection result = connectionRepository.save(connection);
        connectionSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Connection> findAll() {
        log.debug("Request to get all Connections");
        return connectionRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Connection> findOne(Long id) {
        log.debug("Request to get Connection : {}", id);
        return connectionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Connection : {}", id);
        connectionRepository.deleteById(id);
        connectionSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Connection> search(String query) {
        log.debug("Request to search Connections for query {}", query);
        return StreamSupport
            .stream(connectionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
