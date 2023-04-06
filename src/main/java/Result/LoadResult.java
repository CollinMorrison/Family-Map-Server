package Result;

/**
 * Contains the information that is the result of a load request
 */
public class LoadResult {
    /**
     * Message that is contained in the response
     */
    private String message;
    /**
     * Whether the request was successful
     */
    private Boolean success;

    /**
     * Constructor for the Load result
     * @param message
     * @param success
     */
    public LoadResult(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }
}
