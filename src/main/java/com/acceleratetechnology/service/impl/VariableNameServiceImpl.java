package com.acceleratetechnology.service.impl;

import com.acceleratetechnology.service.VariableNameService;
import com.acceleratetechnology.domain.VariableName;
import com.acceleratetechnology.repository.VariableNameRepository;
import com.acceleratetechnology.repository.search.VariableNameSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link VariableName}.
 */
@Service
@Transactional
public class VariableNameServiceImpl implements VariableNameService {

    private final Logger log = LoggerFactory.getLogger(VariableNameServiceImpl.class);

    private final VariableNameRepository variableNameRepository;

    private final VariableNameSearchRepository variableNameSearchRepository;

    public VariableNameServiceImpl(VariableNameRepository variableNameRepository, VariableNameSearchRepository variableNameSearchRepository) {
        this.variableNameRepository = variableNameRepository;
        this.variableNameSearchRepository = variableNameSearchRepository;
    }

    @Override
    public VariableName save(VariableName variableName) {
        log.debug("Request to save VariableName : {}", variableName);
        VariableName result = variableNameRepository.save(variableName);
        variableNameSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VariableName> findAll(Pageable pageable) {
        log.debug("Request to get all VariableNames");
        return variableNameRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<VariableName> findOne(Long id) {
        log.debug("Request to get VariableName : {}", id);
        return variableNameRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete VariableName : {}", id);
        variableNameRepository.deleteById(id);
        variableNameSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VariableName> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of VariableNames for query {}", query);
        return variableNameSearchRepository.search(queryStringQuery(query), pageable);    }
}
