package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.GenerateGenerations;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

import java.util.List;
import java.util.Random;
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
            database.openConnection();
            // Create the userDao and authTokenDao to interact with the database
            UserDao userDao = new UserDao(database.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(database.getConnection());
            // Get personID for new user
            UUID uuid = UUID.randomUUID();
            String personID = uuid.toString();
            // Create a new user
            User newUser = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(), r.getLastName(), r.getGender(), personID);
            // Generate 4 generations of ancestor data for the new user
            //generate a number between 500 and 2000
            int referenceYear = new Random().nextInt(1500) + 500;
            GenerateGenerations generateGenerations = new GenerateGenerations(database.getConnection());
            // (just like the /fill endpoint if called with a generations value of 4
            // and this new user's username as parameters)
            generateGenerations.generatePerson(r.getGender(), 4, r.getUsername(), r.getFirstName(), r.getLastName(), referenceYear, personID);
            // Logs the user in, construct the register result
            UUID authUuid = UUID.randomUUID();
            AuthToken newAuthToken = new AuthToken(authUuid.toString(), newUser.getUsername());
            RegisterResult registerResult = new RegisterResult(
                    newAuthToken.getAuthToken(),
                    newUser.getUsername(),
                    newUser.getPersonID(),
                    true,
                    "Successfully registered new User"
            );
            // Save the all applicable data to the database
            authTokenDao.insert(newAuthToken);
            userDao.insert(newUser);
            User verifyUser = userDao.find(newUser.getUsername());
            database.closeConnection(true);
            // Returns the response
            return registerResult;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
