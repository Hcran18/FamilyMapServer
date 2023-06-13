package Service;

import DataAccess.*;
import Result.LoadResult;
import Request.LoadRequest;
import model.Authtoken;
import model.Event;
import model.Person;
import model.User;

import java.sql.Connection;
import java.util.UUID;

/**
 * Service class that handles the load operation.
 */
public class LoadService {
    /**
     * The Database object.
     */
    Database db;

    /**
     * The Dao object used for accessing the user data.
     */
    UserDao uDao;

    /**
     * The Dao object used for accessing the person data.
     */
    PersonDao pDao;

    /**
     * The Dao object used for accessing the event data.
     */
    EventDAO eDao;

    /**
     * The Dao object used for accessing the authtoken data.
     */
    AuthtokenDao aDao;

    /**
     * An array of User objects.
     */
    User[] users;

    /**
     * An array of Person objects.
     */
    Person[] people;

    /**
     * An array of Event objects.
     */
    Event[] events;

    /**
     * The number of loaded users.
     */
    int numUsers;

    /**
     * The number of loaded people.
     */
    int numPeople;

    /**
     * The number of loaded events.
     */
    int numEvents;

    /**
     * Loads the data from the request into the database.
     *
     * @param r The request containing the data to be loaded.
     * @return The result of the load operation.
     */
    public LoadResult load(LoadRequest r) {
        db = new Database();

        users = r.getUsers();
        people = r.getPersons();
        events = r.getEvents();

        if (users != null && people != null && events != null) {
            numUsers = users.length;
            numPeople = people.length;
            numEvents = events.length;

            try {
                db.openConnection();
                Connection conn = db.getConnection();

                uDao = new UserDao(conn);
                pDao = new PersonDao(conn);
                eDao = new EventDAO(conn);
                aDao = new AuthtokenDao(conn);

                uDao.clear();
                pDao.clear();
                eDao.clear();

                for (User user : users) {
                    uDao.insertUser(user);
                    UUID authtokenID = UUID.randomUUID();
                    Authtoken authtoken = new Authtoken(authtokenID.toString(), user.getUsername());
                    aDao.insertAuthtoken(authtoken);
                }
                for (Person person : people) {
                    pDao.insertPerson(person);
                }
                for (Event event : events) {
                    eDao.insertEvent(event);
                }

                db.closeConnection(true);

                LoadResult result = new LoadResult();
                result.setMessage("Successfully added " + numUsers + " users, " + numPeople + " persons, and " +
                        numEvents + " events to the database.");
                result.setSuccess(true);

                return result;
            } catch (DataAccessException e) {
                e.printStackTrace();

                db.closeConnection(false);

                LoadResult result = new LoadResult();
                result.setMessage("Error: Database Connection failed");
                result.setSuccess(false);

                return result;
            }
        }
        else {
            LoadResult result = new LoadResult();

            result.setMessage("Error: all fields must be filled out");
            result.setSuccess(false);

            return result;
        }
    }
}
