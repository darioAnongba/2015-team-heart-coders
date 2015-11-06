package ch.epfl.sweng.swissaffinity.events;

/**
 * Representation of the location of an event.
 */
public class Location {

    private final String mName;

    /**
     * The constructor of a location.
     *
     * @param name the name of the location.
     */
    public Location(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The location name is invalid");
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
