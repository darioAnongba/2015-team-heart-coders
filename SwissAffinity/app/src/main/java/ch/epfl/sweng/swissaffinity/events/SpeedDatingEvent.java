package ch.epfl.sweng.swissaffinity.events;

import java.io.Serializable;
import java.util.Date;

import ch.epfl.sweng.swissaffinity.utilities.Location;


/**
 * Representation of a speed-dating event.
 */
public class SpeedDatingEvent extends AbstractEvent implements Event, Serializable {

    private final int mMenSeats;
    private final int mWomenSeats;
    private final int mMenRegistered;
    private final int mWomenRegistered;
    private final int mMinAge;
    private final int mMaxAge;
    private final Establishment mEstablishment;

    public SpeedDatingEvent(
            AbstractEvent.Builder builder,
            int mMenSeats,
            int mWomenSeats,
            int mMenRegistered,
            int mWomenRegistered,
            int mMinAge,
            int mMaxAge,
            Establishment mEstablishment)
    {
        super(builder);
        this.mMenSeats = mMenSeats;
        this.mWomenSeats = mWomenSeats;
        this.mMenRegistered = mMenRegistered;
        this.mWomenRegistered = mWomenRegistered;
        this.mMinAge = mMinAge;
        this.mMaxAge = mMaxAge;
        this.mEstablishment = mEstablishment;
    }

    private SpeedDatingEvent(AbstractEvent.Builder abstractEventBuilder, Builder builder) {
        this(
                abstractEventBuilder,
                builder.mMenSeats,
                builder.mWomenSeats,
                builder.mMenRegistered,
                builder.mWomenRegistered,
                builder.mMinAge,
                builder.mMaxAge,
                builder.mEstablishment);
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
    public Date getDateBegin() {
        return mDateBegin;
    }

    @Override
    public Date getDateEnd() {
        return mDateEnd;
    }

    @Override
    public Location getLocation() {
        return mLocation;
    }

    @Override
    public String getImagePath() {
        return mImagePath;
    }

    @Override
    public double getBasePrice() {
        return mBasePrice;
    }

    public static class Builder {
        private int mMenSeats;
        private int mWomenSeats;
        private int mMenRegistered;
        private int mWomenRegistered;
        private int mMinAge;
        private int mMaxAge;
        private Establishment mEstablishment;

        public Builder setMenSeats(int menSeats) {
            mMenSeats = menSeats;
            return this;
        }

        public Builder setWomenSeats(int womenSeats) {
            mWomenSeats = womenSeats;
            return this;
        }

        public Builder setMenRegistered(int menRegistered) {
            mMenRegistered = menRegistered;
            return this;
        }

        public Builder setWomenRegistered(int womenRegistered) {
            mWomenRegistered = womenRegistered;
            return this;

        }

        public Builder setMinAge(int minAge) {
            mMinAge = minAge;
            return this;
        }

        public Builder setMaxAge(int maxAge) {
            mMaxAge = maxAge;
            return this;
        }

        public Builder setEstablishment(Establishment establishment) {
            mEstablishment = establishment;
            return this;
        }

        public SpeedDatingEvent build(AbstractEvent.Builder abstractEventBuilder) {
            return new SpeedDatingEvent(abstractEventBuilder, this);
        }
    }
}
