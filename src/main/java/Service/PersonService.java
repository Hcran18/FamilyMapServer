package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Result.PersonResult;
import model.Person;

import java.sql.Connection;
import java.util.Objects;

/**
 * Service class that handles the retrieval of person data.
 */
public class PersonService {
    private Database db;
    private PersonDao pDao;
    private Person[] persons;

    /**
     * Retrieves a list of persons.
     *
     * @return The result containing the list of persons.
     */
    public PersonResult persons(String username) {
        db = new Database();

        try {
            db.openConnection();
            Connection conn = db.getConnection();

            pDao = new PersonDao(conn);

            persons = pDao.findAllForUser(username);

            PersonResult result = new PersonResult();

            result.setData(persons);
            result.setSuccess(true);

            return result;
        }
        catch (DataAccessException e) {
            e.printStackTrace();

            db.closeConnection(false);

            PersonResult result = new PersonResult();
            result.setMessage("Error: Database Connection failed");
            result.setSuccess(false);

            return result;
        }
    }
}
