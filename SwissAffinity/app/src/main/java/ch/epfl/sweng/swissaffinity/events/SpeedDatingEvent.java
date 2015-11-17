package ch.epfl.sweng.swissaffinity.events;

import java.io.Serializable;


/**
 * Representation of a speed dating event.
 */
public class SpeedDatingEvent extends Event implements Serializable {

    private final int mMenSeats;
    private final int mWomenSeats;
    private final int mMenRegistered;
    private final int mWomenRegistered;
    private final int mMinAge;
    private final int mMaxAge;
    private final Establishment mEstablishment;

    private SpeedDatingEvent(Builder builder) {
        super(builder);
        this.mMenSeats = builder.mMenSeats;
        this.mWomenSeats = builder.mWomenSeats;
        this.mMenRegistered = builder.mMenRegistered;
        this.mWomenRegistered = builder.mWomenRegistered;
        this.mMinAge = builder.mMinAge;
        this.mMaxAge = builder.mMaxAge;
        this.mEstablishment = builder.mEstablishment;
    }

    /**
     * Getter for men seats
     *
     * @return the number of men seats
     */
    public int getMenSeats() {
        return mMenSeats;
    }

    /**
     * Getter for women seats
     *
     * @return the number of women seats
     */
    public int getWomenSeats() {
        return mWomenSeats;
    }

    /**
     * Getter for the regidtered men
     *
     * @return the number of registered men
     */
    public int getMenRegistered() {
        return mMenRegistered;
    }

    /**
     * Getter for the registered women
     *
     * @return the number of registered women
     */
    public int getWomenRegistered() {
        return mWomenRegistered;
    }

    /**
     * Builder for a speed dating event.
     */
    public static class Builder extends Event.Builder {

        private int mMenSeats;
        private int mWomenSeats;
        private int mMenRegistered;
        private int mWomenRegistered;
        private int mMinAge;
        private int mMaxAge;
        private Establishment mEstablishment;

        /**
         * Setter for men seats
         *
         * @param menSeats the number of men seats
         *
         * @return this
         */
        public Builder setMenSeats(int menSeats) {
            mMenSeats = menSeats;
            return this;
        }

        /**
         * Setter for women seats
         *
         * @param womenSeats the number of women seats
         *
         * @return this
         */
        public Builder setWomenSeats(int womenSeats) {
            mWomenSeats = womenSeats;
            return this;
        }

        /**
         * Setter for registered men
         *
         * @param menRegistered the number of registered men
         *
         * @return this
         */
        public Builder setMenRegistered(int menRegistered) {
            mMenRegistered = menRegistered;
            return this;
        }

        /**
         * Setter for registered women
         *
         * @param womenRegistered the number of registered women
         *
         * @return this
         */
        public Builder setWomenRegistered(int womenRegistered) {
            mWomenRegistered = womenRegistered;
            return this;

        }

        /**
         * Setter for minimum age
         *
         * @param minAge the minimum age
         *
         * @return this
         */
        public Builder setMinAge(int minAge) {
            mMinAge = minAge;
            return this;
        }

        /**
         * Setter for maximum age
         *
         * @param maxAge the maximum age
         *
         * @return this
         */
        public Builder setMaxAge(int maxAge) {
            mMaxAge = maxAge;
            return this;
        }

        /**
         * Setter for establishment
         *
         * @param establishment the establishment {@link Establishment}
         *
         * @return this
         */
        public Builder setEstablishment(Establishment establishment) {
            mEstablishment = establishment;
            return this;
        }

        /**
         * Build the speed dating event
         *
         * @return the speed dating event {@link SpeedDatingEvent}
         */
        public SpeedDatingEvent build() {
            return new SpeedDatingEvent(this);
        }
    }
}
