package Generation;

import DataAccess.DataAccessException;
import DataAccess.PersonDao;
import FakeFamilyData.*;
import Generation.EventGenerator;
import model.Person;

import java.sql.Connection;
import java.util.UUID;

public class FamilyTreeGenerator {
    private int numGenerations;
    private MNamesData maleNames = Cache.getMaleNames();
    private FNamesData femaleNames = Cache.getFemaleNames();
    private SNamesData surnames = Cache.getSurnames();
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private String rootGender;
    private String userFather;
    private String userMother;
    private Connection conn;
    private PersonDao pDao;
    private EventGenerator eventGenerator;

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

    public void generateFamilyTree() throws DataAccessException {
        // Call generate person to recursively generate the family tree
        generatePerson(rootGender, numGenerations);
        generatePersonForUser();
        eventGenerator.generateMarriageForUser(personID);
    }

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

    private void generatePersonForUser() throws DataAccessException {
        Person user = new Person(personID, associatedUsername, firstName, lastName, rootGender,
                userFather, userMother, null);

        pDao.insertPerson(user);
    }

    private String generateRandomFemaleName() {
        int randomIndex = (int) (Math.random() * femaleNames.getData().length);
        return femaleNames.getData()[randomIndex];
    }

    private String generateRandomMaleName() {
        int randomIndex = (int) (Math.random() * maleNames.getData().length);
        return maleNames.getData()[randomIndex];
    }

    private String generateRandomSurname() {
        int randomIndex = (int) (Math.random() * surnames.getData().length);
        return surnames.getData()[randomIndex];
    }

    private String generateUniqueID() {
        return UUID.randomUUID().toString();
    }
}
