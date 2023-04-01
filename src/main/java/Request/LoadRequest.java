package Request;

import Model.Event;
import Model.Person;
import Model.User;

/**
 * Loads the user, person, and event data from the body into the database
 */
public class LoadRequest {
    /**
     * users to be loaded into the database
     */
    private User[] users;
    /**
     * persons to be loaded into the database
     */
    private Person[] persons;
    /**
     * events to be loaded into the database
     */
    private Event[] events;

    /**
     * Constructor for the load request
     * @param users
     * @param persons
     * @param events
     */
    public LoadRequest (User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public Event[] getEvents() {
        return events;
    }
}
