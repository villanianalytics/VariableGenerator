package com.acceleratetechnology.repository.search;

import com.acceleratetechnology.domain.Connection;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Connection} entity.
 */
public interface ConnectionSearchRepository extends ElasticsearchRepository<Connection, Long> {
}
