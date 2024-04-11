package Result;

import Model.Person;

/**
 * Response containing all family members of the current user
 */
public class PersonResult {
    /**
     * The array of family members
     */
    private Person[] data;
    /**
     * Whether the request was successful
     */
    private boolean success;

    private String message;

    /**
     * Constructor for the Person Response
     * @param data
     * @param success
     */
    public PersonResult(Person[] data, boolean success, String message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Person[] getData() {
        return data;
    }
}
