package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Result.PersonResult;

/**
 * Is called from the Person handler and calls the person method
 */
public class PersonService {
    /**
     * Handles the person API call
     * @return PersonResult
     */
    public PersonResult person(String authToken) {
        // Create the database connection
        Database database = new Database();
        try {
            database.openConnection();
            // Make sure the authToken is valid
            AuthTokenDao authTokenDao = new AuthTokenDao(database.getConnection());
            AuthToken authTokenObject = authTokenDao.FindByAuthtoken(authToken);
            if (authTokenObject == null) {
                throw new Exception("Invalid AuthToken");
            }
            String username = authTokenObject.getUsername();
            UserDao userDao = new UserDao(database.getConnection());
            User user = userDao.find(username);
            if (user == null) {
                throw new Exception("Invalid AuthToken");
            }
            // Get the list of people associated with the valid user
            PersonDao personDao = new PersonDao(database.getConnection());
            Person[] persons = personDao.GetAllPersons(user.getUsername()).toArray(new Person[0]);
            // Construct the response
            PersonResult response = new PersonResult(persons, true, null);
            database.closeConnection(true);
            return response;
        } catch(Exception e) {
            database.closeConnection(false);
            e.printStackTrace();
            return new PersonResult(null, false, "Error: " + e.getMessage());
        }
    }
}
