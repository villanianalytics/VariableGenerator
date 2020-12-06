package com.acceleratetechnology.repository.search;

import com.acceleratetechnology.domain.App;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link App} entity.
 */
public interface AppSearchRepository extends ElasticsearchRepository<App, Long> {
}
