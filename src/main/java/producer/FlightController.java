package producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import producer.models.Flight;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FlightController {
    private final ObjectMapper JSON = new ObjectMapper();

    @RequestMapping(value = "/flight", method = RequestMethod.GET)
    public String sendEvent(@RequestParam(required = true) String id) throws JsonProcessingException {
        return JSON.writeValueAsString(FlightResults.getFlights(id));
    }


}
