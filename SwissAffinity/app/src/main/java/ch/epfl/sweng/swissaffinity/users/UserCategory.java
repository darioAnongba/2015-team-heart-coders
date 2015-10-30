package ch.epfl.sweng.swissaffinity.users;

/**
 * Created by dario on 16.10.2015.
 *
 * Class that represents a category of Users
 */
public class UserCategory {
    private int id;
    private String name;

    /**
     * Create a new Category of Users
     *
     * @param id The unique identifier of the category
     * @param name The name of the category
     */
    public UserCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Get Id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * set Name
     * @param name The name of the category
     */
    public void setName(String name) {
        this.name = name;
    }
}
