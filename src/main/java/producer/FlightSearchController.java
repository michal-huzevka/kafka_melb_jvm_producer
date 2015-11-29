package producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.DateTimeSerializerBase;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import producer.models.FlightSearch;

import java.util.UUID;

@RestController
public class FlightSearchController {
    private final ObjectMapper JSON = new ObjectMapper();


    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    @Autowired
    private KafkaProducerBean producer;


    @RequestMapping(value = "/flight_search", method = RequestMethod.POST)
    public String sendEvent(@RequestBody(required = true) FlightSearch flightSearch) {
        flightSearch.setId(Long.toString(System.currentTimeMillis()));
        sendToKafka(flightSearch);
        return flightSearch.getId();
    }

    private void sendToKafka(FlightSearch flightSearch) {
        try {
            String topic = applicationConfiguration.getTopic();
            producer.getProducer().send(new ProducerRecord<String, String>(topic, JSON.writeValueAsString(flightSearch)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
