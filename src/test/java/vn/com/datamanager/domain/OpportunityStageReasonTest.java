package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class OpportunityStageReasonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpportunityStageReason.class);
        OpportunityStageReason opportunityStageReason1 = new OpportunityStageReason();
        opportunityStageReason1.setId(1L);
        OpportunityStageReason opportunityStageReason2 = new OpportunityStageReason();
        opportunityStageReason2.setId(opportunityStageReason1.getId());
        assertThat(opportunityStageReason1).isEqualTo(opportunityStageReason2);
        opportunityStageReason2.setId(2L);
        assertThat(opportunityStageReason1).isNotEqualTo(opportunityStageReason2);
        opportunityStageReason1.setId(null);
        assertThat(opportunityStageReason1).isNotEqualTo(opportunityStageReason2);
    }
}
