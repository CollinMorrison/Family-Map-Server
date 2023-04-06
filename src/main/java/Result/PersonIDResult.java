package Result;

import Request.PersonIDRequest;

/**
 * Contains the person requested in the request
 */
public class PersonIDResult {
    /**
     * username associated with the person
     */
    private String associatedUsername;
    /**
     * ID associated with the person
     */
    private String personID;
    /**
     * First name of the person
     */
    private String firstName;
    /**
     * last name of the person
     */
    private String lastName;
    /**
     * gender of the person
     */
    private String gender;
    /**
     * The person's father's ID
     */
    private String fatherID;
    /**
     * The person's mother's ID
     */
    private String motherID;
    /**
     * The person's spouse's ID
     */
    private String spouseID;
    /**
     * Whether the request was successful
     */
    private boolean success;
    private String message;

    /**
     * Constructor for the PersonID Response
     * @param associatedUsername
     * @param personID
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     * @param success
     */
    public PersonIDResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, boolean success, String message) {
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
