package producer;

import producer.models.Flight;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by michalhuzevka on 29/11/2015.
 */
public class FlightResults {
    public static ConcurrentHashMap<String, ConcurrentLinkedQueue<Flight>> hashMap = new ConcurrentHashMap<>();
    public static ArrayList<Flight> getFlights(String id) {
        if (hashMap.containsKey(id) == false) {
            return new ArrayList<Flight>();
        }
        ConcurrentLinkedQueue<Flight> flights = hashMap.get(id);
        ArrayList<Flight> returnedFlights = new ArrayList<Flight>();
        while (!flights.isEmpty()) {
            returnedFlights.add(flights.remove());
        }
        return returnedFlights;
    }

    public static void putFlight(Flight flight) {
        System.out.println("putting flight: " + flight.getId());
        String id = flight.getId();
        if (hashMap.containsKey(id) == false) {
            hashMap.put(id, new ConcurrentLinkedQueue<>());
        }
        hashMap.get(id).add(flight);
    }
}
