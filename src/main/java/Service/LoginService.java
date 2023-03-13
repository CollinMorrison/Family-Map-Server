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
            //create the userDao and authTokenDao to interact with the database
            UserDao userDao = new UserDao(database.getConnection());
            AuthTokenDao authTokenDao = new AuthTokenDao(database.getConnection());
            //get the user associated with the username
            User user = userDao.find(r.getUsername());
            //Get the auth token associated with the username
            AuthToken authToken = authTokenDao.Find(r.getUsername());
            //Create the login result
            String username = user.getUsername();
            String personID = user.getPersonID();
            //TODO: make the success value accurate
            boolean success = false;
            //TODO: implement this functionality too
            String message = "successful";
            return new LoginResult(authToken.getAuthToken(), username, personID, success, message);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean authTokenIsValid (String username) {
        boolean isValid = false;
        try {
            Database database = new Database();
            AuthTokenDao authTokenDao = new AuthTokenDao(database.getConnection());
            //if the auth token is valid
            if (authTokenDao.Find(username) != null) {
                isValid = true;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return isValid;
    }
}
