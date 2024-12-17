package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class ConfigLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConfigLog.class);
        ConfigLog configLog1 = new ConfigLog();
        configLog1.setId(1L);
        ConfigLog configLog2 = new ConfigLog();
        configLog2.setId(configLog1.getId());
        assertThat(configLog1).isEqualTo(configLog2);
        configLog2.setId(2L);
        assertThat(configLog1).isNotEqualTo(configLog2);
        configLog1.setId(null);
        assertThat(configLog1).isNotEqualTo(configLog2);
    }
}
