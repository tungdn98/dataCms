package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class FinalcialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Finalcial.class);
        Finalcial finalcial1 = new Finalcial();
        finalcial1.setId(1L);
        Finalcial finalcial2 = new Finalcial();
        finalcial2.setId(finalcial1.getId());
        assertThat(finalcial1).isEqualTo(finalcial2);
        finalcial2.setId(2L);
        assertThat(finalcial1).isNotEqualTo(finalcial2);
        finalcial1.setId(null);
        assertThat(finalcial1).isNotEqualTo(finalcial2);
    }
}
