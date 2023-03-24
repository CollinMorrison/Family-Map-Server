package test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private Database db;
    private User bestUser;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestUser = new User("myUsername", "myPassword", "myEmail", "myFirstName", "myLastName", "m", "myID");
        Connection conn = db.getConnection();
        uDao = new UserDao(conn);
        uDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertThrows(DataAccessException.class, () -> uDao.insert(bestUser));
    }

    @Test
    public void findPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertThrows(DataAccessException.class, () -> uDao.insert(bestUser));
    }

    @Test
    public void getAllUsersPass() throws DataAccessException {
        User secondUser = new User(
                "otherUsername",
                "otherPassword",
                "otherEmail",
                "otherFirstName",
                "otherLastName",
                "m",
                "otherPersonID"
        );
        uDao.insert(bestUser);
        uDao.insert(secondUser);
        List<User> allUsers = uDao.GetAllUsers();
        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
    }

    @Test
    public void deletePass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getUsername());
        assertEquals(compareTest, bestUser);
        uDao.delete(bestUser);
        compareTest = uDao.find(bestUser.getUsername());
        assertNull(compareTest);
    }

    @Test
    public void clearTest() throws DataAccessException {
        uDao.insert(bestUser);
        uDao.clear();
        User undefinedUser = uDao.find(bestUser.getUsername());
        assertNull(undefinedUser);
    }
}
