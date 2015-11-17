package ch.epfl.sweng.swissaffinity.utilities;

import java.io.Serializable;

/**
 * Representation of a location.
 */
public class Location implements Serializable {

    private final int mId;
    private final String mName;

    /**
     * The constructor of a location
     *
     * @param id   the ID
     * @param name the name
     */
    public Location(int id, String name) {
        if (id < 0 || name == null) {
            throw new IllegalArgumentException();
        }
        mId = id;
        mName = name;
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
}
