package DataAccess;


import Model.AuthToken;

import java.sql.Connection;
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
    public void insert(AuthToken authToken) throws DataAccessException{}

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
