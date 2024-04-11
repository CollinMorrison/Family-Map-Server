package test;

import DataAccess.DataAccessException;
import Request.RegisterRequest;
import Service.ClearService;
import Service.FillService;
import Service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    private FillService fillService;
    private RegisterService registerService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        fillService = new FillService();
        registerService = new RegisterService();
        ClearService clearService = new ClearService();
        clearService.clear();
        registerService.register(new RegisterRequest(
                "username",
                "password",
                "email",
                "firstname",
                "lastname",
                "m"
        ));
    }

    @Test
    public void fillPass() {
        assertTrue(fillService.fill(2, "username").isSuccess());
    }

    @Test
    public void fillFailUsername() {
        assertFalse(fillService.fill(2, "username21").isSuccess());
    }

    @Test void fillFailGenerations() {
        assertFalse(fillService.fill(-1, "username").isSuccess());
    }
}
