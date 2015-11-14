package ch.epfl.sweng.swissaffinity.events;


import java.io.Serializable;
import java.util.Date;

import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Representation of a basic event.
 */
public abstract class Event implements Serializable {

    /**
     * The possible states of an event.
     */
    public enum State implements Serializable {
        PENDING("pending"),
        CONFIRMED("confirmed"),
        CANCELLED("cancelled");

        private String mState;

        State(String state) {
            mState = state;
        }

        /**
         * Getter for the state of an event.
         *
         * @param state the server API state.
         * @return the state.
         */
        public static State getState(String state) {
            for (State s : State.values()) {
                if (s.mState.equalsIgnoreCase(state)) {
                    return s;
                }
            }
            return null;
        }
    }

    protected final int mId;
    protected final String mName;
    protected final Location mLocation;
    protected final int mMaxPeople;
    protected final Date mDateBegin;
    protected final Date mDateEnd;
    protected final double mBasePrice;
    protected final State mState;
    protected final String mDescription;
    protected final String mImagePath;
    protected final Date mLastUpdate;

    protected Event(Builder builder) {
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
     * Getter for the name
     *
     * @return the name of the event
     */
    public String getName() {
        return mName;
    }

    /**
     * Getter for the description
     *
     * @return the description of the event
     */
    public String getDescription() {
        return mDescription;
    }

    public Date getDateBegin() {
        return mDateBegin;
    }

    /**
     * Getter for the beginning date
     *
     * @return the starting date and time {@link Date}
     */
    public Date getDateEnd() {
        return mDateEnd;
    }

    /**
     * Getter for the location
     *
     * @return the location of the event {@link Location}
     */
    public Location getLocation() {
        return mLocation;
    }

    /**
     * Getter for the image path
     *
     * @return the relative path for the image
     */
    public String getImagePath() {
        return mImagePath;
    }

    /**
     * Getter for the price
     *
     * @return the price of the event
     */
    public double getBasePrice() {
        return mBasePrice;
    }

    /**
     * Getter for the maximum people
     *
     * @return the maximum number of people for the event
     */
    public int getMaxPeople() {
        return mMaxPeople;
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
        private State mState;
        private String mDescription;
        private String mImagePath;
        private Date mLastUpdate;

        /**
         * Setter for ID.
         *
         * @param id
         * @return this
         */
        public Builder setId(int id) {
            mId = id;
            return this;
        }

        /**
         * Setter for name.
         *
         * @param name
         * @return this
         */
        public Builder setName(String name) {
            mName = name;
            return this;
        }

        /**
         * Setter for location.
         *
         * @param location
         * @return this
         */
        public Builder setLocation(Location location) {
            mLocation = location;
            return this;
        }

        /**
         * Setter for maximum number of people.
         *
         * @param maxPeople
         * @return this
         */
        public Builder setMaxPeople(int maxPeople) {
            mMaxPeople = maxPeople;
            return this;
        }

        /**
         * Setter for starting date and time.
         *
         * @param dateBegin
         * @return this
         */
        public Builder setDateBegin(Date dateBegin) {
            mDateBegin = dateBegin;
            return this;
        }

        /**
         * Setter for ending date and time
         *
         * @param dateEnd
         * @return this
         */
        public Builder setDateEnd(Date dateEnd) {
            mDateEnd = dateEnd;
            return this;
        }

        /**
         * Setter for price.
         *
         * @param basePrice
         * @return this
         */
        public Builder setBasePrice(double basePrice) {
            mBasePrice = basePrice;
            return this;
        }

        /**
         * Setter for event state.
         *
         * @param state
         * @return this
         */
        public Builder setState(String state) {
            mState = State.getState(state);
            return this;
        }

        /**
         * Setter for the description
         *
         * @param descrition
         * @return this
         */
        public Builder setDescrition(String descrition) {
            mDescription = descrition;
            return this;
        }

        /**
         * Setter for the image path.
         *
         * @param imagePath
         * @return this
         */
        public Builder setImagePath(String imagePath) {
            mImagePath = imagePath;
            return this;
        }

        /**
         * Setter for the last update date and time.
         *
         * @param lastUpdate
         * @return this
         */
        public Builder setLastUpdate(Date lastUpdate) {
            mLastUpdate = lastUpdate;
            return this;
        }
    }
}
