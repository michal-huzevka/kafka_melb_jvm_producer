package producer.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.apache.kafka.clients.producer.ProducerRecord;
import producer.FlightResults;
import producer.KafkaProducerBean;
import producer.StartupHousekeeper;
import producer.models.Flight;
import producer.models.FlightSearch;

import java.io.IOException;
import java.util.Random;

public class Consumer implements Runnable {
    private KafkaStream m_stream;
    private String appName;
    private int m_threadNumber;
    private final ObjectMapper JSON = new ObjectMapper();

    public Consumer(KafkaStream a_stream, int a_threadNumber, String appName) {
        this.m_threadNumber = a_threadNumber;
        this.m_stream = a_stream;
        this.appName = appName;
    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        while (it.hasNext()) {
            byte[] message = it.next().message();
            try {
                Flight flight= JSON.readValue(message, Flight.class);
                FlightResults.putFlight(flight);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("Shutting down Thread: " + m_threadNumber);
    }

}
