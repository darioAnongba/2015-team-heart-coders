package ch.epfl.sweng.swissaffinity.utilities;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return Objects.equals(mId, location.mId) &&
               Objects.equals(mName, location.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mName);
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
