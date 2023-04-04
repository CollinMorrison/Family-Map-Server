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
import java.util.ArrayList;
import java.util.List;

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
        Person secondPerson = new Person("otherPersonID",
                "otherAssociatedUsername",
                "other firstName",
                "otherLastName",
                "otherFatherID",
                "otherMotherID",
                "otherSpouseID",
                "badGender");
        assertThrows(DataAccessException.class, () -> pDao.Insert(secondPerson));
    }

    @Test
    public void findPass() throws DataAccessException {
        pDao.Insert(bestPerson);
        Person compareTest = pDao.Find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void findPassTwo() throws DataAccessException {
        pDao.Insert(bestPerson);
        Person compareTest = pDao.Find("badID");
        assertNull(compareTest);
        assertNotEquals(compareTest, bestPerson);
    }

    @Test
    public void deletePass() throws DataAccessException {
        pDao.Insert(bestPerson);
        Person compareTest = pDao.Find(bestPerson.getPersonID());
        assertEquals(compareTest, bestPerson);
        pDao.delete(bestPerson.getPersonID());
        compareTest = pDao.Find(bestPerson.getPersonID());
        assertNull(compareTest);
    }

    @Test
    public void deletePassTwo() throws DataAccessException {
        pDao.Insert(bestPerson);
        Person secondPerson = new Person("otherPersonID",
                "otherAssociatedUsername",
                "other firstName",
                "otherLastName",
                "otherFatherID",
                "otherMotherID",
                "otherSpouseID",
                "m");
        pDao.Insert(secondPerson);
        Person compareTest = pDao.Find(bestPerson.getPersonID());
        assertEquals(compareTest, bestPerson);
        pDao.delete(bestPerson.getPersonID());
        compareTest = pDao.Find(bestPerson.getPersonID());
        assertNull(compareTest);
        compareTest = pDao.Find(secondPerson.getPersonID());
        assertEquals(compareTest, secondPerson);
    }

    @Test
    public void getAllPersonsPass() throws DataAccessException {
        Person secondPerson = new Person("otherPersonID",
                "otherAssociatedUsername",
                "other firstName",
                "otherLastName",
                "otherFatherID",
                "otherMotherID",
                "otherSpouseID",
                "m");
        pDao.Insert(bestPerson);
        pDao.Insert(secondPerson);
        List<Person> allPersons = pDao.GetAllPersons();
        assertNotNull(allPersons);
        assertEquals(2, allPersons.size());
    }

    @Test
    public void getAllPersonsPassTwo() throws DataAccessException {
        List<Person> allPersons = pDao.GetAllPersons();
        List<Person> emptyList = new ArrayList<>();
        assertEquals(allPersons, emptyList);
    }

    @Test
    public void getAllPersonsUsernamePass() throws DataAccessException {
        Person secondPerson = new Person("otherPersonID",
                "associatedUsername",
                "other firstName",
                "otherLastName",
                "otherFatherID",
                "otherMotherID",
                "otherSpouseID",
                "m");
        pDao.Insert(bestPerson);
        pDao.Insert(secondPerson);
        List<Person> allPersons = pDao.GetAllPersons("associatedUsername");
        assertEquals(2, allPersons.size());
    }

    @Test
    public void getAllPersonsUsernamePassTwo() throws DataAccessException {
        List<Person> allPersons = pDao.GetAllPersons("badUsername");
        assertEquals(0, allPersons.size());
    }

    @Test
    public void clearPass() throws DataAccessException {
        pDao.Insert(bestPerson);
        pDao.clear();
        Person undefinedPerson = pDao.Find(bestPerson.getPersonID());
        assertNull(undefinedPerson);
    }

    @Test
    public void clearPassTwo() throws DataAccessException {
        pDao.clear();
        Person undefinedPerson = pDao.Find(bestPerson.getPersonID());
        assertNull(undefinedPerson);
    }
}
