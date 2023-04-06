package test;

import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Request.LoginRequest;
import Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class PersonIDServiceTest {
    private PersonIDService personIDService;
    private String personID;
    private String authToken;
    private ClearService clearService;
    private LoadService loadService;
    private LoadRequest loadRequest;
    private LoginService loginService;


    @BeforeEach
    public void setUp() {
        personIDService = new PersonIDService();
        clearService = new ClearService();
        clearService.clear();
        loadService = new LoadService();
        loginService = new LoginService();
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
        loadService.load(loadRequest);
        authToken = loginService.login(new LoginRequest("username1", "password1")).getAuthtoken();
    }

    @Test
    public void personIDPass() {
        assertTrue(personIDService.personID(authToken, "1").isSuccess());
    }

    @Test
    public void personIDFailAuthToken() {
        assertFalse(personIDService.personID("randomAuth", "1").isSuccess());
    }

    @Test
    public void personIDFailPersonID() {
        assertFalse(personIDService.personID(authToken, "123445").isSuccess());
    }

    @Test
    public void personIDFailNoAssociation() {
        assertFalse(personIDService.personID(authToken, "3").isSuccess());
    }
}
