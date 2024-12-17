package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class PaymentStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentStatus.class);
        PaymentStatus paymentStatus1 = new PaymentStatus();
        paymentStatus1.setId(1L);
        PaymentStatus paymentStatus2 = new PaymentStatus();
        paymentStatus2.setId(paymentStatus1.getId());
        assertThat(paymentStatus1).isEqualTo(paymentStatus2);
        paymentStatus2.setId(2L);
        assertThat(paymentStatus1).isNotEqualTo(paymentStatus2);
        paymentStatus1.setId(null);
        assertThat(paymentStatus1).isNotEqualTo(paymentStatus2);
    }
}
