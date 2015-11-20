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

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (getClass() != obj.getClass()){
            return false;
        }
        final Location other = (Location) obj;
        if (this.mId != other.getId()){
            return false;
        }
        if ((this.mName == null) ? (other.getName() != null) :
                (! this.mName.equals(other.getName()))){
            return false;
        }
        return true;
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
