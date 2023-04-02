package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Result.PersonIDResult;

import javax.xml.crypto.Data;
import java.util.Objects;

/**
 * Is called from the PersonID handler and calls the personID method
 */
public class PersonIDService {
    /**
     * Handles the personID API call
     * @return PersonIDResult
     */
    public PersonIDResult personID(String authToken, String personID) {
        // Create the database connection
        Database database = new Database();
        try {
            database.openConnection();
            // Make sure the authToken is valid
            AuthTokenDao authTokenDao = new AuthTokenDao(database.getConnection());
            AuthToken authTokenObject = authTokenDao.FindByAuthtoken(authToken);
            if (authTokenObject == null) {
                return null;
            }
            String username = authTokenObject.getUsername();
            UserDao userDao = new UserDao(database.getConnection());
            User user = userDao.find(username);
            if (user == null) {
                return null;
            }
            // Get the person with the personID
            PersonDao personDao = new PersonDao(database.getConnection());
            Person person = personDao.Find(personID);
            // Make sure the person is associated with the right user
            if (!person.getAssociatedUsername().equals(user.getUsername())) {
                return null;
            }
            // Construct the response
            PersonIDResult response = new PersonIDResult(
                    person.getAssociatedUsername(),
                    person.getPersonID(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getGender(),
                    person.getFatherID(),
                    person.getMotherID(),
                    person.getSpouseID(),
                    true
            );
            database.closeConnection(true);
            return response;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
