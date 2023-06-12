package Generation;

import DataAccess.DataAccessException;
import DataAccess.EventDAO;
import DataAccess.PersonDao;
import FakeFamilyData.Cache;
import FakeFamilyData.LocationData;
import model.Event;
import model.Person;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

/**
 * The EventGenerator class is responsible for generating various events
 */
public class EventGenerator {
    /**
     * The LocationData object containing location information for event generation.
     */
    private LocationData locations = Cache.getLocations();

    /**
     * The associated username for the events.
     */
    private String associatedUsername;

    /**
     * The connection object used for database operations.
     */
    private Connection conn;

    /**
     * The PersonDao object for accessing and manipulating person data.
     */
    private PersonDao pDao;

    /**
     * The EventDAO object for accessing and manipulating event data.
     */
    private EventDAO eDao;

    /**
     * Constructs an EventGenerator object with the specified associated username and database connection.
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
    public void generateMarriageEvents(Person mother, Person father) throws DataAccessException {
        // Randomly generate the location of the event
        int randomIndex = (int) (Math.random() * locations.getData().length);
        float latitude = Float.parseFloat(locations.getData()[randomIndex].getLatitude());
        float longitude = Float.parseFloat(locations.getData()[randomIndex].getLongitude());
        String country = locations.getData()[randomIndex].getCountry();
        String city = locations.getData()[randomIndex].getCity();
        String eventType = "Marriage";

        Random random = new Random();

        int motherBirthYear = eDao.findBirthYear(mother.getPersonID());
        int fatherBirthYear = eDao.findBirthYear(father.getPersonID());

        int motherDeathYear = eDao.findDeathYear(mother.getPersonID());
        int fatherDeathYear = eDao.findDeathYear(father.getPersonID());

        if (fatherDeathYear < motherBirthYear) {
            fatherBirthYear = motherBirthYear + random.nextInt(motherDeathYear - motherBirthYear);
            fatherDeathYear = fatherBirthYear + random.nextInt((motherDeathYear + 5) - fatherBirthYear);

            if ((fatherDeathYear - fatherBirthYear) < 13) {
                fatherDeathYear = fatherDeathYear + 13;
            }

            // Change the birth and death dates in the database
            eDao.updateBirthByID(father.getPersonID(), fatherBirthYear);
            eDao.updateDeathByID(father.getPersonID(), fatherDeathYear);
        }
        if (motherDeathYear < fatherBirthYear) {
            motherBirthYear = fatherBirthYear + random.nextInt(fatherDeathYear - fatherBirthYear);
            motherDeathYear = motherBirthYear + random.nextInt((fatherDeathYear + 5) - motherBirthYear);

            if ((motherDeathYear - motherBirthYear) < 13) {
                motherDeathYear = motherDeathYear + 13;
            }

            // Change the birth and death dates in the database
            eDao.updateBirthByID(mother.getPersonID(), motherBirthYear);
            eDao.updateDeathByID(mother.getPersonID(), motherDeathYear);
        }

        // Make sure that they are at least 13 when married
        int minMarriageYear = Math.max(motherBirthYear, fatherBirthYear) + 13;
        // Make sure that they are not married after their deaths
        int maxMarriageYear = Math.min(motherDeathYear, fatherDeathYear);

        if (minMarriageYear > motherDeathYear) {
            motherDeathYear = motherDeathYear + (minMarriageYear - motherDeathYear);
            minMarriageYear = Math.max(motherBirthYear, fatherBirthYear) + 13;
            maxMarriageYear = Math.min(motherDeathYear, fatherDeathYear);

            // Change death year in the database
            eDao.updateDeathByID(mother.getPersonID(), motherDeathYear);
        }
        if (minMarriageYear > fatherDeathYear) {
            fatherDeathYear = fatherDeathYear + (minMarriageYear - fatherDeathYear);
            minMarriageYear = Math.max(motherBirthYear, fatherBirthYear) + 13;
            maxMarriageYear = Math.min(motherDeathYear, fatherDeathYear);

            // Change death year in the database
            eDao.updateDeathByID(father.getPersonID(), fatherDeathYear);
        }

        if (motherDeathYear == maxMarriageYear && motherDeathYear == minMarriageYear) {
            motherDeathYear = motherDeathYear + 1;
            minMarriageYear = Math.max(motherBirthYear, fatherBirthYear) + 13;
            maxMarriageYear = Math.min(motherDeathYear, fatherDeathYear);

            // Change death year in the database
            eDao.updateDeathByID(mother.getPersonID(), motherDeathYear);
        }
        if (fatherDeathYear == maxMarriageYear && fatherDeathYear == minMarriageYear) {
            fatherDeathYear = fatherDeathYear + 1;
            minMarriageYear = Math.max(motherBirthYear, fatherBirthYear) + 13;
            maxMarriageYear = Math.min(motherDeathYear, fatherDeathYear);

            // Change death year in the database
            eDao.updateDeathByID(father.getPersonID(), fatherDeathYear);
        }


        // Generate a random year for their marriage between the age of 13 and a year before their death
        int year = minMarriageYear + random.nextInt(maxMarriageYear - minMarriageYear);

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
     * @param person The person object for whom to generate the birth event.
     * @throws DataAccessException If there is an error accessing the data.
     */
    public void generateBirthEvent(Person person) throws DataAccessException {
        // Randomly generate the location of the event
        int randomIndex = (int) (Math.random() * locations.getData().length);
        float latitude = Float.parseFloat(locations.getData()[randomIndex].getLatitude());
        float longitude = Float.parseFloat(locations.getData()[randomIndex].getLongitude());
        String country = locations.getData()[randomIndex].getCountry();
        String city = locations.getData()[randomIndex].getCity();
        String eventType = "Birth";

        String childID = pDao.findChild(person.getPersonID());
        int childBirthYear = eDao.findBirthYear(childID);

        // Find the year in where the parent would be at least 13 years older than their child
        int maxParentBirthYear = childBirthYear - 13;

        // Generate random year with the constraints
        Random random = new Random();
        int year = maxParentBirthYear + random.nextInt(childBirthYear - maxParentBirthYear);

        if (childID == null) {
            year = 1500 + random.nextInt(Calendar.getInstance().get(Calendar.YEAR) - 1530);
        }

        //TODO Make sure that this works
        // If female then make sure they cannot have children over the age of 50
        if (person.getGender().equals("f")) {
            int maxChildBearingYear = 50;
            int motherAge = childBirthYear - year;

            if (motherAge > maxChildBearingYear) {
                int subtractYears = motherAge - maxChildBearingYear + 1;
                year = year - subtractYears;
            }
        }

        Event birth = new Event(generateUniqueID(), associatedUsername, person.getPersonID(),
                latitude, longitude, country, city, eventType, year);

        eDao.insertEvent(birth);
    }

    /**
     * Generates a death event for the given person.
     *
     * @param person The person object for whom to generate the death event.
     * @throws DataAccessException If there is an error accessing the data.
     */
    public void generateDeathEvent(Person person) throws DataAccessException {
        // Randomly generate the location of the event
        int randomIndex = (int) (Math.random() * locations.getData().length);
        float latitude = Float.parseFloat(locations.getData()[randomIndex].getLatitude());
        float longitude = Float.parseFloat(locations.getData()[randomIndex].getLongitude());
        String country = locations.getData()[randomIndex].getCountry();
        String city = locations.getData()[randomIndex].getCity();
        String eventType = "Death";

        String childID = pDao.findChild(person.getPersonID());
        int childBirthYear = eDao.findBirthYear(childID);

        // Find their child's birth year and make sure they cannot die before that
        int minDeathYear = childBirthYear + 13;

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        // Generate random death year with the constraints
        Random random = new Random();
        int year = minDeathYear + random.nextInt(currentYear - minDeathYear + 1);

        while (year < eDao.findBirthYear(person.getPersonID())) {
            year = minDeathYear + random.nextInt(currentYear - minDeathYear + 1);
        }

        // Get the persons current age
        int personBirthYear = eDao.findBirthYear(person.getPersonID());
        int maxAge = 120;
        int currentAge = year - personBirthYear;

        // Make sure they did not live past 120
        if (currentAge > maxAge) {
            int subtractAge = currentAge - maxAge + 1;
            year = year - subtractAge;
        }

        Event death = new Event(generateUniqueID(), associatedUsername, person.getPersonID(),
                latitude, longitude, country, city, eventType, year);

        eDao.insertEvent(death);
    }

    /**
     * Generates a Birth event for the user based on the parents' information.
     *
     * @param personID The ID of the user (child) for whom to generate the marriage event.
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

        do {
            year = random.nextInt(motherYearAge50 - fatherYearAge13 + 1) + fatherYearAge13;
        } while (year <= motherBirthYear + 13);

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
