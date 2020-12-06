package com.acceleratetechnology.repository.search;

import com.acceleratetechnology.domain.VariableName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link VariableName} entity.
 */
public interface VariableNameSearchRepository extends ElasticsearchRepository<VariableName, Long> {
}
