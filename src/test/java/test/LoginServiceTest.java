package test;

import DataAccess.DataAccessException;
import DataAccess.Database;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import Service.ClearService;
import Service.LoginService;
import Service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    private RegisterService registerService;
    private LoginService loginService;
    private LoginRequest loginRequest;

    @BeforeEach
    public void setUp() throws DataAccessException {
        loginService = new LoginService();
        registerService = new RegisterService();
        ClearService clearService = new ClearService();
        clearService.clear();
    }

    @Test
    public void loginPass() {
        // Register a valid new user
        registerService.register(new RegisterRequest(
                "username",
                "password",
                "email",
                "firstname",
                "lastname",
                "m"
        ));
        loginRequest = new LoginRequest("username", "password");
        LoginResult response = loginService.login(loginRequest);
        assertTrue(response.isSuccess());
    }

    @Test
    public void loginFail() {
        // Register a valid new user
        registerService.register(new RegisterRequest(
                "username",
                "password",
                "email",
                "firstname",
                "lastname",
                "m"
        ));
        // Try to log in with incorrect credentials
        loginRequest = new LoginRequest("username2", "password");
        LoginResult response = loginService.login(loginRequest);
        assertFalse(response.isSuccess());
    }
}
