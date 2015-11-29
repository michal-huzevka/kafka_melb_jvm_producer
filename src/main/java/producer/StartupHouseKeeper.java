package producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import producer.kafka.ConsumerGroup;
import producer.models.Flight;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class StartupHousekeeper implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ApplicationConfiguration appConfig;
    private boolean started = false;


    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (started) return;
        started = true;

        try {
            startKafkaConsumer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void startKafkaConsumer() throws Exception {
        String zooKeeper = appConfig.getZooKeeper();
        String groupId = appConfig.getAppName();
        String topic = "flight_results";
        int threads = 4;

        ConsumerGroup example = new ConsumerGroup(zooKeeper, groupId, topic);
        example.run(threads);

    }

}