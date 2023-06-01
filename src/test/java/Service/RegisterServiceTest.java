package Service;

import DataAccess.AuthtokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    private RegisterService service;
    private static Database db = new Database();

    @BeforeEach
    public void setUp() {
        service = new RegisterService();
    }

    @AfterAll
    public static void tearDown() throws DataAccessException {
        try {
            db.openConnection();
            Connection conn = db.getConnection();
            UserDao uDao = new UserDao(conn);

            uDao.delete("username");

            AuthtokenDao aDao = new AuthtokenDao(conn);

            aDao.deleteByUsername("username");

            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @Test
    public void registerPass() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setEmail("email");
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setGender("m");

        RegisterResult result = service.register(request);

        assertTrue(result.isSuccess());
        assertEquals("username", result.getUsername());
        assertNotNull(result.getAuthtoken());
        assertNotNull(result.getPersonID());
    }

    @Test
    public void registerAgainFail() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("username");
        request.setPassword("password");
        request.setEmail("email");
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setGender("m");

        RegisterResult result = service.register(request);

        assertFalse(result.isSuccess());
        assertEquals("Error: Database Connection failed", result.getMessage());
    }

}
