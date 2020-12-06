package com.acceleratetechnology.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.acceleratetechnology.web.rest.TestUtil;

public class CubesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cubes.class);
        Cubes cubes1 = new Cubes();
        cubes1.setId(1L);
        Cubes cubes2 = new Cubes();
        cubes2.setId(cubes1.getId());
        assertThat(cubes1).isEqualTo(cubes2);
        cubes2.setId(2L);
        assertThat(cubes1).isNotEqualTo(cubes2);
        cubes1.setId(null);
        assertThat(cubes1).isNotEqualTo(cubes2);
    }
}
