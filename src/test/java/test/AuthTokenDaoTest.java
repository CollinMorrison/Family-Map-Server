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

}
