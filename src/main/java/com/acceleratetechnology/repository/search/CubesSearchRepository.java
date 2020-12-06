package com.acceleratetechnology.repository.search;

import com.acceleratetechnology.domain.Cubes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Cubes} entity.
 */
public interface CubesSearchRepository extends ElasticsearchRepository<Cubes, Long> {
}
