package ch.epfl.sweng.swissaffinity.events;

import java.io.Serializable;

import ch.epfl.sweng.swissaffinity.utilities.Address;

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
         * Getter for the server API representation of a type
         *
         * @return its server API type representation
         */
        public String get() {
            return mType;
        }

        /**
         * Getter for the type of an establishment
         *
         * @param type the server API type
         *
         * @return the corresponding type
         */
        public static Type getType(String type) {
            for (Type t : Type.values()) {
                if (t.mType.equalsIgnoreCase(type)) {
                    return t;
                }
            }
            return null;
        }
    }

    private final int mId;
    private final String mName;
    private final Type mType;
    private final Address mAddress;
    private final String mPhoneNumber;
    private final String mDescription; // nullable
    private final String mUrl; // nullable
    private final int mMaxSeats; // nullable
    private final String mLogoPath; // nullable

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
     */
    public Establishment(
            int id,
            String name,
            Type type,
            Address address,
            String phoneNumber,
            String description,
            String url,
            int maxSeats,
            String logoPath)
    {
        if (id < 0 || name == null || type == null || address == null || phoneNumber == null ||
            description == null || url == null || logoPath == null)
        {
            throw new IllegalArgumentException();
        }
        mId = id;
        mName = name;
        mType = type;
        mAddress = address;
        mPhoneNumber = phoneNumber;
        mDescription = description;
        mUrl = url;
        mMaxSeats = maxSeats;
        mLogoPath = logoPath;
    }

    /**
     * Getter for the ID
     *
     * @return the ID
     */
    public int getId() {
        return mId;
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
     * Getter for the logo path
     *
     * @return the logo path
     */
    public String getLogoPath() {
        return mLogoPath;
    }

    /**
     * Getter for the URL
     *
     * @return the url
     */
    public String getUrl() {
        return mUrl;
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
     * Getter for the address
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
