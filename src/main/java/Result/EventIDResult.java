package Result;

/**
 * Contains the event with the ID from the request
 */
public class EventIDResult {
    /**
     * username associated with the person
     */
    private String associatedUsername;
    /**
     * event ID associated with the event
     */
    private String eventID;
    /**
     * ID associated with the person
     */
    private String personID;
    /**
     * Latitude of the event
     */
    private Float latitude;
    /**
     * longitude of the event
     */
    private Float longitude;
    /**
     * country in which the event took place
     */
    private String country;
    /**
     * city in which the event took place
     */
    private String city;
    /**
     * Type of event
     */
    private String eventType;
    /**
     * Year the event took place
     */
    private Integer year;
    /**
     * Whether the request was successful
     */
    private boolean success;

    private String message;

    /**
     * Constructor for the EventID response
     * @param associatedUsername
     * @param eventID
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     * @param success
     */
    public EventIDResult(String associatedUsername, String eventID, String personID, Float latitude, Float longitude, String country, String city, String eventType, Integer year, boolean success, String message) {
        this.associatedUsername = associatedUsername;
        this.eventID = eventID;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
