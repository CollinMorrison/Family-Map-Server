package test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person bestPerson;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestPerson = new Person("myID", "associatedUsername", "firstName", "lastName", "fatherID", "motherID", "spouseID", "m");
        Connection conn = db.getConnection();
        pDao = new PersonDao(conn);
        pDao.clear();
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        pDao.Insert(bestPerson);
        String testID = bestPerson.getPersonID();
        Person compareTest = pDao.Find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        pDao.Insert(bestPerson);
        assertThrows(DataAccessException.class, () -> pDao.Insert(bestPerson));
    }

    @Test
    public void findPass() throws DataAccessException {
        pDao.Insert(bestPerson);
        Person compareTest = pDao.Find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void findFail() throws DataAccessException {
        pDao.Insert(bestPerson);
        assertThrows(DataAccessException.class, () -> pDao.Insert(bestPerson));
    }

    @Test
    public void clearTest() throws DataAccessException {
        pDao.Insert(bestPerson);
        pDao.clear();
        Person undefinedPerson = pDao.Find(bestPerson.getPersonID());
        assertNull(undefinedPerson);
    }
}
