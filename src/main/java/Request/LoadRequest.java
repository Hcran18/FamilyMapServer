package Request;

import model.User;
import model.Person;
import model.Event;

/**
 * Represents a request to load data.
 */
public class LoadRequest {
    /**
     * An array of User objects.
     */
    User[] users;

    /**
     * An array of Person objects.
     */
    Person[] persons;

    /**
     * An array of Event objects.
     */
    Event[] events;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
