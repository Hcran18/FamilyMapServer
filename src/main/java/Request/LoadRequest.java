package Request;

import model.User;
import model.Person;
import model.Event;

/**
 * Represents a request to load data into the application.
 */
public class LoadRequest {
    User[] users;
    Person[] persons;
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
