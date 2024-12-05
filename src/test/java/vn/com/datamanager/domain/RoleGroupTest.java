package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class RoleGroupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleGroup.class);
        RoleGroup roleGroup1 = new RoleGroup();
        roleGroup1.setId(1L);
        RoleGroup roleGroup2 = new RoleGroup();
        roleGroup2.setId(roleGroup1.getId());
        assertThat(roleGroup1).isEqualTo(roleGroup2);
        roleGroup2.setId(2L);
        assertThat(roleGroup1).isNotEqualTo(roleGroup2);
        roleGroup1.setId(null);
        assertThat(roleGroup1).isNotEqualTo(roleGroup2);
    }
}
