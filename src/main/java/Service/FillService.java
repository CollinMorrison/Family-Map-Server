package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Model.GenerateGenerations;
import Model.Person;
import Model.User;
import Request.FillRequest;
import Result.FillResult;

import java.util.List;
import java.util.Random;

/**
* Is called from the Fill handler and calls the fill method
*/
public class FillService {
    /**
     * Handles the fill API call
     * @return FillResult
     */
    public FillResult fill(int generations, String username) {
        if (generations < 0) {
            return new FillResult(
                    "Error: Invalid generations value.",
                    false
            );
        }
        // Create the database connection
        Database database = new Database();
        try {
            database.openConnection();
            UserDao userDao = new UserDao(database.getConnection());
            PersonDao personDao = new PersonDao(database.getConnection());
            GenerateGenerations generateGenerations = new GenerateGenerations(database.getConnection());
            // Get user associated with the username passed in
            User user = userDao.find(username);
            if (user == null) {
                database.closeConnection(false);
                return new FillResult(
                        "Error: Invalid username",
                        false
                );
            }
            // Check if there is data already associated with that user. If there is, delete it
            List<Person> associatedPersons = personDao.GetAllPersons(username);
            if (associatedPersons != null) {
                for (Person currentPerson : associatedPersons) {
                    personDao.delete(currentPerson.getPersonID());
                }
            }
            // Generate a number between 500 and 2000
            int referenceYear = new Random().nextInt(1500) + 500;
            // Generate new information for the user
            if (generations == 0) {
                generations = 4;
            }
            generateGenerations.generatePerson(
                    user.getGender(),
                    generations,
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    referenceYear,
                    user.getPersonID(),
                    generations
            );
            // Send back a response, including the number of people and number of events added to the database
            String responseMessage = "Successfully added "
                    + generateGenerations.getNumPeople()
                    + " persons and "
                    + generateGenerations.getNumEvents()
                    + " events to the database.";
            FillResult response = new FillResult(
                    responseMessage,
                    true
            );
            database.closeConnection(true);
            return response;
        } catch (DataAccessException e) {
            database.closeConnection(false);
            e.printStackTrace();
            return new FillResult(
                    "Error: Internal Server Error",
                    false
            );
        }
    }
}
