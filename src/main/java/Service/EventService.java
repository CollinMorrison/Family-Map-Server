package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.User;
import Result.EventResult;


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
                throw new Exception("Invalid AuthToken");
            }
            String username = authTokenObject.getUsername();
            UserDao userDao = new UserDao(database.getConnection());
            User user = userDao.find(username);
            if (user == null) {
                throw new Exception("Invalid AuthToken");
            }
            // Get the list of events associated with the valid user
            EventDao eventDao = new EventDao(database.getConnection());
            Event[] events = eventDao.FindByUser(user).toArray(new Event[0]);
            // Construct the response
            EventResult response = new EventResult(events, true, null);
            database.closeConnection(true);
            return response;
        } catch(Exception e) {
            database.closeConnection(false);
            e.printStackTrace();
            return new EventResult(
                    null,
                    false,
                    "Error: " + e.getMessage()
            );
        }
    }
}
