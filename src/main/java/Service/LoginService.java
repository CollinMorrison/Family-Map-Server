package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.User;
import Request.LoginRequest;
import Result.LoginResult;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Is called from the Login handler and calls the login method
 */
public class LoginService {
    /**
     * handles the login functionality
     * @param r
     * @return LoginResult
     */
    public LoginResult login(LoginRequest r) {
        //create the database connection
        Database database = new Database();
        try {
            database.openConnection();
            //create the userDao and authTokenDao to interact with the database
            UserDao userDao = new UserDao(database.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(database.getConnection());
            //Get the auth token associated with the username
            AuthToken authToken = userDao.Validate(r.getUsername(), r.getPassword());
            if (authToken == null) {
                throw new Exception("Invalid username or password");
            }
            //get the user associated with the username
            User user = userDao.find(r.getUsername());
            //Create the login result
            String username = user.getUsername();
            String personID = user.getPersonID();
            database.closeConnection(true);
            return new LoginResult(authToken.getAuthToken(), username, personID, true, null);
        } catch (Exception e) {
            database.closeConnection(false);
            e.printStackTrace();
            return new LoginResult(
                    null,
                    null,
                    null,
                    false,
                    "Error: " + e.getMessage()
            );
        }
    }

}
