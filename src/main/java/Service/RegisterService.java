package Service;

import DataAccess.*;
import FakeFamilyData.Cache;
import FakeFamilyData.LocationData;
import Generation.FamilyTreeGenerator;
import Request.RegisterRequest;
import Result.RegisterResult;
import model.Authtoken;
import model.Event;
import model.Person;
import model.User;

import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

import java.sql.Connection;

/**
 * Service class that handles the registration operation.
 */
public class RegisterService {
    private Database db;
    private User newUser;
    private Authtoken newAuthtoken;
    private UserDao uDao;
    private PersonDao pDao;
    private AuthtokenDao aDao;
    private EventDAO eDao;
    private LocationData locations = Cache.getLocations();
    /**
     * Registers a new user based on the provided registration request.
     *
     * @param r The registration request containing the user details.
     * @return The result of the registration operation.
     */
    public RegisterResult register(RegisterRequest r) {
        db = new Database();

        try {
            // Start connection
            db.openConnection();

            Connection conn = db.getConnection();

            uDao = new UserDao(conn);
            UUID personIDUuid = UUID.randomUUID();
            String personID = personIDUuid.toString();

            if (r.getUsername() == null || r.getPassword() == null || r.getEmail() == null || r.getFirstName() == null
                || r.getLastName() == null || r.getGender() == null) {
                // Check for if gender is either m or f
                if (r.getGender().length() == 1 && (Objects.equals(r.getGender(), "m") ||
                        Objects.equals(r.getGender(), "f"))) {

                    // Create new user and authtoken
                    newUser = new User(r.getUsername(), r.getPassword(), r.getEmail(), r.getFirstName(),
                            r.getLastName(), r.getGender(), personID);

                    aDao = new AuthtokenDao(conn);
                    UUID authtokenUuid = UUID.randomUUID();
                    String authtoken = authtokenUuid.toString();
                    newAuthtoken = new Authtoken(authtoken, r.getUsername());

                    uDao.insertUser(newUser);
                    aDao.insertAuthtoken(newAuthtoken);

                    // Create and generate a family tree
                    FamilyTreeGenerator ft = new FamilyTreeGenerator(4, r.getGender(), r.getUsername(), conn);

                    ft.generateFamilyTree();

                    // Create a person to add to the person table for the user
                    pDao = new PersonDao(conn);
                    Person user = new Person(personID, r.getUsername(), r.getFirstName(), r.getLastName(), r.getGender(),
                            pDao.findLastPersonID(), pDao.findLastPersonSpouseID(), null);

                    pDao.insertPerson(user);

                    UUID eventUuid = UUID.randomUUID();
                    String eventID = eventUuid.toString();
                    int randomIndex = (int) (Math.random() * locations.getData().length);
                    float latitude = Float.parseFloat(locations.getData()[randomIndex].getLatitude());
                    float longitude = Float.parseFloat(locations.getData()[randomIndex].getLongitude());
                    String country = locations.getData()[randomIndex].getCountry();
                    String city = locations.getData()[randomIndex].getCity();

                    eDao = new EventDAO(conn);
                    Event birth = new Event(eventID, r.getUsername(), personID, latitude, longitude,
                            country, city, "Birth", Calendar.getInstance().get(Calendar.YEAR) - 15);

                    eDao.insertEvent(birth);
                    db.closeConnection(true);

                    // Return the results of the registration
                    RegisterResult result = new RegisterResult();

                    result.setAuthtoken(authtoken);
                    result.setUsername(r.getUsername());
                    result.setPersonID(personID);
                    result.setSuccess(true);

                    return result;
                } else {
                    db.closeConnection(false);

                    RegisterResult result = new RegisterResult();

                    result.setMessage("Error: Gender can only be m or f");
                    result.setSuccess(false);

                    return result;
                }
            }
            else {
                db.closeConnection(false);

                RegisterResult result = new RegisterResult();

                result.setMessage("Error: Credentials must be filled out");
                result.setSuccess(false);

                return result;
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);
            RegisterResult result = new RegisterResult();

            result.setMessage("Error: Database Connection failed");
            result.setSuccess(false);

            return result;
        }
    }
}
