package ch.epfl.sweng.swissaffinity.events;


import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import ch.epfl.sweng.swissaffinity.users.*;

/**
 * Tbe Abstract Class to represent an event in general
 */

public abstract class Event {

    private int id;
    private Location location;
    private String name;
    private int maxPeople;
    private Calendar dateBeginning;
    private Calendar dateEnd;
    private int basePrice;
    private ArrayList<Contributor> animators;

    protected enum State {PENDING, CONFIRMED, CANCELLED}

    ;
    private State state;
    private String description;
    private URL imageEventURl;
    private Calendar createdAt;
    private Contributor createdBy;


    /**
     * The constructor of the Event
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
     * @param imageEventURl the image's url of the event
     * @param createdAt     the Calendar date for the creation of the event
     * @param createdBy     the Contributor that has created the event
     */
    public Event(int id,
                 Location location,
                 String name,
                 int maxPeople,
                 Calendar dateBeginning,
                 Calendar dateEnd,
                 int basePrice,
                 ArrayList<Contributor> animators,
                 State state,
                 String description,
                 URL imageEventURl,
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
        this.imageEventURl = imageEventURl;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        //checkParameters();
    }


    /**
     * The getter of the creator of the event
     *
     * @return the Contributor that created the event
     */
    public Contributor getCreatedBy() {
        return createdBy;
    }

    /**
     * The setter of the creator of the event
     *
     * @param createdBy the Contributor that has created the event
     */
    public void setCreatedBy(Contributor createdBy) {
        this.createdBy = createdBy;
        // checkParameters();
    }

    /**
     * The id's getter of the event
     *
     * @return the event's id
     */
    public int getId() {
        return id;
    }

    /**
     * The id's setter for the event
     *
     * @param id the new if for the event
     */
    public void setId(int id) {
        this.id = id;
        //   checkParameters();
    }

    /**
     * The location's getter of the event
     *
     * @return the location of the event
     */
    public Location getLocation() {
        return location;
    }

    /***
     * The location's setter of the event
     *
     * @param location the new location's event
     */
    public void setLocation(Location location) {
        this.location = location;
        //checkParameters();
    }

    /**
     * The getter for the name of the event
     *
     * @return the name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for the name of the event
     *
     * @param name the new name of the event
     */
    public void setName(String name) {
        this.name = name;
        //checkParameters();
    }

    /**
     * The getter of the maximum number of people for the event
     *
     * @return the maximum number of participant to an event
     */
    public int getMaxPeople() {
        return maxPeople;
    }

    /**
     * The setter of the maximum number of people for the event
     *
     * @param maxPeople the new maximum number of participant
     */
    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
        //checkParameters();
    }

    /**
     * The getter of the Date of the event
     *
     * @return The date of the event
     */
    public Calendar getDateBeginning() {
        return dateBeginning;
    }

    /**
     * The setter for the Date of the event
     *
     * @param dateBeginning the new Date for the event
     */
    public void setDateBeginning(Calendar dateBeginning) {
        this.dateBeginning = dateBeginning;
        //checkParameters();
    }

    /**
     * The getter for the ending of the event
     *
     * @return the ending date and hour of the event (Calendar)
     */
    public Calendar getDateEnd() {
        return dateEnd;
    }

    /**
     * The setter for the ending of the event
     *
     * @param dateEnd the new ending date and hour of the event (Calendar)
     */
    public void setDateEnd(Calendar dateEnd) {
        this.dateEnd = dateEnd;
        //checkParameters();
    }

    /**
     * The getter for the price of the event
     *
     * @return the base price of the event
     */
    public int getBasePrice() {
        return basePrice;
    }

    /**
     * The setter for the price of the event
     *
     * @param basePrice the new price for the event
     */
    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
        //checkParameters();
    }

    /**
     * The getter for the contributors of the event
     *
     * @return The contributors of the event
     */
    public ArrayList<Contributor> getAnimators() {
        return animators;
    }

    /**
     * The setter for the contributors of the event
     *
     * @param animators the new list of the event's contributors
     */
    public void setAnimators(ArrayList<Contributor> animators) {
        this.animators = animators;
        //checkParameters();
    }

    /**
     * The getter for the state of the event ( has he attained the good number of peopleto be checked as valid)
     *
     * @return the state of the event
     */
    public State isState() {
        return state;
    }

    /**
     * The setter for the event's state
     *
     * @param state the new event's state
     */
    public void setState(State state) {
        this.state = state;
        //checkParameters();
    }

    /**
     * The getter for the event's description
     *
     * @return the descrition of the event
     */
    public String getDescription() {
        return description;
    }

    /**
     * The setter for the event's description
     *
     * @param description the new event's description
     */
    public void setDescription(String description) {
        this.description = description;
        //checkParameters();
    }

    /**
     * The getter for the image's Url of the event
     *
     * @return
     */
    public URL getImageEventURl() {
        return imageEventURl;
    }

    /**
     * The setter for the image's Url of the event
     *
     * @param imageEventURl the new image's Url for the event
     */
    public void setImageEventURl(URL imageEventURl) {
        this.imageEventURl = imageEventURl;
        //checkParameters();
    }

    /**
     * The getter for the creation's date of the event
     *
     * @return the new creation date for the event
     */
    public Calendar getCreatedAt() {
        return createdAt;
    }

    /**
     * The setter for the creation's date of the event
     *
     * @param createdAt the new creation date's of the event
     */
    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
        //checkParameters();
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
                || imageEventURl == null || createdBy == null || createdAt.after(dateBeginning)
                || createdAt.after(dateEnd) || dateBeginning.after(getDateEnd())) {
            throw new IllegalArgumentException("One of the argument is empty , null or equal to 0");
        }
    }
}
