package ch.epfl.sweng.swissaffinity.events;


import ch.epfl.sweng.swissaffinity.utilities.Calendar;

/**
 * Representation of a default event.
 */
public class DefaultEvent implements Event {

    private int mId;
    private String mName;
    private Location mLocation;
    private int mMaxPeople;
    private Calendar mDateBegin;
    private Calendar mDateEnd;
    private double mBasePrice;
    private State mState;
    private String mDescription;
    private String mImagePath;
    private Calendar mLastUpdate;

    /**
     * The constructor of the class
     *
     * @param id          it's unique id
     * @param name        it's name (a good one for title)
     * @param location    it's location {@link Location}
     * @param maxPeople   the max number of people allowed
     * @param dateBegin   the beginning date and time
     * @param dateEnd     the ending date and time
     * @param basePrice   the price
     * @param state       the actual state of the event {@link Event.State}
     * @param description a description of the event
     * @param imagePath   the relative path to an image
     * @param lastUpdate  the last time the event was updated
     */
    public DefaultEvent(
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
            Calendar lastUpdate)
    {
        mId = id;
        mLocation = location;
        mName = name;
        mMaxPeople = maxPeople;
        mDateBegin = dateBegin;
        mDateEnd = dateEnd;
        mBasePrice = basePrice;
        mState = state;
        mDescription = description;
        mImagePath = imagePath;
        mLastUpdate = lastUpdate;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getDesription() {
        return mDescription;
    }

    @Override
    public Calendar getDate() {
        return mDateBegin;
    }

    @Override
    public Location getLocation() {
        return mLocation;
    }
}
