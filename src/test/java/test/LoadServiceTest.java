package test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Service.LoadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest {
    private LoadService loadService;
    private LoadRequest loadRequest;

    @BeforeEach
    public void setUp() {
        loadService = new LoadService();
        User[] users = {
                new User("username1",
                        "password1",
                        "email1",
                        "firstname1",
                        "lastname1",
                        "m",
                        "abc"),

                new User("username2",
                        "password2",
                        "email2",
                        "firstname2",
                        "lastname2",
                        "f",
                        "def"),

                new User("username3",
                        "password3",
                        "email3",
                        "firstname3",
                        "lastname3",
                        "m",
                        "ghi")
        };
        Person[] persons = {
                new Person("1",
                "username1",
                "firstName1",
                "lastName1",
                "fatherID1",
                "motherID1",
                "spouseID1",
                "m"),

            new Person("2",
                "username2",
                "firstName2",
                "lastName2",
                "fatherID2",
                "motherID2",
                "spouseID2",
                "m"),

            new Person("3",
                "username3",
                "firstName3",
                "lastName3",
                "fatherID3",
                "motherID3",
                "spouseID3",
                "m")
        };
        Event[] events = {

            new Event("eventID1",
                "associatedUsername1",
                "personID1",
                (float)1.1,
                (float)1.1,
                "country1",
                "city1",
                "eventType1",
                1),

            new Event("eventID2",
                "associatedUsername2",
                "personID2",
                (float)1.2,
                (float)1.2,
                "country2",
                "city2",
                "eventType2",
                2),

            new Event("eventID3",
                "associatedUsername3",
                "personID3",
                (float)1.3,
                (float)1.3,
                "country3",
                "city3",
                "eventType3",
                3)

        };
        loadRequest = new LoadRequest(users, persons, events);
    }

    @Test
    public void loadPass() {
        assertTrue(loadService.load(loadRequest).getSuccess());
    }

    @Test
    public void loadFailInvalidData() {
        User[] users = {
                new User("username1",
                        "password1",
                        "email1",
                        "firstname1",
                        "lastname1",
                        "m",
                        "abc"),

                new User("username2",
                        "password2",
                        "email2",
                        "firstname2",
                        "lastname2",
                        "f",
                        "abc"),

                new User("username3",
                        "password3",
                        "email3",
                        "firstname3",
                        "lastname3",
                        "m",
                        "ghi")
        };
        Person[] persons = {
                new Person("1",
                        "username1",
                        "firstName1",
                        "lastName1",
                        "fatherID1",
                        "motherID1",
                        "spouseID1",
                        "m"),

                new Person("2",
                        "username2",
                        "firstName2",
                        "lastName2",
                        "fatherID2",
                        "motherID2",
                        "spouseID2",
                        "m"),

                new Person("3",
                        "username3",
                        "firstName3",
                        "lastName3",
                        "fatherID3",
                        "motherID3",
                        "spouseID3",
                        "m")
        };
        Event[] events = {

                new Event("eventID1",
                        "associatedUsername1",
                        "personID1",
                        (float)1.1,
                        (float)1.1,
                        "country1",
                        "city1",
                        "eventType1",
                        1),

                new Event("eventID2",
                        "associatedUsername2",
                        "personID2",
                        (float)1.2,
                        (float)1.2,
                        "country2",
                        "city2",
                        "eventType2",
                        2),

                new Event("eventID3",
                        "associatedUsername3",
                        "personID3",
                        (float)1.3,
                        (float)1.3,
                        "country3",
                        "city3",
                        "eventType3",
                        3)

        };
        loadRequest = new LoadRequest(users, persons, events);
        assertFalse(loadService.load(loadRequest).getSuccess());
    }
}
