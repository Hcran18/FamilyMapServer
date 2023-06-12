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
     * The Database object used for accessing the database.
     */
    Database db;

    /**
     * The UserDao object used for accessing the user data in the database.
     */
    UserDao uDao;

    /**
     * The PersonDao object used for accessing the person data in the database.
     */
    PersonDao pDao;

    /**
     * The EventDao object used for accessing the event data in the database.
     */
    EventDAO eDao;

    /**
     * The AuthtokenDao object used for accessing the authtoken data in the database.
     */
    AuthtokenDao aDao;

    /**
     * An array of User objects representing the users to load.
     */
    User[] users;

    /**
     * An array of Person objects representing the people to load.
     */
    Person[] people;

    /**
     * An array of Event objects representing the events to load.
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
     * Loads the data from the specified request into the database.
     *
     * @param r The load request containing the data to be loaded.
     * @return The result of the load operation.
     */
    public LoadResult load(LoadRequest r) {
        db = new Database();

        users = r.getUsers();
        people = r.getPersons();
        events = r.getEvents();

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
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);

            LoadResult result = new LoadResult();
            result.setMessage("Error: Database Connection failed");
            result.setSuccess(false);

            return result;
        }
    }
}
