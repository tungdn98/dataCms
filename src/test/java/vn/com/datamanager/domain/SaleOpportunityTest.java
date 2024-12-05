package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class SaleOpportunityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleOpportunity.class);
        SaleOpportunity saleOpportunity1 = new SaleOpportunity();
        saleOpportunity1.setId(1L);
        SaleOpportunity saleOpportunity2 = new SaleOpportunity();
        saleOpportunity2.setId(saleOpportunity1.getId());
        assertThat(saleOpportunity1).isEqualTo(saleOpportunity2);
        saleOpportunity2.setId(2L);
        assertThat(saleOpportunity1).isNotEqualTo(saleOpportunity2);
        saleOpportunity1.setId(null);
        assertThat(saleOpportunity1).isNotEqualTo(saleOpportunity2);
    }
}
