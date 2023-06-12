package Generation;

import DataAccess.DataAccessException;
import DataAccess.PersonDao;
import FakeFamilyData.*;
import Generation.EventGenerator;
import model.Person;

import java.sql.Connection;
import java.util.UUID;

/**
 * The FamilyTreeGenerator class is responsible for generating a family tree based on the provided parameters.
 */
public class FamilyTreeGenerator {
    /**
     * The number of generations to generate in the family tree.
     */
    private int numGenerations;

    /**
     * The cached male first names data.
     */
    private MNamesData maleNames = Cache.getMaleNames();

    /**
     * The cached female first names data.
     */
    private FNamesData femaleNames = Cache.getFemaleNames();

    /**
     * The cached surnames data.
     */
    private SNamesData surnames = Cache.getSurnames();

    /**
     * The person ID of the root person in the family tree.
     */
    private String personID;

    /**
     * The associated username for the family tree.
     */
    private String associatedUsername;

    /**
     * The first name of the root person in the family tree.
     */
    private String firstName;

    /**
     * The last name of the root person in the family tree.
     */
    private String lastName;

    /**
     * The gender of the root person in the family tree.
     */
    private String rootGender;

    /**
     * The person ID of the user's father in the family tree.
     */
    private String userFather;

    /**
     * The person ID of the user's mother in the family tree.
     */
    private String userMother;

    /**
     * The database connection used for interacting with the database.
     */
    private Connection conn;

    /**
     * The DAO for interacting with the Person table in the database.
     */
    private PersonDao pDao;

    /**
     * The event generator used for generating events for the family tree.
     */
    private EventGenerator eventGenerator;

    /**
     * Constructs a FamilyTreeGenerator object with the specified parameters.
     *
     * @param numGenerations     the number of generations to generate in the family tree
     * @param personID           the person ID of the root person in the family tree
     * @param associatedUsername the associated username for the family tree
     * @param firstName          the first name of the root person in the family tree
     * @param lastName           the last name of the root person in the family tree
     * @param rootGender         the gender of the root person in the family tree
     * @param conn               the database connection
     */
    public FamilyTreeGenerator(int numGenerations, String personID, String associatedUsername,
                               String firstName, String lastName, String rootGender, Connection conn) {

        this.numGenerations = numGenerations;
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rootGender = rootGender;
        this.conn = conn;
        this.pDao = new PersonDao(conn);
        eventGenerator = new EventGenerator(associatedUsername, conn);
    }

    /**
     * Generates the family tree based on the provided parameters.
     *
     * @throws DataAccessException if there is an error accessing the data
     */
    public void generateFamilyTree() throws DataAccessException {
        // Call generate person to recursively generate the family tree
        generatePerson(rootGender, numGenerations);
        generatePersonForUser();
        eventGenerator.generateBirthForUser(personID);
    }

    /**
     * Recursively Generates the family tree based on the number of generations
     *
     * @param gender the gender of the person to generate
     * @param generations the number of generations to generate for this person
     * @return the generated person
     * @throws DataAccessException if there is an error accessing the data
     */
    private Person generatePerson(String gender, int generations) throws DataAccessException {
        Person mother = null;
        Person father = null;

        // Recursively generate each person in the tree
        if (generations >= 1) {
            mother = generatePerson("f", generations - 1);
            father = generatePerson("m", generations - 1);

            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());

            eventGenerator.generateMarriageEvents(mother, father);

            userFather = father.getPersonID();
            userMother = mother.getPersonID();

            // As recursion unwinds, insert each person into the person table
            pDao.insertPerson(mother);
            pDao.insertPerson(father);
        }

        // Generate a person and set their information
        Person person = new Person();
        person.setPersonID(generateUniqueID());
        person.setAssociatedUsername(associatedUsername);
        if (mother != null) {
            person.setMotherID(mother.getPersonID());
        }
        if(father != null) {
            person.setFatherID(father.getPersonID());
        }
        person.setGender(gender);

        if (person.getGender().equals("f")) {
            person.setFirstName(generateRandomFemaleName());
        }
        if (person.getGender().equals("m")) {
            person.setFirstName(generateRandomMaleName());
        }

        person.setLastName(generateRandomSurname());

        eventGenerator.generateBirthEvent(person);
        eventGenerator.generateDeathEvent(person);

        return person;
    }

    /**
     * Generates a person for the user and inserts it into the database.
     *
     * @throws DataAccessException if there is an error accessing the data
     */
    private void generatePersonForUser() throws DataAccessException {
        Person user = new Person(personID, associatedUsername, firstName, lastName, rootGender,
                userFather, userMother, null);

        pDao.insertPerson(user);
    }

    /**
     * Generates a random female name.
     *
     * @return a random female name
     */
    private String generateRandomFemaleName() {
        int randomIndex = (int) (Math.random() * femaleNames.getData().length);
        return femaleNames.getData()[randomIndex];
    }

    /**
     * Generates a random male name.
     *
     * @return a random male name
     */
    private String generateRandomMaleName() {
        int randomIndex = (int) (Math.random() * maleNames.getData().length);
        return maleNames.getData()[randomIndex];
    }

    /**
     * Generates a random surname.
     *
     * @return a random surname
     */
    private String generateRandomSurname() {
        int randomIndex = (int) (Math.random() * surnames.getData().length);
        return surnames.getData()[randomIndex];
    }

    /**
     * Generates a unique ID using UUID.
     *
     * @return a unique ID
     */
    private String generateUniqueID() {
        return UUID.randomUUID().toString();
    }
}
