package com.acceleratetechnology.service.impl;

import com.acceleratetechnology.service.ConnectionMgrService;
import com.acceleratetechnology.domain.ConnectionMgr;
import com.acceleratetechnology.repository.ConnectionMgrRepository;
import com.acceleratetechnology.repository.search.ConnectionMgrSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ConnectionMgr}.
 */
@Service
@Transactional
public class ConnectionMgrServiceImpl implements ConnectionMgrService {

    private final Logger log = LoggerFactory.getLogger(ConnectionMgrServiceImpl.class);

    private final ConnectionMgrRepository connectionMgrRepository;

    private final ConnectionMgrSearchRepository connectionMgrSearchRepository;

    public ConnectionMgrServiceImpl(ConnectionMgrRepository connectionMgrRepository, ConnectionMgrSearchRepository connectionMgrSearchRepository) {
        this.connectionMgrRepository = connectionMgrRepository;
        this.connectionMgrSearchRepository = connectionMgrSearchRepository;
    }

    @Override
    public ConnectionMgr save(ConnectionMgr connectionMgr) {
        log.debug("Request to save ConnectionMgr : {}", connectionMgr);
        ConnectionMgr result = connectionMgrRepository.save(connectionMgr);
        connectionMgrSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConnectionMgr> findAll(Pageable pageable) {
        log.debug("Request to get all ConnectionMgrs");
        return connectionMgrRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ConnectionMgr> findOne(Long id) {
        log.debug("Request to get ConnectionMgr : {}", id);
        return connectionMgrRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConnectionMgr : {}", id);
        connectionMgrRepository.deleteById(id);
        connectionMgrSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConnectionMgr> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ConnectionMgrs for query {}", query);
        return connectionMgrSearchRepository.search(queryStringQuery(query), pageable);    }
}
