package com.acceleratetechnology.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.acceleratetechnology.web.rest.TestUtil;

public class ConnectionMgrTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConnectionMgr.class);
        ConnectionMgr connectionMgr1 = new ConnectionMgr();
        connectionMgr1.setId(1L);
        ConnectionMgr connectionMgr2 = new ConnectionMgr();
        connectionMgr2.setId(connectionMgr1.getId());
        assertThat(connectionMgr1).isEqualTo(connectionMgr2);
        connectionMgr2.setId(2L);
        assertThat(connectionMgr1).isNotEqualTo(connectionMgr2);
        connectionMgr1.setId(null);
        assertThat(connectionMgr1).isNotEqualTo(connectionMgr2);
    }
}
