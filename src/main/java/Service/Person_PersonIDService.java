package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;

import Result.Person_PersonIDResult;
import model.Person;

import java.sql.Connection;
import java.util.Objects;

/**
 * Service class that handles the retrieval of a specific person by ID.
 */
public class Person_PersonIDService {
    /**
     * The Database object.
     */
    private Database db;

    /**
     * The Dao object used for accessing the person data.
     */
    private PersonDao pDao;

    /**
     * The Person object.
     */
    private Person person;

    /**
     * Retrieves a specific person by ID.
     *
     * @return The result containing the person information.
     */
    public Person_PersonIDResult person_personID(String username, String personID) {
        db = new Database();

        try {
            db.openConnection();
            Connection conn = db.getConnection();

            pDao = new PersonDao(conn);

            person = pDao.findByID(personID);

            String user = person.getAssociatedUsername();

            if (Objects.equals(user, username)) {
                Person_PersonIDResult result = new Person_PersonIDResult();

                result.setPersonID(personID);
                result.setAssociatedUsername(username);
                result.setFirstName(person.getFirstName());
                result.setLastName(person.getLastName());
                result.setGender(person.getGender());
                result.setMotherID(person.getMotherID());
                result.setFatherID(person.getFatherID());
                result.setSpouseID(person.getSpouseID());
                result.setSuccess(true);

                db.closeConnection(true);

                return result;
            }
            else {
                db.closeConnection(false);
                Person_PersonIDResult result = new Person_PersonIDResult();

                result.setMessage("Error: Requested person does not belong to this user");
                result.setSuccess(false);

                return result;
            }
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);

            Person_PersonIDResult result = new Person_PersonIDResult();
            result.setMessage("Error: Database Connection failed");
            result.setSuccess(false);

            return result;
        }
    }
}
