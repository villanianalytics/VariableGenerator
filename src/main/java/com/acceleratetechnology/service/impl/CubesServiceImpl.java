package com.acceleratetechnology.service.impl;

import com.acceleratetechnology.service.CubesService;
import com.acceleratetechnology.domain.Cubes;
import com.acceleratetechnology.repository.CubesRepository;
import com.acceleratetechnology.repository.search.CubesSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Cubes}.
 */
@Service
@Transactional
public class CubesServiceImpl implements CubesService {

    private final Logger log = LoggerFactory.getLogger(CubesServiceImpl.class);

    private final CubesRepository cubesRepository;

    private final CubesSearchRepository cubesSearchRepository;

    public CubesServiceImpl(CubesRepository cubesRepository, CubesSearchRepository cubesSearchRepository) {
        this.cubesRepository = cubesRepository;
        this.cubesSearchRepository = cubesSearchRepository;
    }

    @Override
    public Cubes save(Cubes cubes) {
        log.debug("Request to save Cubes : {}", cubes);
        Cubes result = cubesRepository.save(cubes);
        cubesSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cubes> findAll(Pageable pageable) {
        log.debug("Request to get all Cubes");
        return cubesRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Cubes> findOne(Long id) {
        log.debug("Request to get Cubes : {}", id);
        return cubesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cubes : {}", id);
        cubesRepository.deleteById(id);
        cubesSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cubes> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Cubes for query {}", query);
        return cubesSearchRepository.search(queryStringQuery(query), pageable);    }
}
