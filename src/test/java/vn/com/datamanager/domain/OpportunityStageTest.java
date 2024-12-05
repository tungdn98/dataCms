package vn.com.datamanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import vn.com.datamanager.web.rest.TestUtil;

class OpportunityStageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpportunityStage.class);
        OpportunityStage opportunityStage1 = new OpportunityStage();
        opportunityStage1.setId(1L);
        OpportunityStage opportunityStage2 = new OpportunityStage();
        opportunityStage2.setId(opportunityStage1.getId());
        assertThat(opportunityStage1).isEqualTo(opportunityStage2);
        opportunityStage2.setId(2L);
        assertThat(opportunityStage1).isNotEqualTo(opportunityStage2);
        opportunityStage1.setId(null);
        assertThat(opportunityStage1).isNotEqualTo(opportunityStage2);
    }
}
