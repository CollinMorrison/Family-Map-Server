package Model;

import DataAccess.DataAccessException;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import Serializer.*;

import java.sql.Connection;
import java.util.Random;
import java.util.UUID;

public class GenerateGenerations {
    private Connection conn;
    private int numPeople;
    private int numEvents;
    public GenerateGenerations (Connection conn) {
        this.conn = conn;
        this.numPeople = 0;
        this.numEvents = 0;
    }

    public Person generatePerson(String gender, int generations, String username, String firstName, String lastName, int referenceYear, String personID, int maxGenerations) throws DataAccessException {
        Serializer serializer = new Serializer();
        Location[] locations = serializer.loadLocations();
        String[] firstNames = serializer.loadFNames();
        String[] middleNames = serializer.loadMNames();
        String[] lastNames = serializer.loadSNames();
        Person mother = null;
        Person father = null;

        if (generations >= 1) {
            mother = generatePerson("f", generations - 1, username, null, null, referenceYear - 13, null, maxGenerations);
            father = generatePerson("m", generations - 1, username, null, null, referenceYear - 13, null, maxGenerations);
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            //Save person in database
            PersonDao personDao = new PersonDao(this.conn);
            personDao.Insert(mother);
            ++numPeople;
            personDao.Insert(father);
            ++numPeople;
            //add marriage events to mother and father that are in sync with each other
            generateMarriageEvents(mother, father, locations, referenceYear - 13);
            if (generations != maxGenerations) {
                //create a new person that is not at the end of the tree
                UUID newPersonUUID = UUID.randomUUID();
                String newPersonID = newPersonUUID.toString();
                int randomFirstNameInt = new Random().nextInt(firstNames.length);
                String randomFirstName = firstNames[randomFirstNameInt];
                int randomLastNameInt = new Random().nextInt(lastNames.length);
                String randomLastName = lastNames[randomLastNameInt];
                Person newPerson = new Person(
                        newPersonID,
                        username,
                        randomFirstName,
                        randomLastName,
                        father.getPersonID(),
                        mother.getPersonID(),
                        null, // Spouse ID, generated later when a marriage event is added
                        gender
                );
                //generate events for the person (except marriage) and save them in database
                generateBirthEvent(newPerson, referenceYear - 13, locations);
                generateDeathEvent(newPerson, referenceYear + 1, locations);
                //return person
                return newPerson;
            } else {
                // create the person that corresponds to the user
                Person newPerson = new Person(
                        personID,
                        username,
                        firstName,
                        lastName,
                        father.getPersonID(),
                        mother.getPersonID(),
                        null,
                        gender
                );
                //generate events for the person (except marriage) and save them in database
                generateBirthEvent(newPerson, referenceYear - 13, locations);
                generateDeathEvent(newPerson, referenceYear + 1, locations);
                personDao.Insert(newPerson);
                ++numPeople;
                return newPerson;
            }
        } else {
            //create a new person that is at the end of the tree
            // set the reference year to
            UUID newPersonUUID = UUID.randomUUID();
            String newPersonID = newPersonUUID.toString();
            int randomFirstNameInt = new Random().nextInt(firstNames.length);
            String randomFirstName = firstNames[randomFirstNameInt];
            int randomLastNameInt = new Random().nextInt(lastNames.length);
            String randomLastName = lastNames[randomLastNameInt];
            Person newPerson = new Person(
                    newPersonID,
                    username,
                    randomFirstName,
                    randomLastName,
                    null,
                    null,
                    null, // Spouse ID, generated later when a marriage event is added
                    gender
            );
            //generate events for the person (except marriage) and save them in database
            generateBirthEvent(newPerson, referenceYear - 13, locations);
            generateDeathEvent(newPerson, referenceYear + 1, locations);
            //return person
            return newPerson;
        }
    }

    private void generateMarriageEvents(Person mother, Person father, Location[] locations, int marriageYear) throws DataAccessException {
        UUID motherUuid = UUID.randomUUID();
        String eventID = motherUuid.toString();
        int rnd = new Random().nextInt(locations.length);
        Location randomLocation = locations[rnd];
        // Construct events
        Event motherEvent = new Event(
                eventID,
                mother.getAssociatedUsername(),
                mother.getPersonID(),
                randomLocation.latitude,
                randomLocation.longitude,
                randomLocation.country,
                randomLocation.city,
                "marriage",
                 marriageYear
                );
        UUID fatherUuid = UUID.randomUUID();
        String fatherEventID = fatherUuid.toString();
        Event fatherEvent = new Event(
            fatherEventID,
                father.getAssociatedUsername(),
                father.getPersonID(),
                randomLocation.latitude,
                randomLocation.longitude,
                randomLocation.country,
                randomLocation.city,
                "marriage",
                marriageYear
        );
        // Add events to the database
        EventDao eventDao = new EventDao(this.conn);
        eventDao.insert(motherEvent);
        ++numEvents;
        eventDao.insert(fatherEvent);
        ++numEvents;
    }

    private void generateBirthEvent(Person person, int referenceYear, Location[] locations) throws DataAccessException {
        UUID eventUuid = UUID.randomUUID();
        String eventID = eventUuid.toString();
        int rnd = new Random().nextInt(locations.length);
        Location randomLocation = locations[rnd];
        Event birthEvent = new Event(
            eventID,
                person.getAssociatedUsername(),
                person.getPersonID(),
                randomLocation.latitude,
                randomLocation.longitude,
                randomLocation.country,
                randomLocation.city,
                "birth",
                referenceYear
        );
        // Add event to the database
        EventDao eventDao = new EventDao(this.conn);
        eventDao.insert(birthEvent);
        ++numEvents;
    }

    private void generateDeathEvent(Person person, int referenceYear, Location[] locations) throws DataAccessException {
        UUID eventUuid = UUID.randomUUID();
        String eventID = eventUuid.toString();
        int rnd = new Random().nextInt(locations.length);
        Location randomLocation = locations[rnd];
        Event deathEvent = new Event(
                eventID,
                person.getAssociatedUsername(),
                person.getPersonID(),
                randomLocation.latitude,
                randomLocation.longitude,
                randomLocation.country,
                randomLocation.city,
                "death",
                referenceYear
        );
        // Add event to the database
        EventDao eventDao = new EventDao(this.conn);
        eventDao.insert(deathEvent);
        ++numEvents;
    }

    public int getNumEvents() {
        return numEvents;
    }
    public int getNumPeople() {
        return numPeople;
    }
}
