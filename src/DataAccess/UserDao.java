package DataAccess;

import Model.AuthToken;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Handles all database communication associated with a User object
 */
public class UserDao {
    /**
     * The database connection
     */
    private final Connection conn;

    /**
     * Constructor for the UserDao
     * @param conn
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new user into the database
     * @param user
     * @throws DataAccessException
     */
    public void insert(User user) throws DataAccessException {
        String sql = "INSERT INTO user (username, password, email, firstName, lastName, gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());
        } catch (SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a user into the database");
        }
    }

    /**
     * Finds a user by their username
     * @param username
     * @return User
     * @throws DataAccessException
     */
    public User find(String username) throws DataAccessException {
        return null;
    }

    /**
     * Deletes a user from the database
     * @param user
     * @throws DataAccessException
     */
    public void delete(User user) throws DataAccessException {}

    /**
     * Returns a list of all the users in the database
     * @return List of users
     */
    public List<User> GetAllUsers() {
        return null;
    }

    /**
     * Validates a user trying to log in
     * @param username
     * @param password
     * @return AuthToken
     */
    public AuthToken Validate(String username, String password) {
        return null;
    }

}
