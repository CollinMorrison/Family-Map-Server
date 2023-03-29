package Model;

import java.util.UUID;

public class GenerateGenerations {
    public Person generatePerson(String gender, int generations) {
        Person mother = null;
        Person father = null;

        if (generations > 1) {
            mother = generatePerson("f", generations - 1);
            father = generatePerson("m", generations - 1);
            mother.setSpouseID(father.getPersonID());
            father.setSpouseID(mother.getPersonID());
            //add marriage events to mother and father that are in sync with each other
            generateMarriageEvents(mother, father);
        }
        //create a new person

        //generate events for the person (except marriage)

        //Save person in database

        //return person
        return null;
    }

    private void generateMarriageEvents(Person mother, Person Father) {
        UUID motherUuid = UUID.randomUUID();
        String eventID = motherUuid.toString();
        //int rnd = new Random().nextInt(LocationData.data.length);
        //Location randomLocation = LocationData.data[rnd];
//        Event motherEvent = new Event(
//                eventID,
//                mother.getAssociatedUsername(),
//                mother.getPersonID(),
//                randomLocation.latitude,
//                randomLocation.longitude,
//                randomLocation.country,
//                randomLocation.city,
//                "marriage",
//
//                )
//        Event fatherEvent = new Event(
//
//        )
    }

    private void generateBirthEvent(Person person) {

    }

    private void generateDeathEvent(Person person) {

    }
}
