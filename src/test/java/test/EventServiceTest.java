package test;

import Request.RegisterRequest;
import Result.EventResult;
import Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class EventServiceTest {
    private EventService eventService;
    private RegisterService registerService;
    private ClearService clearService;
    private String authToken;

    @BeforeEach
    public void setUp() {
        eventService = new EventService();
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
    public void eventPass() {
        assertTrue(eventService.event(authToken).isSuccess());
    }

    @Test
    public void eventFailAuthToken() {
        assertFalse(eventService.event("random").isSuccess());
    }
}
