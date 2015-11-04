package ch.epfl.sweng.swissaffinity.events;

/**
 * The class to represent the type of Establishment
 */
public class EstablishmentType {

    String name;

    /**
     * The constructor of the EstablismentType
     * @param name the name of the Establishment
     */
    public EstablishmentType(String name) {
        this.name = name;
    }

    /**
     * The getter for the name of the Establisment
     * @return the establisment name
     */
    public String getName() {
        return name;
    }

    /**
     * The setter for the name of the event
     * @param name the new name for the establisment
     * @throws IllegalArgumentException if the name is empty
     */
    public void setName(String name) throws IllegalArgumentException {
        if(name.isEmpty()) {
            throw new IllegalArgumentException("The name is empty" + name);
        }
        this.name = name;
    }
}
