package Service;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.User;
import Result.EventIDResult;

/**
 * Is called from the EventID handler and calls the eventID method
 */
public class EventIDService {
    /**
     * Handled the EventID API call
     * @return EventIDResult
     */
    public EventIDResult eventID(String authToken, String eventID) {
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
            // Get the event with the eventID
            EventDao eventDao = new EventDao(database.getConnection());
            Event event = eventDao.find(eventID);
            if (event == null) {
                throw new Exception("Invalid eventID");
            }
            // Make sure the event is associated with the right user
            if (!event.getAssociatedUsername().equals(user.getUsername())) {
                throw new Exception("Requested event does not belong to this user");
            }
            // Construct the response
            EventIDResult response = new EventIDResult(
                    event.getAssociatedUsername(),
                    event.getEventID(),
                    event.getPersonID(),
                    event.getLatitude(),
                    event.getLongitude(),
                    event.getCountry(),
                    event.getCity(),
                    event.getEventType(),
                    event.getYear(),
                    true,
                    null
            );
            database.closeConnection(true);
            return response;
        } catch (Exception e) {
            database.closeConnection(false);
            e.printStackTrace();
            return new EventIDResult(
                    null,
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
