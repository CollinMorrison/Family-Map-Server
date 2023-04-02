package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.User;
import Result.EventResult;

import javax.xml.crypto.Data;

/**
 * Is called from the Event handler and calls the event method
 */
public class EventService {
    /**
     * Handles the event API call
     * @return EventResult
     */
    public EventResult event(String authToken) {
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
            // Get the list of events associated with the valid user
            EventDao eventDao = new EventDao(database.getConnection());
            Event[] events = eventDao.FindByUser(user).toArray(new Event[0]);
            // Construct the response
            EventResult response = new EventResult(events, true);
            database.closeConnection(true);
            return response;
        } catch(DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
