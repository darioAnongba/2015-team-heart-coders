package ch.epfl.sweng.swissaffinity.events;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import ch.epfl.sweng.swissaffinity.users.Contributor;

/**
 * The class to represent the speedDating which extends Event
 */
public class SpeedDatingEvent extends AbstractEvent {

    private int mMenSeats;
    private int mWomenSeats;
    private int mMenRegistered;
    private int mWomenRegistered;
    private int mMinAge;
    private int mMaxAge;
    private Establishment mEstablishment;

    /**
     * * @param id it's unique id
     * @param name it's name (a good one for title)
     * @param location it's location {@link Location}
     * @param maxPeople the max number of people allowed
     * @param dateBegin the beginning date and time
     * @param dateEnd the ending date and time
     * @param basePrice the price
     * @param state the actual state of the event {@link Event.State}
     * @param description a description of the event
     * @param imagePath the relative path to an image
     * @param lastUpdate the last time the event was updated
     * @param menSeats number of places for men
     * @param womenSeats number of places for women
     * @param menRegistered number of registered men
     * @param womenRegistered number of registered women
     * @param minAge minimum age to attend the event
     * @param maxAge maximum age to attend the event
     * @param establishment the establishment where the event takes place {@link Establishment}
     */
    public SpeedDatingEvent(
            int id,
            String name,
            Location location,
            int maxPeople,
            Calendar dateBegin,
            Calendar dateEnd,
            double basePrice,
            State state,
            String description,
            String imagePath,
            Calendar lastUpdate,
            int menSeats,
            int womenSeats,
            int menRegistered,
            int womenRegistered,
            int minAge,
            int maxAge,
            Establishment establishment)
    {
        super(id,
              name,
              location,
              maxPeople,
              dateBegin,
              dateEnd,
              basePrice,
              state,
              description,
              imagePath,
              lastUpdate);
        mMenSeats = menSeats;
        mWomenSeats = womenSeats;
        mMenRegistered = menRegistered;
        mWomenRegistered = womenRegistered;
        mMinAge = minAge;
        mMaxAge = maxAge;
        mEstablishment = establishment;
    }
}
