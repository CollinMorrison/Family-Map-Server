package DataAccess;

/**
 * Describes a DataAccessException, used for errors in accessing the database
 */
public class DataAccessException extends Exception {
    /**
     * Constructor, describes the message within the error
     * @param message
     */
    DataAccessException(String message) {
        super(message);
    }
}
