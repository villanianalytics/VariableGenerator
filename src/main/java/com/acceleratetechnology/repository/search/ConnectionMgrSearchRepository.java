package com.acceleratetechnology.repository.search;

import com.acceleratetechnology.domain.ConnectionMgr;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link ConnectionMgr} entity.
 */
public interface ConnectionMgrSearchRepository extends ElasticsearchRepository<ConnectionMgr, Long> {
}
