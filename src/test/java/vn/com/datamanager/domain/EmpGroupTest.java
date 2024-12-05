package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class EmpGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpGroup.class);
        EmpGroup empGroup1 = new EmpGroup();
        empGroup1.setId(1L);
        EmpGroup empGroup2 = new EmpGroup();
        empGroup2.setId(empGroup1.getId());
        assertThat(empGroup1).isEqualTo(empGroup2);
        empGroup2.setId(2L);
        assertThat(empGroup1).isNotEqualTo(empGroup2);
        empGroup1.setId(null);
        assertThat(empGroup1).isNotEqualTo(empGroup2);
    }
}
