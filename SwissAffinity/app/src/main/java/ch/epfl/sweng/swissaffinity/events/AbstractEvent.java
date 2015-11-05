package ch.epfl.sweng.swissaffinity.events;


import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import ch.epfl.sweng.swissaffinity.users.Contributor;

/**
 * The Abstract Class to represent an event in general
 */
public abstract class AbstractEvent implements Event {

    private int id;
    private Location location;
    private String name;
    private int maxPeople;
    private Calendar dateBeginning;
    private Calendar dateEnd;
    private int basePrice;
    private Collection<Contributor> animators;
    private State state;
    private String description;
    private String imagePath;
    private Calendar createdAt;
    private Contributor createdBy;

    /**
     * The constructor of the AbstractEvent
     *
     * @param id            the number that represent the event
     * @param location      the location of the event ( by a name of a city)
     * @param name          the name of the event
     * @param maxPeople     the maximum number of people for the event
     * @param dateBeginning the Calendar date of the beginning of the event
     * @param dateEnd       the Calendar date of the ending of the event
     * @param basePrice     the base price for the participation to the event
     * @param animators     the contributor's list for the event
     * @param state         the state of the event ( Enumeration )
     * @param description   the description of the event
     * @param imagePath     the image's url of the event
     * @param createdAt     the Calendar date for the creation of the event
     * @param createdBy     the Contributor that has created the event
     */
    public AbstractEvent(int id,
                         Location location,
                         String name,
                         int maxPeople,
                         Calendar dateBeginning,
                         Calendar dateEnd,
                         int basePrice,
                         Collection<Contributor> animators,
                         State state,
                         String description,
                         String imagePath,
                         Calendar createdAt,
                         Contributor createdBy) {

        this.id = id;
        this.location = location;
        this.name = name;
        this.maxPeople = maxPeople;
        this.dateBeginning = dateBeginning;
        this.dateEnd = dateEnd;
        this.basePrice = basePrice;
        this.animators = animators;
        this.state = state;
        this.description = description;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        //checkParameters();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesription() {
        return description;
    }

    @Override
    public Calendar getDate() {
        return dateBeginning;
    }

    @Override
    public Location getLocation() {
        return location;
    }


    /**
     * check if all the parameters are valid for the event
     *
     * @throws IllegalArgumentException if one of the argument is null(for object)
     *                                  or negative (for int) or empty(for string and list)
     */
    private void checkParameters() throws IllegalArgumentException {
        if (id < 0 || id == 0 || name.isEmpty() || maxPeople == 0 || location == null
                || maxPeople < 1 || basePrice < 0 || animators.isEmpty()
                || imagePath == null || createdBy == null || createdAt.after(dateBeginning)
                || createdAt.after(dateEnd) || dateBeginning.after(dateEnd)) {
            throw new IllegalArgumentException("One of the argument is empty , null or equal to 0");
        }
    }
}
