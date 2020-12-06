package com.acceleratetechnology.service.impl;

import com.acceleratetechnology.service.AppService;
import com.acceleratetechnology.domain.App;
import com.acceleratetechnology.repository.AppRepository;
import com.acceleratetechnology.repository.search.AppSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link App}.
 */
@Service
@Transactional
public class AppServiceImpl implements AppService {

    private final Logger log = LoggerFactory.getLogger(AppServiceImpl.class);

    private final AppRepository appRepository;

    private final AppSearchRepository appSearchRepository;

    public AppServiceImpl(AppRepository appRepository, AppSearchRepository appSearchRepository) {
        this.appRepository = appRepository;
        this.appSearchRepository = appSearchRepository;
    }

    @Override
    public App save(App app) {
        log.debug("Request to save App : {}", app);
        App result = appRepository.save(app);
        appSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<App> findAll(Pageable pageable) {
        log.debug("Request to get all Apps");
        return appRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<App> findOne(Long id) {
        log.debug("Request to get App : {}", id);
        return appRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete App : {}", id);
        appRepository.deleteById(id);
        appSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<App> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Apps for query {}", query);
        return appSearchRepository.search(queryStringQuery(query), pageable);    }
}
