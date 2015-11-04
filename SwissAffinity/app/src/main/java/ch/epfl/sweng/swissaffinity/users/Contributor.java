package ch.epfl.sweng.swissaffinity.users;

import java.util.Calendar;
import java.util.List;

import ch.epfl.sweng.swissaffinity.utils.Address;

/**
 * Class representing a contributor user
 */
public final class Contributor extends BaseUser {
    public enum Roles {
        SUPER_ADMIN, ADMIN, CONTRIBUTOR
    }

    private List<Roles> roles;
    private List<UserCategory> categories;

    /**
     * Create a new contributor
     *
     * @param roles The list of roles of the contributor
     * @param categories The list of categories in which the contributor belongs
     */
    public Contributor(int id,
                       String username,
                       String email,
                       String password,
                       Calendar lastLogin,
                       Calendar createdAt,
                       String lastName,
                       String firstName,
                       String mobilePhone,
                       String homePhone,
                       Address address,
                       List<Roles> roles,
                       List<UserCategory> categories) {
        super(
                id,
                username,
                email,
                password,
                lastLogin,
                createdAt,
                lastName,
                firstName,
                mobilePhone,
                homePhone,
                address);
        this.roles = roles;
        this.categories = categories;
    }

    /**
     * Get roles
     *
     * @return roles
     */
    public List<Roles> getRoles() {
        return roles;
    }

    /**
     * Set roles
     *
     * @param roles The list of roles of the contributor
     */
    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    /**
     * get categories
     * @return categories
     */
    public List<UserCategory> getCategories() {
        return categories;
    }

    /**
     * Set categories
     *
     * @param categories The list of categories in which the contributor belongs
     */
    public void setCategories(List<UserCategory> categories) {
        this.categories = categories;
    }
}
