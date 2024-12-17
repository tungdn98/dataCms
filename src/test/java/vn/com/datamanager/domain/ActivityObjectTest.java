package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class ActivityObjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActivityObject.class);
        ActivityObject activityObject1 = new ActivityObject();
        activityObject1.setId(1L);
        ActivityObject activityObject2 = new ActivityObject();
        activityObject2.setId(activityObject1.getId());
        assertThat(activityObject1).isEqualTo(activityObject2);
        activityObject2.setId(2L);
        assertThat(activityObject1).isNotEqualTo(activityObject2);
        activityObject1.setId(null);
        assertThat(activityObject1).isNotEqualTo(activityObject2);
    }
}
