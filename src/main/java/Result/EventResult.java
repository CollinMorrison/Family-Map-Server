package Result;

import Model.Event;

/**
 * Contains all events from the event request
 */
public class EventResult {
    /**
     * The list of events received as a result of the request
     */
    private Event[] data;
    /**
     * Whether the request was successful
     */
    private boolean success;

    private String message;

    /**
     * Constructor for the Event Result
     * @param data
     * @param success
     */
    public EventResult(Event[] data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess () {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
