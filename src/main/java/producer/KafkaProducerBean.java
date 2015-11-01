package producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaProducerBean {
    private KafkaProducer producer;

    @Autowired
    private ApplicationConfiguration appConfig;

    @PostConstruct
    public void configure() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, appConfig.getBrokerList());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.CLIENT_ID_CONFIG, appConfig.getAppName());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, "1");

        producer = new KafkaProducer<>(props);
    }

    public KafkaProducer getProducer() {
        return producer;
    }
}




