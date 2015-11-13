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

    protected AbstractEvent(Builder builder) {
        mId = builder.mId;
        mLocation = builder.mLocation;
        mName = builder.mName;
        mMaxPeople = builder.mMaxPeople;
        mDateBegin = builder.mDateBegin;
        mDateEnd = builder.mDateEnd;
        mBasePrice = builder.mBasePrice;
        mState = builder.mState;
        mDescription = builder.mDescription;
        mImagePath = builder.mImagePath;
        mLastUpdate = builder.mLastUpdate;
    }

    /**
     * Builder for the abstract event class.
     */
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

        /**
         * Setter for ID.
         * @param id
         * @return this
         */
        public Builder setId(int id) {
            mId = id;
            return this;
        }

        /**
         * Setter for name.
         * @param name
         * @return this
         */
        public Builder setName(String name) {
            mName = name;
            return this;
        }

        /**
         * Setter for location.
         * @param location
         * @return this
         */
        public Builder setLocation(Location location) {
            mLocation = location;
            return this;
        }

        /**
         * Setter for maximum number of people.
         * @param maxPeople
         * @return this
         */
        public Builder setMaxPeople(int maxPeople) {
            mMaxPeople = maxPeople;
            return this;
        }

        /**
         * Setter for starting date and time.
         * @param dateBegin
         * @return this
         */
        public Builder setDateBegin(Date dateBegin) {
            mDateBegin = dateBegin;
            return this;
        }

        /**
         * Setter for ending date and time
         * @param dateEnd
         * @return this
         */
        public Builder setDateEnd(Date dateEnd) {
            mDateEnd = dateEnd;
            return this;
        }

        /**
         * Setter for price.
         * @param basePrice
         * @return this
         */
        public Builder setBasePrice(double basePrice) {
            mBasePrice = basePrice;
            return this;
        }

        /**
         *
         * @param state
         * @return
         */
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

        public Builder setLastUpdate(Date lastUpdate) {
            mLastUpdate = lastUpdate;
            return this;
        }
    }
}
