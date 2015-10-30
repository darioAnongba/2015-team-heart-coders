package ch.epfl.sweng.swissaffinity.events;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import ch.epfl.sweng.swissaffinity.users.Contributor;

/**
 * Created by Joel on 10/28/2015.
 * A placeholder for default event constructor.
 * Event Cannot be instantiated, and server api is not finished.
 */
public class DefaultEvent extends Event {
    /**
     * The constructor of the Event
     *
     * @param id          the number that represent the event
     * @param location    the location of the event ( by a name of a city)
     * @param name        the name of the event
     * @param maxPeople   the maximum number of people for the event
     * @param dateStart   the Calendar date of the beginning of the event
     * @param dateEnd     the Calendar date of the ending of the event
     * @param basePrice   the base price for the participation to the event
     * @param animators   the contributor's list for the event
     * @param status
     * @param description the description of the event
     * @param imageURl    the image's url of the event
     * @param createdAt   the Calendar date for the creation of the event
     */
    public DefaultEvent(int id,
                        Location location,
                        String name,
                        int maxPeople,
                        Calendar dateStart,
                        Calendar dateEnd,
                        int basePrice,
                        ArrayList<Contributor> animators,
                        Event.State status,
                        String description,
                        URL imageURl,
                        Calendar createdAt) {
        super(id, location, name, maxPeople, dateStart, dateEnd, basePrice, animators, status, description, imageURl, createdAt, null);
    }
}
