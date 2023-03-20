package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

import java.util.UUID;

/**
 * Is called from the Register handler and calls the register method
 */
public class RegisterService {
    /**
     * handles the register functionality
     * @param r
     * @return RegisterResult
     */
    public RegisterResult register(RegisterRequest r) {
        // Create the database connection
        Database database = new Database();
        try {
            // Create the userDao and authTokenDao to interact with the database
            UserDao userDao = new UserDao(database.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(database.getConnection());
            // Get personID for new user
            UUID uuid = UUID.randomUUID();
            String personID = uuid.toString();
            // Create a new user
            User newUser = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender(), personID);
            // Generate 4 generations of ancestor data for the new user
            // (just like the /fill endpoint if called with a generations value of 4
            // and this new user's username as parameters)
            // Logs the user in
            // Returns an authToken
            return null;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
