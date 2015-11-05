package ch.epfl.sweng.swissaffinity.events;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import ch.epfl.sweng.swissaffinity.users.Contributor;

/**
 * The class to represent the speedDating which extends Event
 */
public class SpeedDating extends AbstractEvent {

    private int menSits;
    private int womenSits;
    private int menSitsAvailable;
    private int womenSitsAvailable;
    private int minAge;
    private int maxAge;
    private Establishment establishment;

    /**
     * The constructor for the SpeedDation class
     * @param id the number that represent the SpeedDating
     * @param location the location of SpeedDating
     * @param name the name of the SpeedDating
     * @param maxPeople the maximum number of people allowed to the SpeedDating
     * @param dateBeginning The Calendar date at which the SpeedDating begins
     * @param dateEnd The Calendar date at which the SpeedDating ends
     * @param basePrice the base price of the SpeedDating
     * @param animators the list of Contributor of the SpeedDating
     * @param state the state of the SpeedDating
     * @param description the description of the SpeedDating
     * @param imageEventURl the url of the image for the SpeedDating
     * @param createdAt the Calendar date of the creation of the SpeedDating
     * @param createdBy the Contributor that created the SpeedDating
     * @param menSits the number of place for men
     * @param womenSits the number of place for women
     * @param menSitsAvailable the number of place still available for man
     * @param womenSitsAvailable the number of place still available for women
     * @param minAge the minimum age to particpate to the SpeedDating
     * @param maxAge  the maximum age to participate to the SpeedDating
     * @param establishment the Establishment where will be the SpeedDating
     * @throws IllegalArgumentException if one attributes is empty or null or negative
     */
    public SpeedDating(int id,
                       Location location,
                       String name,
                       int maxPeople,
                       Calendar dateBeginning,
                       Calendar dateEnd,
                       int basePrice,
                       ArrayList<Contributor> animators,
                       State state,
                       String description,
                       String imageEventURl,
                       Calendar createdAt,
                       Contributor createdBy,
                       int menSits,
                       int womenSits,
                       int menSitsAvailable,
                       int womenSitsAvailable,
                       int minAge,
                       int maxAge,
                       Establishment establishment) throws IllegalArgumentException{

        super(id, location, name, maxPeople, dateBeginning, dateEnd, basePrice, animators, state, description, imageEventURl, createdAt, createdBy);
        if(menSits<1 || womenSits <1 || menSitsAvailable<1 || womenSitsAvailable<1 || womenSitsAvailable>womenSits||menSitsAvailable>menSits|| minAge<5 || maxAge>120 || establishment==null) {
            throw new IllegalArgumentException("one of the argumet is not correct");
        }
        this.menSits = menSits;
        this.womenSits = womenSits;
        this.menSitsAvailable = menSitsAvailable;
        this.womenSitsAvailable = womenSitsAvailable;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.establishment = establishment;
    }


    /**
     * The getter of the number of man's place
     * @return the number of the man's place
     */
    public int getMenSits() {
        return menSits;
    }

    /**
     * The setter of the number of the man's place
     * @param menSits The new number of men's place
     * @throws IllegalArgumentException if the number of men's sit is <1
     */
    public void setMenSits(int menSits) throws IllegalArgumentException{
        if(menSits<1) {
            throw new IllegalArgumentException("The number of sit for men is no good" + menSits);
        }
        this.menSits = menSits;
    }

    /**
     * The getter of the number of women's place
     * @return the number of women's place
     */
    public int getWomenSits() {
        return womenSits;
    }

    /**
     * The setter of the number of women's sits
     * @param womenSits the new number of women's sits
     * @throws IllegalArgumentException if the number is < 1
     */
    public void setWomenSits(int womenSits) throws IllegalArgumentException {
        if(womenSits<1) {
            throw new IllegalArgumentException("The number of sits for woment is not good" + womenSits);
        }
        this.womenSits = womenSits;
    }

    /**
     * The getter of the number of men's sits available
     * @return the number of men's sits available
     */
    public int getMenSitsAvailable() {
        return menSitsAvailable;
    }

    /**
     * The setter of the number of men's sits available
     * @param menSitsAvailable the new number of men's sits available
     * @throws IllegalArgumentException if the number is <0 or > menSits
     */
    public void setMenSitsAvailable(int menSitsAvailable) throws IllegalArgumentException {
        if(menSitsAvailable<0 || menSitsAvailable>menSits) {
            throw new IllegalArgumentException("The number os negative or moire than the number of place"+menSitsAvailable);
        }
        this.menSitsAvailable = menSitsAvailable;
    }

    /**
     * The getter of the number of women's sits available
     * @return the number of women's sits available
     */
    public int getWomenSitsAvailable() {
        return womenSitsAvailable;
    }

    /**
     * The setter of the number of women's sits available
     * @param womenSitsAvailable the new number of sit's for women available
     * @throws IllegalArgumentException it he number is <0 or >womenSits
     */
    public void setWomenSitsAvailable(int womenSitsAvailable) throws IllegalArgumentException {
        if(womenSitsAvailable<0 || menSitsAvailable>menSits) {
            throw new IllegalArgumentException("the number is negative or too big " + womenSitsAvailable);
        }
        this.womenSitsAvailable = womenSitsAvailable;
    }

    /**
     * The getter of the minimum age for the SpeedDating
     * @return the minimum age fot the speedDating
     */
    public int getMinAge() {
        return minAge;
    }

    /**
     * The setter of the minimum age for the speedDating
     * @param minAge the new age minimum for the speedDating
     * @throws IllegalArgumentException if the number is < 5
     */
    public void setMinAge(int minAge)throws IllegalArgumentException {
        if(minAge<5 ||minAge>100) {
            throw new IllegalArgumentException("The age is too small or too big"+ minAge);
        }
        this.minAge = minAge;
    }

    /**
     * The getter for the maximum age for the speedDating
     * @return the maximum age to participate to the speedDating
     */
    public int getMaxAge() {
        return maxAge;
    }

    /**
     * The setter for the maximum age for the speedDating
     * @param maxAge
     * @throws IllegalArgumentException if the number is too big or too small
     */
    public void setMaxAge(int maxAge) throws IllegalArgumentException {
        if(maxAge<5 || maxAge>120) {
            throw new IllegalArgumentException("the number is too bug or too small" + maxAge);
        }
        this.maxAge = maxAge;
    }

    /**
     * The getter for the establisment of the SpeedDating
     * @return the estalisment of the SpeedDating
     */
    public Establishment getEstablishment() {
        return establishment;
    }

    /**
     * The setter for the establisment of the SpeedDating
     * @param establishment the new establisment of the SpeedDating
     * @throws IllegalArgumentException if the new establisment is null
     */
    public void setEstablishment(Establishment establishment) throws IllegalArgumentException{
        if(establishment==null) {
            throw new IllegalArgumentException("The Establishment is null" + establishment);
        }
        this.establishment = establishment;
    }



}
