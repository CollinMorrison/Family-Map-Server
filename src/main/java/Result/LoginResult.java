package Result;

/**
 * Stores all information received from a login request
 */
public class LoginResult {
    /**
     * The auth token associated with the user logging in
     */
    private String authtoken;
    /**
     * The username used to log in
     */
    private String username;
    /**
     * The personID associated with the person logging in
     */
    private String personID;

    public boolean isSuccess() {
        return success;
    }

    /**
     * Whether the request was successful
     */
    private boolean success;

    private String message;

    /**
     * Constructor for a Login Result
     * @param authtoken
     * @param username
     * @param personID
     * @param success
     */
    public LoginResult(String authtoken, String username, String personID, boolean success, String message) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personID;
        this.success = success;
        this.message = message;
    }
}
