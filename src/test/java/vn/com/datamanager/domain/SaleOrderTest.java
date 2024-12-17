package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class SaleOrderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SaleOrder.class);
        SaleOrder saleOrder1 = new SaleOrder();
        saleOrder1.setId(1L);
        SaleOrder saleOrder2 = new SaleOrder();
        saleOrder2.setId(saleOrder1.getId());
        assertThat(saleOrder1).isEqualTo(saleOrder2);
        saleOrder2.setId(2L);
        assertThat(saleOrder1).isNotEqualTo(saleOrder2);
        saleOrder1.setId(null);
        assertThat(saleOrder1).isNotEqualTo(saleOrder2);
    }
}
