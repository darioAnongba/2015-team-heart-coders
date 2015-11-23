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
         * Getter for the state of the event
         *
         * @return the server API state
         */
        public String get() {
            return mState;
        }

        /**
         * Getter for the state of an event
         *
         * @param state the server API state
         * @return the corresponding state
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

    private final int mId;
    private final String mName;
    private final Location mLocation;
    private final int mMaxPeople;
    private final Date mDateBegin;
    private final Date mDateEnd;
    private final double mBasePrice;
    private final State mState;
    private final String mDescription;
    private final String mImagePath;
    private final Date mLastUpdate;

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
     * Getter for the ID
     *
     * @return the id
     */
    public String getId() {
        return String.valueOf(mId);
    }

    /**
     * Getter for the name
     *
     * @return the name
     */
    public String getName() {
        return mName;
    }

    /**
     * Getter for the description
     *
     * @return the description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Getter for the beginning date
     *
     * @return the beginning date and time {@link Date}
     */
    public Date getDateBegin() {
        return mDateBegin;
    }

    /**
     * Getter for the ending date
     *
     * @return the ending date and time {@link Date}
     */
    public Date getDateEnd() {
        return mDateEnd;
    }

    /**
     * Getter for the location
     *
     * @return the location {@link Location}
     */
    public Location getLocation() {
        return mLocation;
    }

    /**
     * Getter for the image path
     *
     * @return the relative path of the image
     */
    public String getImagePath() {
        return mImagePath;
    }

    /**
     * Getter for the price
     *
     * @return the price
     */
    public double getBasePrice() {
        return mBasePrice;
    }

    /**
     * Getter for the maximum people
     *
     * @return the maximum number of people
     */
    public int getMaxPeople() {
        return mMaxPeople;
    }

    /**
     * Builder for the event class.
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
         * Setter for ID
         *
         * @param id the ID
         * @return this
         */
        public Builder setId(int id) {
            if (id < 0) {
                throw new IllegalArgumentException();
            }
            mId = id;
            return this;
        }

        /**
         * Setter for name
         *
         * @param name the name
         * @return this
         */
        public Builder setName(String name) {
            if (name == null) {
                throw new IllegalArgumentException();
            }
            mName = name;
            return this;
        }

        /**
         * Setter for location
         *
         * @param location the location {@link Location}
         * @return this
         */
        public Builder setLocation(Location location) {
            if (location == null) {
                throw new IllegalArgumentException();
            }
            mLocation = location.clone();
            return this;
        }

        /**
         * Setter for maximum number of people
         *
         * @param maxPeople the maximum number of people
         * @return this
         */
        public Builder setMaxPeople(int maxPeople) {
            if (maxPeople < 0) {
                throw new IllegalArgumentException();
            }
            mMaxPeople = maxPeople;
            return this;
        }

        /**
         * Setter for the beginning date
         *
         * @param dateBegin the beginning date and time {@link Date}
         * @return this
         */
        public Builder setDateBegin(Date dateBegin) {
            if (dateBegin == null) {
                throw new IllegalArgumentException();
            }
            mDateBegin = dateBegin;
            return this;
        }

        /**
         * Setter for the ending date
         *
         * @param dateEnd the ending date and time {@link Date}
         * @return this
         */
        public Builder setDateEnd(Date dateEnd) {
            if (dateEnd == null) {
                throw new IllegalArgumentException();
            }
            mDateEnd = dateEnd;
            return this;
        }

        /**
         * Setter for price
         *
         * @param basePrice the price
         * @return this
         */
        public Builder setBasePrice(double basePrice) {
            if (basePrice < 0) {
                throw new IllegalArgumentException();
            }
            mBasePrice = basePrice;
            return this;
        }

        /**
         * Setter for event state
         *
         * @param state the state {@link State}
         * @return this
         */
        public Builder setState(String state) {
            if (state == null) {
                throw new IllegalArgumentException();
            }
            mState = State.getState(state);
            return this;
        }

        /**
         * Setter for the description
         *
         * @param description the description
         * @return this
         */
        public Builder setDescription(String description) {
            if (description == null) {
                throw new IllegalArgumentException();
            }
            mDescription = description;
            return this;
        }

        /**
         * Setter for the image path
         *
         * @param imagePath the image relative path
         * @return this
         */
        public Builder setImagePath(String imagePath) {
            if (imagePath == null) {
                throw new IllegalArgumentException();
            }
            mImagePath = imagePath;
            return this;
        }

        /**
         * Setter for the last updated date
         *
         * @param lastUpdate the last updated date and time {@link Date}
         * @return this
         */
        public Builder setLastUpdate(Date lastUpdate) {
            if (lastUpdate == null) {
                throw new IllegalArgumentException();
            }
            mLastUpdate = lastUpdate;
            return this;
        }

        @Override
        public Builder clone(){
            Builder temp =  new Builder();
            temp.setLastUpdate(mLastUpdate);
            temp.setImagePath(mImagePath);
            temp.setId(mId);
            temp.setMaxPeople(mMaxPeople);
            temp.setDescription(mDescription);
            temp.setState(mState.get());
            temp.setLocation(mLocation.clone());
            temp.setBasePrice(mBasePrice);
            temp.setName(mName);
            temp.setDateBegin(mDateBegin);
            temp.setDateEnd(mDateEnd);
            return temp;
        }
    }
}
