package Service;

import DataAccess.*;
import Result.ClearResult;

/**
 * Is called from the Clear handler and calls the clear method
 */
public class ClearService {
    /**
     * handles the clear api call
     * @return ClearResult
     */
    public ClearResult clear() {
        // Create the database connection
        Database database = new Database();
        try {
            database.openConnection();
            // Initialize all Dao objects
            AuthTokenDao authTokenDao = new AuthTokenDao(database.getConnection());
            EventDao eventDao = new EventDao(database.getConnection());
            PersonDao personDao = new PersonDao(database.getConnection());
            UserDao userDao = new UserDao(database.getConnection());
            // Perform clear operation
            authTokenDao.clear();
            eventDao.clear();
            personDao.clear();
            userDao.clear();
            // Construct and return clear response
            ClearResult clearResult = new ClearResult(
                    "Clear Succeeded",
                    true
            );
            // Close database connection
            database.closeConnection(true);
            // Return clear result
            return clearResult;
        } catch (DataAccessException e) {
            database.closeConnection(false);
            e.printStackTrace();
            return new ClearResult("Error: Internal Server Error", false);
        }
    }
}
