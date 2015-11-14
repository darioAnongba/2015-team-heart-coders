package ch.epfl.sweng.swissaffinity.events;

import java.io.Serializable;
import java.net.URL;

import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Representation of an establishment for an event to take place in.
 */
public class Establishment implements Serializable {

    /**
     * Type of establishment.
     */
    public enum Type implements Serializable {
        BAR("bar"),
        RESTAURANT("restaurant"),
        HOTEL("hotel");

        private final String mType;

        Type(String type) {
            mType = type;
        }

        /**
         * Getter for the string representation of the type.
         *
         * @return its type
         */
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
     *
     * @param id          its unique id.
     * @param name        its name.
     * @param type        its type {@link Type}
     * @param address     its address {@link Address}
     * @param phoneNumber its phone number
     * @param description the description of the establishment.
     * @param url         the URL of the website
     * @param maxSeats    maximum number of seats
     * @param logoPath    the relative path to the logo
     * @param location    its location {@link Location}
     */
    public Establishment(
            int id,
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

    /**
     * Getter for the name.
     *
     * @return its name
     */
    public int getId() {
        return mId;
    }
    /**
     * Getter for the name.
     *
     * @return its name
     */
    public String getName() {
        return mName;
    }

    /**
     * Getter for the logo path.
     *
     * @return the relative path
     */
    public String getLogoPath() {
        return mLogoPath;
    }

    /**
     * Getter for the URL.
     *
     * @return the URL
     */
    public URL getUrl() {
        return mUrl;
    }

    /**
     * Getter for the description.
     *
     * @return the description
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Getter for its address.
     *
     * @return the address
     */
    public Address getAddress() {
        return mAddress;
    }

    /**
     * Getter for the type {@link Type}
     *
     * @return the type
     */
    public Type getType() {
        return mType;
    }
}
