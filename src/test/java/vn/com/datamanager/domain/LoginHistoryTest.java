package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class LoginHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoginHistory.class);
        LoginHistory loginHistory1 = new LoginHistory();
        loginHistory1.setId(1L);
        LoginHistory loginHistory2 = new LoginHistory();
        loginHistory2.setId(loginHistory1.getId());
        assertThat(loginHistory1).isEqualTo(loginHistory2);
        loginHistory2.setId(2L);
        assertThat(loginHistory1).isNotEqualTo(loginHistory2);
        loginHistory1.setId(null);
        assertThat(loginHistory1).isNotEqualTo(loginHistory2);
    }
}
