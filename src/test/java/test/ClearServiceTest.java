package test;

import DataAccess.*;
import Model.AuthToken;
import Model.Event;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.RegisterResult;
import Service.ClearService;
import Service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.xml.crypto.Data;

public class ClearServiceTest {
    private ClearService clearService;

    @BeforeEach
    public void setUp() throws DataAccessException {
        clearService = new ClearService();
    }

    @Test
    public void ClearTest() throws DataAccessException {
        assertTrue(clearService.clear().isSuccess());
    }

    @Test
    public void ClearTestTwo() throws DataAccessException {
        assertEquals("Clear Succeeded", clearService.clear().getMessage());
    }


}
