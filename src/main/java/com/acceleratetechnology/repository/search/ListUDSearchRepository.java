package com.acceleratetechnology.repository.search;

import com.acceleratetechnology.domain.ListUD;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ListUD} entity.
 */
public interface ListUDSearchRepository extends ElasticsearchRepository<ListUD, Long> {
}
