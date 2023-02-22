package Result;

import Request.ClearRequest;

/**
 * Stores all information received from a clear request
 */
public class ClearResult {
    /**
     * message associated with the clear request
     */
    private String message;
    /**
     * Whether the request was successful
     */
    private boolean success;

    /**
     * Constructor for the Clear Result
     * @param message
     * @param success
     */
    public ClearResult(String message, boolean success) {

    }
}
