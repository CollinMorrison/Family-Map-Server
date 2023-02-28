package Result;

/**
 * Stores all the data received from a fill request
 */
public class FillResult {
    /**
     * message received as a result of the request
     */
    private String message;
    /**
     * Whether the request was successful
     */
    private boolean success;

    /**
     * Constructor for the fill result
     * @param message
     * @param success
     */
    public FillResult(String message, boolean success) {

    }
}
