package DataAccess;


import Model.Person;
import Model.User;

import java.sql.Connection;
import java.util.List;

/**
 * Handles all database communication associated with a Person object
 */
public class PersonDao {

    /**
     * The database connection
     */
    private final Connection conn;

    /**
     * Constructor for the PersonDao
     * @param conn
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * inserts a new person into the database
     * @param person
     * @throws DataAccessException
     */
    public void Insert(Person person) throws DataAccessException {}

    /**
     * Returns a person from the database by personID
     * @param personID
     * @return
     * @throws DataAccessException
     */
    public Person Find(String personID) throws DataAccessException {
        return null;
    }

    /**
     * Deletes a person from the database by personID
     * @param personID
     * @throws DataAccessException
     */
    public void delete (String personID) throws DataAccessException {}

    /**
     * Returns a list of all the persons in the database
     * @return List of persons
     */
    public List<Person> GetAllPersons() {
        return null;
    }
}
