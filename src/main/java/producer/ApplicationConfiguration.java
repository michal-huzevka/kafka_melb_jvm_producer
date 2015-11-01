package producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by michalhuzevka on 17/07/15.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Value("${app.name}")
    private String appName;

    @Value("${app.topic}")
    private String topic;

    @Value("${app.brokerList}")
    private String brokerList;

    public String getAppName() {
        return appName;
    }


    public String getTopic() {
        return topic;
    }

    public String getBrokerList() {
        return brokerList;
    }
}
