package ch.epfl.sweng.swissaffinity.utilities;

import java.io.Serializable;

/**
 * Representation of a location.
 */
public class Location implements Serializable {

    private final int mId;
    private final String mName;

    /**
     * The constructor of a location.
     *
     * @param name the name of the location.
     */
    public Location(int id, String name) {
        if (id < 0 || name == null) {
            throw new NullPointerException();
        }
        mId = id;
        mName = name;
    }

    /**
     * Getter for the ID
     *
     * @return
     */
    public int getId() {
        return mId;
    }

    /**
     * Getter for the name
     *
     * @return
     */
    public String getName() {
        return mName;
    }
}
