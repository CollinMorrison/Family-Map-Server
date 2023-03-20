package test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.AuthTokenDao;
import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthTokenDaoTest {
    private Database db;
    private AuthToken bestAuthToken;
    private AuthTokenDao authTokenDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestAuthToken = new AuthToken("randomString", "myUsername");
        Connection conn = db.getConnection();
        authTokenDao = new AuthTokenDao(conn);
        authTokenDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        authTokenDao.insert(bestAuthToken);
        AuthToken compareTest = authTokenDao.Find("myUsername");
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        authTokenDao.insert(bestAuthToken);
        assertThrows(DataAccessException.class, () -> authTokenDao.insert(bestAuthToken));
    }

    @Test
    public void findPass() throws DataAccessException {
        authTokenDao.insert(bestAuthToken);
        AuthToken compareTest = authTokenDao.Find("myUsername");
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        authTokenDao.insert(bestAuthToken);
        assertThrows(DataAccessException.class, () -> authTokenDao.insert(bestAuthToken));
    }

    @Test
    public void getAllTokensPass() throws DataAccessException {
        AuthToken secondAuthToken = new AuthToken("otherAuthToken", "otherUsername");
        authTokenDao.insert(bestAuthToken);
        authTokenDao.insert(secondAuthToken);
        List<AuthToken> allAuthTokens = authTokenDao.GetAllAuthTokens();
        assertNotNull(allAuthTokens);
        assertEquals(2, allAuthTokens.size());
    }

    @Test
    public void deletePass() throws DataAccessException {
        authTokenDao.insert(bestAuthToken);
        AuthToken compareTest = authTokenDao.Find(bestAuthToken.getUsername());
        assertEquals(compareTest, bestAuthToken);
        authTokenDao.delete(bestAuthToken.getUsername());
        compareTest = authTokenDao.Find(bestAuthToken.getUsername());
        assertNull(compareTest);
    }

    @Test
    public void clearTest() throws DataAccessException {
        authTokenDao.insert(bestAuthToken);
        authTokenDao.clear();
        AuthToken undefinedAuthToken = authTokenDao.Find(bestAuthToken.getUsername());
        assertNull(undefinedAuthToken);
    }
}
