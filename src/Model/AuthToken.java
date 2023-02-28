package Model;

/**
 * Template for AuthToken entities as they appear in the database
 */
public class AuthToken {
    /**
     * Unique authToken
     */
    private String authToken;
    /**
     * Username that is associated with the authtoken
     */
    private String username;

    /**
     * Constructor for AuthToken, creates a new AuthToken entity with the provided attributes
     * @param authToken
     * @param username
     */
    AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * overrides the equals method for an object in Java
     * @param o
     * @return bool
     */
    @Override
    public boolean equals(Object o) {
        return false;
    }
}
