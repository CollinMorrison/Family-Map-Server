package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.Person;
import Result.PersonResult;

/**
 * Is called from the Person handler and calls the person method
 */
public class PersonService {
    /**
     * Handles the person API call
     * @return PersonResult
     */
    public PersonResult person() {
        // Create the database connection
        Database database = new Database();
        try {
            database.openConnection();
            PersonDao personDao = new PersonDao(database.getConnection());
            Person[] persons = personDao.GetAllPersons().toArray(new Person[0]);
            // Construct the response
            PersonResult response = new PersonResult(persons, true);
            database.closeConnection(true);
            return response;
        } catch(DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
