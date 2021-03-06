package com.acceleratetechnology.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ConnectionSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ConnectionSearchRepositoryMockConfiguration {

    @MockBean
    private ConnectionSearchRepository mockConnectionSearchRepository;

}
