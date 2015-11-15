package ch.epfl.sweng.swissaffinity.users;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Representation of a user.
 */
public final class User implements Serializable {

    /**
     * Gender of the user.
     */
    public enum Gender implements Serializable {
        MALE("male"),
        FEMALE("female");

        private String mGender;

        Gender(String gender) {
            mGender = gender;
        }

        /**
         * Getter for the gender
         *
         * @return the server API gender
         */
        public String get() {
            return mGender;
        }

        /**
         * Getter for the gender
         *
         * @param gender the server API gender
         *
         * @return
         */
        public static Gender getGender(String gender) {
            for (Gender g : Gender.values()) {
                if (g.mGender.equalsIgnoreCase(gender)) {
                    return g;
                }
            }
            return null;
        }
    }

    private int mId;
    private long mFacebookId;
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
     * Constructor of the class.
     *
     * @param id              the ID
     * @param facebookId      the Facebook ID
     * @param username        the user name
     * @param email           the email
     * @param lastName        the last name
     * @param firstName       the first name
     * @param mobilePhone     the mobile phone number
     * @param homePhone       the home phone number
     * @param address         the address {@link Address}
     * @param locked          true if the user is locked
     * @param enabled         true if the user is enabled
     * @param gender          the gender {@link Gender}
     * @param birthDate       the birth date {@link Date}
     * @param profession      the profession
     * @param profilePicture  the url of the profile picture
     * @param areasOfInterest the locations the user is interested in
     * @param eventsAttended  the events the user attended
     */
    public User(
            int id,
            long facebookId,
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
        mFacebookId = facebookId;
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
     * Getter for the ID
     *
     * @return
     */
    public int getId() {

        return mId;
    }

    /**
     * Getter for the Facebook ID
     *
     * @return
     */
    public long getFacebookId() {
        return mFacebookId;
    }

    /**
     * Getter for the user name
     *
     * @return
     */
    public String getUsername() {
        return mUsername;
    }

    /**
     * Getter for the email
     *
     * @return
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Setter for the email
     *
     * @param email The email
     */
    public void setEmail(String email) {
        mEmail = email;
    }

    /**
     * Getter for the last name
     *
     * @return
     */
    public String getLastName() {
        return mLastName;
    }

    /**
     * Setter for the last name
     *
     * @param lastName The last name
     */
    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    /**
     * Getter for the first name
     *
     * @return
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * Setter for the first name
     *
     * @param firstName The first name
     */
    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    /**
     * Getter for the mobile phone number
     *
     * @return
     */
    public String getMobilePhone() {
        return mMobilePhone;
    }

    /**
     * Setter for the mobile phone number
     *
     * @param mobilePhone The mobile phone
     */
    public void setMobilePhone(String mobilePhone) {
        mMobilePhone = mobilePhone;
    }

    /**
     * Getter for the home phone number
     *
     * @return
     */
    public String getHomePhone() {
        return mHomePhone;
    }

    /**
     * Setter for the home phone number
     *
     * @param homePhone The home phone
     */
    public void setHomePhone(String homePhone) {
        mHomePhone = homePhone;
    }

    /**
     * Getter for the address
     *
     * @return
     */
    public Address getAddress() {
        return mAddress;
    }

    /**
     * Setter for the address
     *
     * @param address The address
     */
    public void setAddress(Address address) {
        mAddress = address;
    }

    /**
     * Getter for the locked
     *
     * @return
     */
    public boolean getLocked() {
        return mLocked;
    }

    /**
     * Getter for the enabled
     *
     * @return
     */
    public boolean getEnabled() {
        return mEnabled;
    }

    /**
     * Getter for the gender
     *
     * @return
     */
    public Gender getGender() {
        return mGender;
    }

    /**
     * Setter for the gender
     *
     * @param gender the gender
     */
    public void setGender(Gender gender) {
        mGender = gender;
    }

    /**
     * Getter for the birth date
     *
     * @return
     */
    public Date getBirthDate() {
        return mBirthDate;
    }

    /**
     * Setter for the birth date
     *
     * @param birthDate The birth date
     */
    public void setBirthDate(Date birthDate) {
        mBirthDate = birthDate;
    }

    /**
     * Getter for the profession
     *
     * @return
     */
    public String getProfession() {
        return mProfession;
    }

    /**
     * Setter for the profession
     *
     * @param profession The profession
     */
    public void setProfession(String profession) {
        mProfession = profession;
    }

    /**
     * Getter for the  profile picture url
     *
     * @return
     */
    public URL getProfilePicture() {
        return mProfilePicture;
    }

    /**
     * Setter for the profile picture url
     *
     * @param profilePicture The profile picture url
     */
    public void setProfilePicture(URL profilePicture) {
        mProfilePicture = profilePicture;
    }

    /**
     * Getter for the areas of interest
     *
     * @return
     */
    public Collection<Location> getAreasOfInterest() {
        return new HashSet<>(mAreasOfInterest);
    }

    /**
     * Setter for the areas of interest
     *
     * @param areasOfInterest the areas of interest
     */
    public void setAreasOfInterest(Collection<Location> areasOfInterest) {
        mAreasOfInterest = new HashSet<>(areasOfInterest);
    }

    /**
     * Getter for the events attended
     *
     * @return
     */
    public List<Event> getEventsAttended() {
        return new ArrayList<>(mEventsAttended);
    }

    /**
     * Setter for the events attended
     *
     * @param eventsAttended The events attended
     */
    public void setEventsAttended(List<Event> eventsAttended) {
        mEventsAttended = new ArrayList<>(eventsAttended);
    }
}
