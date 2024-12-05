package vn.com.datamanager.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Data Cms.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private boolean initSampleData;

    public boolean isInitSampleData() {
        return initSampleData;
    }

    public void setInitSampleData(boolean initSampleData) {
        this.initSampleData = initSampleData;
    }
}
