package Result;

import Request.RegisterRequest;

/**
 * Stores all information received from a Register Request
 */
public class RegisterResult {
    /**
     * Auth token associated with the user
     */
    private String authtoken;
    /**
     * username associated with the user
     */
    private String username;
    /**
     * personID associated with the user
     */
    private String personID;
    /**
     * whether the request was successful
     */
    private boolean success;
    /**
     * The message associated with the response
     */
    private String message;

    /**
     * Constructor for the Register Result
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     */
    public RegisterResult(String authtoken, String username, String personID, boolean success, String message) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;
    }
}
