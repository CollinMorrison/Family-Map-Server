package test;

import Request.RegisterRequest;
import Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PersonServiceTest {
    private PersonService personService;
    private RegisterService registerService;
    private ClearService clearService;
    private String authToken;


    @BeforeEach
    public void setUp() {
        personService = new PersonService();
        registerService = new RegisterService();
        clearService = new ClearService();
        clearService.clear();
        RegisterRequest registerRequest = new RegisterRequest(
                "username",
                "password",
                "email",
                "firstname",
                "lastname",
                "m"
        );
        authToken = registerService.register(registerRequest).getAuthtoken();
    }

    @Test
    public void personPass() {
        assertTrue(personService.person(authToken).isSuccess());
    }

    @Test
    public void personFailAuthToken() {
        assertFalse(personService.person("random").isSuccess());
    }
}
