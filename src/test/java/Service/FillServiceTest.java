package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import FakeFamilyData.*;
import Result.FillResult;
import com.google.gson.Gson;
import model.User;
import org.junit.jupiter.api.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    private FillService service;
    private static Database db = new Database();

    @BeforeAll
    public static void beforeAllSetUp() {
        try {

            Gson gson = new Gson();

            Reader locationReader = new FileReader("json/locations.json");
            LocationData locData = gson.fromJson(locationReader, LocationData.class);
            Cache.setLocations(locData);

            Reader mNamesReader = new FileReader("json/mnames.json");
            MNamesData maleData = gson.fromJson(mNamesReader, MNamesData.class);
            Cache.setMaleNames(maleData);

            Reader fNamesReader = new FileReader("json/fnames.json");
            FNamesData femaleData = gson.fromJson(fNamesReader, FNamesData.class);
            Cache.setFemaleNames(femaleData);

            Reader sNamesReader = new FileReader("json/snames.json");
            SNamesData surnamesData = gson.fromJson(sNamesReader, SNamesData.class);
            Cache.setSurnames(surnamesData);
        }
        catch (FileNotFoundException e) {
            System.out.println("Unable to Store Data");
            e.printStackTrace();
        }

        try {
            db.openConnection();
            Connection conn = db.getConnection();

            UserDao uDao = new UserDao(conn);

            User newUser = new User("username", "password", "Dave@email",
                    "Dave", "David", "m", "Dave123A");

            uDao.insertUser(newUser);

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

            uDao.clear();

            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
    }

    @BeforeEach
    public void setUp() {
        service = new FillService();
    }

    @Test
    public void fillPassGen2() {
        FillResult result = service.fill("username", 2);

        assertEquals("Successfully added 7 persons and 19 events to the database.",
                result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test
    public void fillPassGen5() {
        FillResult result = service.fill("username", 5);

        assertEquals("Successfully added 63 persons and 187 events to the database.",
                result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test void fillTwice() {
        FillResult result = service.fill("username", 3);

        assertEquals("Successfully added 15 persons and 43 events to the database.",
                result.getMessage());
        assertTrue(result.isSuccess());

        result = service.fill("username", 4);

        assertEquals("Successfully added 31 persons and 91 events to the database.",
                result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test void fillFail() {
        FillResult result = service.fill("IncorrectUsername", 1);

        assertEquals("Error: User does not exist", result.getMessage());
        assertFalse(result.isSuccess());
    }
}
