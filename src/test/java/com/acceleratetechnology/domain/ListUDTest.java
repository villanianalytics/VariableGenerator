package com.acceleratetechnology.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.acceleratetechnology.web.rest.TestUtil;

public class ListUDTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListUD.class);
        ListUD listUD1 = new ListUD();
        listUD1.setId(1L);
        ListUD listUD2 = new ListUD();
        listUD2.setId(listUD1.getId());
        assertThat(listUD1).isEqualTo(listUD2);
        listUD2.setId(2L);
        assertThat(listUD1).isNotEqualTo(listUD2);
        listUD1.setId(null);
        assertThat(listUD1).isNotEqualTo(listUD2);
    }
}
