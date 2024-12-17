package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class PaymentTermTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentTerm.class);
        PaymentTerm paymentTerm1 = new PaymentTerm();
        paymentTerm1.setId(1L);
        PaymentTerm paymentTerm2 = new PaymentTerm();
        paymentTerm2.setId(paymentTerm1.getId());
        assertThat(paymentTerm1).isEqualTo(paymentTerm2);
        paymentTerm2.setId(2L);
        assertThat(paymentTerm1).isNotEqualTo(paymentTerm2);
        paymentTerm1.setId(null);
        assertThat(paymentTerm1).isNotEqualTo(paymentTerm2);
    }
}
