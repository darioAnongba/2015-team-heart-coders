package ch.epfl.sweng.swissaffinity.users;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Class that represents a client User
 * <p/>
 * Created by dario on 17.10.2015.
 */
public final class User {
    public enum Gender {MALE, FEMALE}

    private int mId;
    private int mfacebookId;

    private String mUsername;
    private String mEmail;
    private String mLastName;
    private String mFirstName;
    private String mMobilePhone;
    private String mHomePhone;
    private Address mAddress;
    private String mProfession;

    private boolean mLocked;
    private boolean mEnabled;

    private Gender mGender;

    private Date mBirthDate;

    private URL mProfilePicture;

    private Collection<Location> mAreasOfInterest;
    private List<Event> mEventsAttended;

    /**
     * Create a new client User
     *
     * @param gender         the gender
     * @param birthDate      the birth date
     * @param profession     the job
     * @param profilePicture the profile picture link on the server
     */
    public User(
            int id,
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
            Date birthDate,
            String profession,
            URL profilePicture,
            Collection<Location> areasOfInterest,
            List<Event> eventsAttended)
    {

        mId = id;
        mfacebookId = facebookId;
        mUsername = username;
        mEmail = email;
        mLastName = lastName;
        mFirstName = firstName;
        mMobilePhone = mobilePhone;
        mHomePhone = homePhone;
        mAddress = address;
        mLocked = locked;
        mEnabled = enabled;
        mGender = gender;
        mBirthDate = birthDate;
        mProfession = profession;
        mProfilePicture = profilePicture;
        mAreasOfInterest = new HashSet<>(areasOfInterest);
        mEventsAttended = new ArrayList<>(eventsAttended);
    }

    /**
     * Get Id
     *
     * @return id
     */
    public int getId() {

        return mId;
    }

    /**
     * Get facebook Id
     *
     * @return id
     */
    public int getFacebookId() {
        return mfacebookId;
    }

    /**
     * Get Username
     *
     * @return username
     */
    public String getUsername() {
        return mUsername;
    }

    /**
     * Get email
     *
     * @return email
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Set email
     *
     * @param email The email
     */
    public void setEmail(String email) {
        mEmail = email;
    }

    /**
     * Get last name
     *
     * @return last name
     */
    public String getLastName() {
        return mLastName;
    }

    /**
     * Set last name
     *
     * @param lastName The last name
     */
    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    /**
     * Get first name
     *
     * @return the first name
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * Set first name
     *
     * @param firstName The first name
     */
    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    /**
     * Get mobile phone
     *
     * @return mobile phone
     */
    public String getMobilePhone() {
        return mMobilePhone;
    }

    /**
     * Set mobile phone
     *
     * @param mobilePhone The mobile phone
     */
    public void setMobilePhone(String mobilePhone) {
        mMobilePhone = mobilePhone;
    }

    /**
     * Get home phone
     *
     * @return The home phone
     */
    public String getHomePhone() {
        return mHomePhone;
    }

    /**
     * Set home phone
     *
     * @param homePhone The home phone
     */
    public void setHomePhone(String homePhone) {
        mHomePhone = homePhone;
    }

    /**
     * Get address
     *
     * @return address
     */
    public Address getAddress() {
        return mAddress;
    }

    /**
     * Set address
     *
     * @param address The address
     */
    public void setAddress(Address address) {
        mAddress = address;
    }

    /**
     * Get locked
     *
     * @return locked
     */
    public boolean getLocked() {
        return mLocked;
    }

    /**
     * Get locked
     *
     * @return enabled
     */
    public boolean getEnabled() {
        return mEnabled;
    }

    /**
     * Get gender
     *
     * @return gender
     */
    public Gender getGender() {
        return mGender;
    }

    /**
     * Set gender
     *
     * @param gender the gender
     */
    public void setGender(Gender gender) {
        mGender = gender;
    }

    /**
     * get birth date
     *
     * @return birth date
     */
    public Date getBirthDate() {
        return mBirthDate;
    }

    /**
     * Set birthDate
     *
     * @param birthDate The birth date
     */
    public void setBirthDate(Date birthDate) {
        mBirthDate = birthDate;
    }

    /**
     * the job (profession)
     *
     * @return profession
     */
    public String getProfession() {
        return mProfession;
    }

    /**
     * Set profession
     *
     * @param profession The profession
     */
    public void setProfession(String profession) {
        mProfession = profession;
    }

    /**
     * Get profile picture link
     *
     * @return profile picture link
     */
    public URL getProfilePicture() {
        return mProfilePicture;
    }

    /**
     * Set profile picture link
     *
     * @param profilePicture The profile picture link
     */
    public void setProfilePicture(URL profilePicture) {
        mProfilePicture = profilePicture;
    }

    /**
     * Get areas of interest
     *
     * @return areas of interest
     */
    public Collection<Location> getAreasOfInterest() {
        return new HashSet<>(mAreasOfInterest);
    }

    /**
     * Set areas of interest
     *
     * @param areasOfInterest the areas of interest
     */
    public void setAreasOfInterest(Collection<Location> areasOfInterest) {
        mAreasOfInterest = new HashSet<>(areasOfInterest);
    }

    /**
     * Get the events the user attended
     *
     * @return the events attended
     */
    public List<Event> getEventsAttended() {
        return new ArrayList<>(mEventsAttended);
    }

    /**
     * Set the events attended
     *
     * @param eventsAttended The events attended
     */
    public void setEventsAttended(List<Event> eventsAttended) {
        mEventsAttended = new ArrayList<>(eventsAttended);
    }
}
