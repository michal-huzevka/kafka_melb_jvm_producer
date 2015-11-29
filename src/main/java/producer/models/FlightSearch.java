package producer.models;

public class FlightSearch {

    private String id;
    private String from;
    private String to;
    private String date;

    public FlightSearch() {

    }

    public FlightSearch(String id, String from, String to, String date) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
