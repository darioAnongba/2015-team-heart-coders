package ch.epfl.sweng.swissaffinity.users;

import java.net.URL;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Class that represents a client User
 *
 * Created by dario on 17.10.2015.
 */
public final class User {
    public enum Gender {MALE, FEMALE}

    private int id;
    private int facebookId;

    private String username;
    private String email;
    private String lastName;
    private String firstName;
    private String mobilePhone;
    private String homePhone;
    private Address address;
    private String profession;

    private boolean locked;
    private boolean enabled;

    private Gender gender;

    private Calendar birthDate;

    private URL profilePicture;

    private Collection<Location> areasOfInterest;
    private Collection<Event> eventsAttended;

    /**
     * Create a new client User
     *
     * @param gender the gender
     * @param birthDate the birth date
     * @param profession the job
     * @param profilePicture the profile picture link on the server
     */
    public User(int id,
                int facebookId,
                String username,
                String email,
                String lastName,
                String firstName,
                String mobilePhone,
                String homePhone,
                Address address,
                boolean locked,
                boolean enabled,
                Gender gender,
                Calendar birthDate,
                String profession,
                URL profilePicture,
                List<Location> areasOfInterest,
                List<Event> eventsAttended) {

        this.id = id;
        this.facebookId = facebookId;
        this.username = username;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.address = address;
        this.locked = locked;
        this.enabled = enabled;
        this.gender = gender;
        this.birthDate = birthDate;
        this.profession = profession;
        this.profilePicture = profilePicture;
        this.areasOfInterest = new HashSet<>(areasOfInterest);
        this.eventsAttended = new HashSet<>(eventsAttended);
    }

    /**
     * Get Id
     *
     * @return id
     */
    public int getId() {

        return id;
    }

    /**
     * Get FacebookId
     *
     * @return id
     */
    public int getFacebookId() {
        return facebookId;
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

    /**
     * Get locked
     * @return locked
     */
    public boolean getLocked() {
        return locked;
    }

    /**
     * Get locked
     * @return enabled
     */
    public boolean getEnabled() {
        return enabled;
    }
    /**
     * Get gender
     * @return gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Set gender
     * @param gender the gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * get birth date
     * @return birth date
     */
    public Calendar getBirthDate() {
        return birthDate;
    }

    /**
     * Set birthDate
     * @param birthDate The birth date
     */
    public void setBirthDate(Calendar birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * the job (profession)
     * @return profession
     */
    public String getProfession() {
        return profession;
    }

    /**
     * Set profession
     * @param profession The profession
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * Get profile picture link
     * @return profile picture link
     */
    public URL getProfilePicture() {
        return profilePicture;
    }

    /**
     * Set profile picture link
     * @param profilePicture The profile picture link
     */
    public void setProfilePicture(URL profilePicture) {
        this.profilePicture = profilePicture;
    }

    /**
     * Get areas of interest
     * @return areas of interest
     */
    public Collection<Location> getAreasOfInterest() {
        return areasOfInterest;
    }

    /**
     * Set areas of interest
     *
     * @param areasOfInterest the areas of interest
     */
    public void setAreasOfInterest(Collection<Location> areasOfInterest) {
        this.areasOfInterest = areasOfInterest;
    }

    /**
     * Get the events the user attended
     * @return the events attended
     */
    public Collection<Event> getEventsAttended() {
        return eventsAttended;
    }

    /**
     * Set the events attended
     * @param eventsAttended The events attended
     */
    public void setEventsAttended(Collection<Event> eventsAttended) {
        this.eventsAttended = eventsAttended;
    }
}
