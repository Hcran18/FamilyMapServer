package DataAccess;

import model.Authtoken;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class AuthtokenDAOTest {
    private Database db;
    private Authtoken newAuthtoken;
    private AuthtokenDao aDao;

    @BeforeEach
    public void setUp() {
        try {
            db = new Database();

            newAuthtoken = new Authtoken("authtoken", "username");

            Connection conn = db.getConnection();

            aDao = new AuthtokenDao(conn);

            aDao.clear();
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() {
        try {
            aDao.insertAuthtoken(newAuthtoken);

            Authtoken compareTest = aDao.findByAuthtoken(newAuthtoken.getAuthtoken());

            assertNotNull(compareTest);
            assertEquals(newAuthtoken, compareTest);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertFail() {
        try {
            aDao.insertAuthtoken(newAuthtoken);

            assertThrows(DataAccessException.class, () -> aDao.insertAuthtoken(newAuthtoken));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByAuthtokenPass() {
        try {
            aDao.insertAuthtoken(newAuthtoken);

            Authtoken foundAuthtoken = aDao.findByAuthtoken(newAuthtoken.getAuthtoken());

            assertNotNull(foundAuthtoken);
            assertEquals(foundAuthtoken, newAuthtoken);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByAuthtokenFail() {
        try {
            assertNull(aDao.findByAuthtoken("InvalidAuthtoken"));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByUsernamePass() {
        try {
            aDao.insertAuthtoken(newAuthtoken);

            Authtoken foundAuthtoken = aDao.findByUsername(newAuthtoken.getUsername());

            assertNotNull(foundAuthtoken);
            assertEquals(foundAuthtoken, newAuthtoken);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findByUsernameFail() {
        try {
            assertNull(aDao.findByUsername("InvalidUsername"));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteByAuthtokenPass() {
        try {
            aDao.insertAuthtoken(newAuthtoken);

            aDao.deleteByAuthtoken(newAuthtoken.getAuthtoken());

            assertNull(aDao.findByAuthtoken(newAuthtoken.getAuthtoken()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteByAuthtokenFail() {
        try {
            aDao.insertAuthtoken(newAuthtoken);

            aDao.deleteByAuthtoken("InvalidAuthtoken");

            assertNotNull(aDao.findByAuthtoken(newAuthtoken.getAuthtoken()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteByUsernamePass() {
        try {
            aDao.insertAuthtoken(newAuthtoken);

            aDao.deleteByUsername(newAuthtoken.getUsername());

            assertNull(aDao.findByUsername(newAuthtoken.getUsername()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteByUsernameFail() {
        try {
            aDao.insertAuthtoken(newAuthtoken);

            aDao.deleteByUsername("InvalidUsername");

            assertNotNull(aDao.findByUsername(newAuthtoken.getUsername()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearTest() {
        try {
            Authtoken authtoken1 = newAuthtoken;
            Authtoken authtoken2 = new Authtoken("SecondAuthtoken", "SecondUsername");

            aDao.insertAuthtoken(authtoken1);
            aDao.insertAuthtoken(authtoken2);

            aDao.clear();

            assertNull(aDao.findByAuthtoken(authtoken1.getAuthtoken()));
            assertNull(aDao.findByAuthtoken(authtoken2.getAuthtoken()));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
