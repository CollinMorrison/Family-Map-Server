package DataAccess;


import Model.AuthToken;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public AuthToken Find(String username) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authToken in the database");
        }
        return null;
    }

    public AuthToken FindByAuthtoken(String authTokenString) throws DataAccessException {
        AuthToken authToken;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authTokenString);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authToken = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an authToken in the database");
        }
        return null;
    }

    /**
     * Deletes an AuthToken by username
     * @param username
     * @throws DataAccessException
     */
    public void delete(String username) throws DataAccessException{
        String sql = "DELETE FROM Authtoken WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting an authToken from the database");
        }
    }

    /**
     * Gets a list of all the AuthTokens in the database
     * @return List of AuthTokens
     * @throws DataAccessException
     */
    public List<AuthToken> GetAllAuthTokens() throws DataAccessException{
        List<AuthToken> allAuthTokens = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            rs = stmt.executeQuery();
            while (rs.next()) {
                AuthToken authTokenToAdd = new AuthToken(rs.getString("authtoken"), rs.getString("username"));
                allAuthTokens.add(authTokenToAdd);
            }
            return allAuthTokens;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while getting all the authTokens");
        }
    }

    public void clear() throws DataAccessException {
        String sql = "DELETE FROM Authtoken";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the Authtoken table");
        }
    }
}
