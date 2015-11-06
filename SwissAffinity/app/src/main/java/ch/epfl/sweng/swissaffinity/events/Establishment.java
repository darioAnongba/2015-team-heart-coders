package ch.epfl.sweng.swissaffinity.events;

import java.net.URL;

import ch.epfl.sweng.swissaffinity.utils.Address;

/**
 * Representation of an establishment for an event to take place in.
 */
public class Establishment {

    public enum Type {
        BAR("bar"),
        RESTAURANT("restaurant"),
        HOTEL("hotel");

        private final String mType;

        Type(String type) {
            mType = type;
        }

        public String get() {
            return mType;
        }
    }

    private final int mId;
    private final String mName;
    private final Type mType;
    private final Address mAddress;
    private final String mPhoneNumber;
    private final String mDescription; // nullable
    private final URL mUrl; // nullable
    private final int mMaxSeats; // nullable
    private final String mLogoPath; // nullable
    private final Location mLocation;

    /**
     * Constructor for an establishment.
     * @param id its unique id.
     * @param name its name.
     * @param type its type {@link Type}
     * @param address its address {@link Address}
     * @param phoneNumber its phone number
     * @param description the description of the establishment.
     * @param url the URL of the website
     * @param maxSeats maximum number of seats
     * @param logoPath the relative path to the logo
     * @param location its location {@link Location}
     */
    public Establishment(int id,
                         String name,
                         Type type,
                         Address address,
                         String phoneNumber,
                         String description,
                         URL url,
                         int maxSeats,
                         String logoPath,
                         Location location)
    {
        mId = id;
        mName = name;
        mType = type;
        mAddress = address;
        mPhoneNumber = phoneNumber;
        mDescription = description;
        mUrl = url;
        mMaxSeats = maxSeats;
        mLogoPath = logoPath;
        mLocation = location;
    }

    public String getmName() {
        return mName;
    }

    public String getmLogoPath() {
        return mLogoPath;
    }

    public URL getmUrl() {
        return mUrl;
    }

    public String getmDescription() {
        return mDescription;
    }

    public Address getmAddress() {
        return mAddress;
    }

    public Type getmType() {
        return mType;
    }
}
