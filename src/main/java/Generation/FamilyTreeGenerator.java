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
    private String rootGender;
    private String associatedUsername;
    private Connection conn;
    private PersonDao pDao;
    private EventGenerator eventGenerator;

    public FamilyTreeGenerator(int numGenerations, String rootGender, String associatedUsername, Connection conn) {

        this.numGenerations = numGenerations;
        this.rootGender = rootGender;
        this.associatedUsername = associatedUsername;
        this.conn = conn;
        this.pDao = new PersonDao(conn);
        eventGenerator = new EventGenerator(associatedUsername, conn);
    }

    public void generateFamilyTree() throws DataAccessException {
        // Call generate person to recursively generate the family tree
        generatePerson(rootGender, numGenerations);
    }

    private Person generatePerson(String gender, int generations) {
        Person mother = null;
        Person father = null;

        // Recursively generate each person in the tree
        if (generations >= 1) {
            mother = generatePerson("f", generations - 1);
            father = generatePerson("m", generations - 1);

            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());

            eventGenerator.generateMarriageEvents(mother, father);

            // As recursion unwinds, insert each person into the person table
            try {
                pDao.insertPerson(mother);
                pDao.insertPerson(father);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
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
