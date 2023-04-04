package test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.ClearService;
import Service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    private RegisterService registerService;
    private RegisterRequest request;

    @BeforeEach
    public void setUp() throws DataAccessException {
        registerService = new RegisterService();
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void registerPass() {
        request = new RegisterRequest(
                "username",
                "password",
                "email",
                "firstname",
                "lastname",
                "m"
        );
        RegisterResult response = registerService.register(request);
        assertTrue(response.isSuccess());
    }

    @Test
    public void registerFailWrongGender() {
        request = new RegisterRequest(
                "username",
                "password",
                "email",
                "firstname",
                "lastname",
                "m/f"
        );
        RegisterResult response = registerService.register(request);
        assertFalse(response.isSuccess());
    }

    @Test
    public void registerFailMultipleUsers() {
        request = new RegisterRequest(
                "username",
                "password",
                "email",
                "firstname",
                "lastname",
                "m"
        );
        RegisterResult response = registerService.register(request);
        assertTrue(response.isSuccess());
        response = registerService.register(request);
        assertFalse(response.isSuccess());
    }
}
