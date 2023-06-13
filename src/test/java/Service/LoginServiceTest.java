package Service;

import DataAccess.AuthtokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Request.LoginRequest;
import Result.LoginResult;
import model.Authtoken;
import model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    private LoginService service;
    private static Database db = new Database();

    @BeforeAll
    public static void beforeAllSetUp() {

        try {
            db.openConnection();
            Connection conn = db.getConnection();
            UserDao uDao = new UserDao(conn);

            User newUser = new User("username", "password", "Dave@email",
                    "Dave", "David", "m", "Dave123A");

            uDao.insertUser(newUser);

            AuthtokenDao aDao = new AuthtokenDao(conn);

            Authtoken newAuthtoken = new Authtoken("myAuthtoken", newUser.getUsername());

            aDao.insertAuthtoken(newAuthtoken);

            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @AfterAll
    public static void tearDown() {
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

    @BeforeEach
    public void setUp() {
        service = new LoginService();
    }

    @Test
    public void loginPass() {
        LoginRequest request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("password");

        LoginResult result = service.login(request);

        assertTrue(result.isSuccess());
        assertNotNull(result.getAuthtoken());
        assertEquals("username", result.getUsername());
        assertNotNull(result.getPersonID());
    }

    @Test
    public void loginUsernameFail() {
        LoginRequest request = new LoginRequest();
        request.setUsername("wrongUsername");
        request.setPassword("password");

        LoginResult result = service.login(request);

        assertFalse(result.isSuccess());
        assertEquals("Error: Invalid login attempt", result.getMessage());
    }

    @Test
    public void loginPasswordFail() {
        LoginRequest request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("WrongPassword");

        LoginResult result = service.login(request);

        assertFalse(result.isSuccess());
        assertEquals("Error: Password is incorrect", result.getMessage());
    }
}
