package ch.epfl.sweng.swissaffinity.events;


import java.io.Serializable;
import java.util.Date;

import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Representation of an abstract event.
 */
public abstract class AbstractEvent implements Serializable {

    protected final int mId;
    protected final String mName;
    protected final Location mLocation;
    protected final int mMaxPeople;
    protected final Date mDateBegin;
    protected final Date mDateEnd;
    protected final double mBasePrice;
    protected final Event.State mState;
    protected final String mDescription;
    protected final String mImagePath;
    protected final Date mLastUpdate;

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
    private AbstractEvent(
            int id,
            String name,
            Location location,
            int maxPeople,
            Date dateBegin,
            Date dateEnd,
            double basePrice,
            Event.State state,
            String description,
            String imagePath,
            Date lastUpdate)
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

    protected AbstractEvent(Builder builder) {
        this(
                builder.mId,
                builder.mName,
                builder.mLocation,
                builder.mMaxPeople,
                builder.mDateBegin,
                builder.mDateEnd,
                builder.mBasePrice,
                builder.mState,
                builder.mDescription,
                builder.mImagePath,
                builder.mLastUpdate);
    }

    public static class Builder {
        private int mId;
        private String mName;
        private Location mLocation;
        private int mMaxPeople;
        private Date mDateBegin;
        private Date mDateEnd;
        private double mBasePrice;
        private Event.State mState;
        private String mDescription;
        private String mImagePath;
        private Date mLastUpdate;

        public Builder setId(int id) {
            mId = id;
            return this;
        }

        public Builder setName(String name) {
            mName = name;
            return this;
        }

        public Builder setLocation(String location) {
            mLocation = new Location(location);
            return this;
        }

        public Builder setMaxPeople(int maxPeople) {
            mMaxPeople = maxPeople;
            return this;
        }

        public Builder setDateBegin(Date dateBegin) {
            mDateBegin = dateBegin;
            return this;
        }

        public Builder setDateEnd(Date dateEnd) {
            mDateEnd = dateEnd;
            return this;
        }

        public Builder setBasePrice(double basePrice) {
            mBasePrice = basePrice;
            return this;
        }

        public Builder setState(String state) {
            mState = Event.State.getState(state);
            return this;
        }

        public Builder setDescrition(String descrition) {
            mDescription = descrition;
            return this;
        }

        public Builder setImagePath(String imagePath) {
            mImagePath = imagePath;
            return this;
        }

        public Builder setmLastUpdate(Date lastUpdate) {
            mLastUpdate = lastUpdate;
            return this;
        }
    }
}
