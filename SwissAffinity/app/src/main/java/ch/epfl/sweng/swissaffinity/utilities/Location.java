package ch.epfl.sweng.swissaffinity.utilities;

import java.io.Serializable;

/**
 * Representation of the location of an event.
 */
public class Location implements Serializable {

    private final String mName;

    /**
     * The constructor of a location.
     *
     * @param name the name of the location.
     */
    public Location(String name) {
        if (name == null) {
            throw new NullPointerException();
        }
        mName = name;
    }

    /**
     * @return the location name.
     */
    public String getName() {
        return mName;
    }
}
