package DataAccess;

import Model.AuthToken;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        String sql = "INSERT INTO User (username, password, email, firstName, lastName, gender, personID) VALUES(?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());
            stmt.executeUpdate();
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
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"), rs.getString("personID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a user in the database");
        }
    }

    /**
     * Deletes a user from the database
     * @param user
     * @throws DataAccessException
     */
    public void delete(User user) throws DataAccessException {
        String sql = "DELETE FROM User WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting a user from the User table");
        }
    }

    /**
     * Returns a list of all the users in the database
     * @return List of users
     */
    public List<User> GetAllUsers() throws DataAccessException {
        List<User> allUsers = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM User;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            while (rs.next()) {
                User userToAdd = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getString("personID")
                );
                allUsers.add(userToAdd);
            }
            return allUsers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error getting the users from the User table");
        }
    }

    /**
     * Validates a user trying to log in
     * @param username
     * @param password
     * @return AuthToken
     */
    public AuthToken Validate(String username, String password) throws DataAccessException {
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (!rs.next()) {
                return null;
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while logging a user into the database");
        }
        //generate a new auth token
        UUID uuid = UUID.randomUUID();
        String authTokenValue = uuid.toString();
        AuthToken authToken = new AuthToken(authTokenValue, username);
        //insert the new authToken into the database
        AuthTokenDao authTokenDao = new AuthTokenDao(this.conn);
        try {
            authTokenDao.insert(authToken);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return authToken;
    }

    /**
     * Clears all users from the database
     * @throws DataAccessException
     */
    public void clear() throws DataAccessException {
        String sql = "DELETE FROM User";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the user table");
        }
    }

}
