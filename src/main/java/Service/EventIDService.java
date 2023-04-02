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
                return null;
            }
            String username = authTokenObject.getUsername();
            UserDao userDao = new UserDao(database.getConnection());
            User user = userDao.find(username);
            if (user == null) {
                return null;
            }
            // Get the event with the eventID
            EventDao eventDao = new EventDao(database.getConnection());
            Event event = eventDao.find(eventID);
            // Make sure the event is associated with the right user
            if (!event.getAssociatedUsername().equals(user.getUsername())) {
                return null;
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
