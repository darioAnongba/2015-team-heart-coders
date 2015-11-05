package ch.epfl.sweng.swissaffinity.users;

import java.util.Calendar;

import ch.epfl.sweng.swissaffinity.utils.Address;

/**
 * Class that represents a basic user of the app.
 */
public abstract class AbstractUser {
    private int id;

    private String username;
    private String email;
    private String password;

    private Calendar lastLogin;
    private Calendar createdAt;

    private String lastName;
    private String firstName;
    private String mobilePhone;
    private String homePhone;
    private Address address;

    public AbstractUser(int id,
                        String username,
                        String email,
                        String password,
                        Calendar lastLogin,
                        Calendar createdAt,
                        String lastName,
                        String firstName,
                        String mobilePhone,
                        String homePhone,
                        Address address) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
        this.lastName = lastName;
        this.firstName = firstName;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.address = address;
    }

    /**
     * Get Id
     *
     * @return id
     */
    public int getId() {

        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    /**
     * Get Username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set username
     *
     * @param username the username of the User
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get email
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set email
     *
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get password
     *
     * @return password encoded
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set password
     *
     * @param password The new password encoded
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get last login date
     *
     * @return last login date
     */
    public Calendar getLastLogin() {
        return lastLogin;
    }

    /**
     * Set last Login date
     *
     * @param lastLogin last login date
     */
    public void setLastLogin(Calendar lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Get creation date
     *
     * @return creation date
     */
    public Calendar getCreatedAt() {
        return createdAt;
    }

    /**
     * Set creation date
     *
     * @param createdAt The creation date
     */
    public void setCreatedAt(Calendar createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Get last name
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set last name
     *
     * @param lastName The last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get first name
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set first name
     *
     * @param firstName The first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get mobile phone
     *
     * @return mobile phone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * Set mobile phone
     *
     * @param mobilePhone The mobile phone
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * Get home phone
     *
     * @return The home phone
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * Set home phone
     *
     * @param homePhone The home phone
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * Get address
     *
     * @return address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Set address
     *
     * @param address The address
     */
    public void setAddress(Address address) {
        this.address = address;
    }
}
