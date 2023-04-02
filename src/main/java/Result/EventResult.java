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

    /**
     * Constructor for the Event Result
     * @param data
     * @param success
     */
    public EventResult(Event[] data, boolean success) {
        this.data = data;
        this.success = success;
    }
}
