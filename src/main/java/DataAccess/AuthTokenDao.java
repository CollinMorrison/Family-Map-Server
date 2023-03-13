package DataAccess;


import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Handles all database communication associated with an AuthToken object
 */
public class AuthTokenDao {

    /**
     * The database connection
     */
    private final Connection conn;

    /**
     * Constructor for the AuthTokenDao
     * @param conn
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Inserts a new AuthToken into the database
     * @param authToken
     * @throws DataAccessException
     */
    public void insert(AuthToken authToken) throws DataAccessException{
        String sql = "INSERT INTO Authtoken (authtoken, username) VALUES(?,?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken.getAuthToken());
            stmt.setString(2, authToken.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an authtoken into the database");
        }
    }

    /**
     * Finds an AuthToken by username
     * @param username
     * @return AuthToken
     * @throws DataAccessException
     */
    public AuthToken Find(String username) throws DataAccessException{
        return null;
    }

    /**
     * Deletes an AuthToken by username
     * @param username
     * @throws DataAccessException
     */
    public void delete(String username) throws DataAccessException{}

    /**
     * Gets a list of all the AuthTokens in the database
     * @return List of AuthTokens
     * @throws DataAccessException
     */
    public List<AuthToken> GetAllAuthTokens() throws DataAccessException{
        return null;
    }
}
