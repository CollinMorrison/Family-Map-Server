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
    private float latitude;
    /**
     * longitude of the event
     */
    private float longitude;
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
    private int year;
    /**
     * Whether the request was successful
     */
    private boolean success;

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
    public EventIDResult(String associatedUsername, String eventID, String personID, float latitude, float longitude, String country, String city, String eventType, int year, boolean success) {

    }
}
