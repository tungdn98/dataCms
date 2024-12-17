package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class EmployeeLeadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeLead.class);
        EmployeeLead employeeLead1 = new EmployeeLead();
        employeeLead1.setId(1L);
        EmployeeLead employeeLead2 = new EmployeeLead();
        employeeLead2.setId(employeeLead1.getId());
        assertThat(employeeLead1).isEqualTo(employeeLead2);
        employeeLead2.setId(2L);
        assertThat(employeeLead1).isNotEqualTo(employeeLead2);
        employeeLead1.setId(null);
        assertThat(employeeLead1).isNotEqualTo(employeeLead2);
    }
}
