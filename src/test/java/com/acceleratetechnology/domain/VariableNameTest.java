package com.acceleratetechnology.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.acceleratetechnology.web.rest.TestUtil;

public class VariableNameTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VariableName.class);
        VariableName variableName1 = new VariableName();
        variableName1.setId(1L);
        VariableName variableName2 = new VariableName();
        variableName2.setId(variableName1.getId());
        assertThat(variableName1).isEqualTo(variableName2);
        variableName2.setId(2L);
        assertThat(variableName1).isNotEqualTo(variableName2);
        variableName1.setId(null);
        assertThat(variableName1).isNotEqualTo(variableName2);
    }
}
