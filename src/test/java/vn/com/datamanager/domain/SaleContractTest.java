package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class SaleContractTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleContract.class);
        SaleContract saleContract1 = new SaleContract();
        saleContract1.setId(1L);
        SaleContract saleContract2 = new SaleContract();
        saleContract2.setId(saleContract1.getId());
        assertThat(saleContract1).isEqualTo(saleContract2);
        saleContract2.setId(2L);
        assertThat(saleContract1).isNotEqualTo(saleContract2);
        saleContract1.setId(null);
        assertThat(saleContract1).isNotEqualTo(saleContract2);
    }
}
