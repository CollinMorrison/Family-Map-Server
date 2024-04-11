package Model;

import java.util.Objects;

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
    public AuthToken(String authToken, String username) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthToken newAuthToken = (AuthToken) o;
        return Objects.equals(authToken, newAuthToken.authToken) && Objects.equals(username, newAuthToken.username);
    }
}
