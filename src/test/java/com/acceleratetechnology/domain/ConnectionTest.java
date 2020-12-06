package com.acceleratetechnology.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.acceleratetechnology.web.rest.TestUtil;

public class ConnectionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Connection.class);
        Connection connection1 = new Connection();
        connection1.setId(1L);
        Connection connection2 = new Connection();
        connection2.setId(connection1.getId());
        assertThat(connection1).isEqualTo(connection2);
        connection2.setId(2L);
        assertThat(connection1).isNotEqualTo(connection2);
        connection1.setId(null);
        assertThat(connection1).isNotEqualTo(connection2);
    }
}
