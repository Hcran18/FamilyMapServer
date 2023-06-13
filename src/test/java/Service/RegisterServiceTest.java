package Service;

import DataAccess.AuthtokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import FakeFamilyData.*;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    private RegisterService service;
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
    }
    @BeforeEach
    public void setUp() {
        service = new RegisterService();
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
