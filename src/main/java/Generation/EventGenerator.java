package Generation;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import DataAccess.PersonDao;
import FakeFamilyData.Cache;
import FakeFamilyData.LocationData;
import model.Event;
import model.Person;

import java.sql.Connection;
import java.util.Random;
import java.util.UUID;

/**
 * The EventGenerator class is responsible for generating various events
 */
public class EventGenerator {
    /**
     * The LocationData object containing location information.
     */
    private LocationData locations = Cache.getLocations();

    /**
     * The associated username for the events.
     */
    private String associatedUsername;

    /**
     * The connection object.
     */
    private Connection conn;

    /**
     * The Dao object for accessing and manipulating person data.
     */
    private PersonDao pDao;

    /**
     * The DAO object for accessing and manipulating event data.
     */
    private EventDAO eDao;

    /**
     * Constructs an EventGenerator object.
     *
     * @param associatedUsername The associated username for the events.
     * @param conn The database connection.
     */
    public EventGenerator(String associatedUsername, Connection conn) {
        this.associatedUsername = associatedUsername;
        this.conn = conn;
        this.pDao = new PersonDao(conn);
        this.eDao = new EventDAO(conn);
    }

    /**
     * Generates marriage events for the given mother and father.
     *
     * @param mother The mother person object.
     * @param father The father person object.
     * @throws DataAccessException If there is an error accessing the data.
     */
    public void generateMarriageEvents(Person mother, Person father, int year) throws DataAccessException {
        // Randomly generate the location of the event
        int randomIndex = (int) (Math.random() * locations.getData().length);
        float latitude = Float.parseFloat(locations.getData()[randomIndex].getLatitude());
        float longitude = Float.parseFloat(locations.getData()[randomIndex].getLongitude());
        String country = locations.getData()[randomIndex].getCountry();
        String city = locations.getData()[randomIndex].getCity();
        String eventType = "Marriage";

        // Create and insert the marriage events
        Event marriageMother = new Event(generateUniqueID(), associatedUsername, mother.getPersonID(),
                latitude, longitude, country, city, eventType, year);
        Event marriageFather = new Event(generateUniqueID(), associatedUsername, father.getPersonID(),
                latitude, longitude, country, city, eventType, year);

        eDao.insertEvent(marriageMother);
        eDao.insertEvent(marriageFather);
    }

    /**
     * Generates a birth event for the given person.
     *
     * @param person The person object to generate a birth event for
     * @throws DataAccessException If there is an error accessing the data.
     */
    public void generateBirthEvent(Person person, int year) throws DataAccessException {
        // Randomly generate the location of the event
        int randomIndex = (int) (Math.random() * locations.getData().length);
        float latitude = Float.parseFloat(locations.getData()[randomIndex].getLatitude());
        float longitude = Float.parseFloat(locations.getData()[randomIndex].getLongitude());
        String country = locations.getData()[randomIndex].getCountry();
        String city = locations.getData()[randomIndex].getCity();
        String eventType = "Birth";

        Event birth = new Event(generateUniqueID(), associatedUsername, person.getPersonID(),
                latitude, longitude, country, city, eventType, year);

        eDao.insertEvent(birth);
    }

    /**
     * Generates a death event for the given person.
     *
     * @param person The person object to generate a death event for.
     * @throws DataAccessException If there is an error accessing the data.
     */
    public void generateDeathEvent(Person person, int year) throws DataAccessException {
        // Randomly generate the location of the event
        int randomIndex = (int) (Math.random() * locations.getData().length);
        float latitude = Float.parseFloat(locations.getData()[randomIndex].getLatitude());
        float longitude = Float.parseFloat(locations.getData()[randomIndex].getLongitude());
        String country = locations.getData()[randomIndex].getCountry();
        String city = locations.getData()[randomIndex].getCity();
        String eventType = "Death";


        Event death = new Event(generateUniqueID(), associatedUsername, person.getPersonID(),
                latitude, longitude, country, city, eventType, year);

        eDao.insertEvent(death);
    }

    /**
     * Generates a Birth event for the user.
     *
     * @param personID The ID of the user
     * @throws DataAccessException If there is an error accessing the data.
     */
    public void generateBirthForUser(String personID) throws DataAccessException {
        Person user = pDao.findByID(personID);

        String motherID = user.getMotherID();
        int motherBirthYear = eDao.findBirthYear(motherID);
        int motherYearAge50 = motherBirthYear + 50;

        String fatherID = user.getFatherID();
        int fatherBirthYear = eDao.findBirthYear(fatherID);
        int fatherYearAge13 = fatherBirthYear + 13;

        Random random = new Random();
        int year;

        int bound = motherYearAge50 - fatherYearAge13 + 1;

        assert bound > 0;

        year = random.nextInt(bound) + fatherYearAge13;

        int randomIndex = (int) (Math.random() * locations.getData().length);
        float latitude = Float.parseFloat(locations.getData()[randomIndex].getLatitude());
        float longitude = Float.parseFloat(locations.getData()[randomIndex].getLongitude());
        String country = locations.getData()[randomIndex].getCountry();
        String city = locations.getData()[randomIndex].getCity();
        String eventType = "Birth";

        Event birth = new Event(generateUniqueID(), associatedUsername, personID, latitude, longitude,
                country, city, eventType, year);

        eDao.insertEvent(birth);
    }

    /**
     * Generates a unique ID for events.
     *
     * @return A unique ID string.
     */
    private String generateUniqueID() {
        return UUID.randomUUID().toString();
    }
}
