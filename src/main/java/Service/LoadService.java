package Service;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;

/**
 * Is called from the Load handler and calls the load method
 */
public class LoadService {
    /**
     * Handles the load API call
     * @param r
     * @return LoadResult
     */
    public LoadResult load(LoadRequest r) {
        // Create database connection
        Database database = new Database();
        try {
            database.openConnection();
            UserDao userDao = new UserDao(database.getConnection());
            PersonDao personDao = new PersonDao(database.getConnection());
            EventDao eventDao = new EventDao(database.getConnection());
            // Clear all data from the database
            userDao.clear();
            personDao.clear();
            eventDao.clear();
            // Load all the data into the database
            int numUsers = 0;
            int numPersons = 0;
            int numEvents = 0;
            // users
            for (User currentUser : r.getUsers()) {
                userDao.insert(currentUser);
                ++numUsers;
            }
            // persons
            for (Person currentPerson : r.getPersons()) {
                personDao.Insert(currentPerson);
                ++numPersons;
            }
            // events
            for (Event currentEvent : r.getEvents()) {
                eventDao.insert(currentEvent);
                ++numEvents;
            }
            // Construct and return a response
            String message = "Successfully added " + numUsers + " users, " + numPersons + " persons, and " + numEvents + " events to the database.";
            LoadResult response = new LoadResult(message, true);
            database.closeConnection(true);
            return response;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
