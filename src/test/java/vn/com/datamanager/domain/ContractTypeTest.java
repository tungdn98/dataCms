package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class ContractTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractType.class);
        ContractType contractType1 = new ContractType();
        contractType1.setId(1L);
        ContractType contractType2 = new ContractType();
        contractType2.setId(contractType1.getId());
        assertThat(contractType1).isEqualTo(contractType2);
        contractType2.setId(2L);
        assertThat(contractType1).isNotEqualTo(contractType2);
        contractType1.setId(null);
        assertThat(contractType1).isNotEqualTo(contractType2);
    }
}
