package Request;

/**
 * Stores everything needed to make a request to register a user
 */
public class RegisterRequest {
    /**
     * user's username
     */
    private String username;
    /**
     * user's password
     */
    private String password;
    /**
     * user's email
     */
    private String email;
    /**
     * user's first name
     */
    private String firstName;
    /**
     * user's last name
     */
    private String lastName;
    /**
     * user's gender
     */
    private String gender;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    /**
     * Constructor for the Register Request
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     */
    public RegisterRequest(String username, String password, String email, String firstName, String lastName, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }
}
