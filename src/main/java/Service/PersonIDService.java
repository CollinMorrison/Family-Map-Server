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
                throw new Exception("Invalid AuthToken");
            }
            String username = authTokenObject.getUsername();
            UserDao userDao = new UserDao(database.getConnection());
            User user = userDao.find(username);
            if (user == null) {
                throw new Exception("Invalid AuthToken");
            }
            // Get the person with the personID
            PersonDao personDao = new PersonDao(database.getConnection());
            Person person = personDao.Find(personID);
            if (person == null) {
                throw new Exception("Invalid personID");
            }
            // Make sure the person is associated with the right user
            if (!person.getAssociatedUsername().equals(user.getUsername())) {
                throw new Exception("The person is not associated with this user");
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
                    true,
                    null
            );
            database.closeConnection(true);
            return response;
        } catch (Exception e) {
            database.closeConnection(false);
            e.printStackTrace();
            return new PersonIDResult(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    false,
                    "Error: " + e.getMessage()
            );
        }
    }
}
