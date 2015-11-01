package producer;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import producer.models.ReservationDetails;

import java.nio.ByteBuffer;
import java.util.Random;

@RestController
public class ReservationDetailsController {
    private final ObjectMapper JSON = new ObjectMapper();


    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    @Autowired
    private KafkaProducerBean producer;

    @RequestMapping(value = "/reservation_details", method = RequestMethod.POST)
    public String sendEvent(@RequestBody(required = false) ReservationDetails reservationDetails) {
        String result = sendToKafka(reservationDetails);
        return result;
    }

    private String sendToKafka(ReservationDetails reservationDetails) {
        reservationDetails = getReservationDetails(reservationDetails);
        try {
            String topic = applicationConfiguration.getTopic();
            System.out.println("sending");
            producer.getProducer().send(new ProducerRecord<String, String>(topic, JSON.writeValueAsString(reservationDetails)));
            System.out.println("sent");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "success";
    }


    private ReservationDetails getReservationDetails(ReservationDetails reservationDetails) {
        if (reservationDetails == null) {
            reservationDetails = new ReservationDetails();
            Random random = new Random();
            reservationDetails.setUuid(String.valueOf(random.nextInt(1000)));
        }
        reservationDetails.setEvent_created(DateTime.now().toString());
        reservationDetails.setEventType(EventTypes.RESERVATION_DETAILS);
        return reservationDetails;
    }

    private String now() {
        return DateTime.now().toString().split("T")[1];
    }
}
