package com.acceleratetechnology.service.impl;

import com.acceleratetechnology.service.ListUDService;
import com.acceleratetechnology.domain.ListUD;
import com.acceleratetechnology.repository.ListUDRepository;
import com.acceleratetechnology.repository.search.ListUDSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ListUD}.
 */
@Service
@Transactional
public class ListUDServiceImpl implements ListUDService {

    private final Logger log = LoggerFactory.getLogger(ListUDServiceImpl.class);

    private final ListUDRepository listUDRepository;

    private final ListUDSearchRepository listUDSearchRepository;

    public ListUDServiceImpl(ListUDRepository listUDRepository, ListUDSearchRepository listUDSearchRepository) {
        this.listUDRepository = listUDRepository;
        this.listUDSearchRepository = listUDSearchRepository;
    }

    @Override
    public ListUD save(ListUD listUD) {
        log.debug("Request to save ListUD : {}", listUD);
        ListUD result = listUDRepository.save(listUD);
        listUDSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ListUD> findAll(Pageable pageable) {
        log.debug("Request to get all ListUDS");
        return listUDRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ListUD> findOne(Long id) {
        log.debug("Request to get ListUD : {}", id);
        return listUDRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ListUD : {}", id);
        listUDRepository.deleteById(id);
        listUDSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ListUD> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ListUDS for query {}", query);
        return listUDSearchRepository.search(queryStringQuery(query), pageable);    }
}
