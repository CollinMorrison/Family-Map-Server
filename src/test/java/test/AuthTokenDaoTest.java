package test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.AuthTokenDao;
import Model.AuthToken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.util.ArrayList;
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
    public void findPassTwo() throws DataAccessException {
        authTokenDao.insert(bestAuthToken);
        AuthToken compareTest = authTokenDao.Find("badUsername");
        assertNull(compareTest);
        assertNotEquals(bestAuthToken, compareTest);
    }

    @Test
    public void findByAuthTokenPass() throws DataAccessException {
        authTokenDao.insert(bestAuthToken);
        AuthToken compareTest = authTokenDao.FindByAuthtoken("randomString");
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }

    @Test
    public void findByAuthTokenPassTwo() throws DataAccessException {
        authTokenDao.insert(bestAuthToken);
        AuthToken compareTest = authTokenDao.FindByAuthtoken("badToken");
        assertNull(compareTest);
        assertNotEquals(bestAuthToken, compareTest);
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

    @Test void getAllTokensPassTwo() throws DataAccessException {
        List<AuthToken> allAuthTokens = authTokenDao.GetAllAuthTokens();
        List<AuthToken> emptyList = new ArrayList<>();
        assertEquals(allAuthTokens, emptyList);
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
    public void deletePassTwo() throws DataAccessException {
        authTokenDao.insert(bestAuthToken);
        AuthToken secondAuthToken = new AuthToken("otherAuthToken", "otherUsername");
        authTokenDao.insert(secondAuthToken);
        AuthToken compareTest = authTokenDao.Find(bestAuthToken.getUsername());
        assertEquals(compareTest, bestAuthToken);
        authTokenDao.delete(bestAuthToken.getUsername());
        compareTest = authTokenDao.Find(bestAuthToken.getUsername());
        assertNull(compareTest);
        compareTest = authTokenDao.Find(secondAuthToken.getUsername());
        assertEquals(compareTest, secondAuthToken);
    }

    @Test
    public void clearPass() throws DataAccessException {
        authTokenDao.insert(bestAuthToken);
        authTokenDao.clear();
        AuthToken undefinedAuthToken = authTokenDao.Find(bestAuthToken.getUsername());
        assertNull(undefinedAuthToken);
    }

    @Test
    public void clearPassTwo() throws DataAccessException {
        authTokenDao.clear();
        AuthToken undefinedAuthToken = authTokenDao.Find(bestAuthToken.getUsername());
        assertNull(undefinedAuthToken);
    }
}
