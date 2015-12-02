package ch.epfl.sweng.swissaffinity.users;

import java.io.Serializable;
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
         * @return the gender
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

    private final int mId;
    private final String mFacebookId;
    private final String mUsername;
    private final String mEmail;
    private final String mLastName;
    private final String mFirstName;
    private final String mMobilePhone;
    private final String mHomePhone;
    private final Address mAddress;
    private final String mProfession;
    private final boolean mLocked;
    private final boolean mEnabled;
    private final Gender mGender;
    private final Date mBirthDate;
    private final String mProfilePicture;
    private final Collection<Location> mAreasOfInterest;
    private final List<Event> mEventsAttended;

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
        String facebookId,
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
        String profilePicture,
        Collection<Location> areasOfInterest,
        List<Event> eventsAttended)
    {
        if (id < 0 || facebookId == null || username == null || email == null || lastName == null ||
            firstName == null || mobilePhone == null || homePhone == null || address == null ||
            gender == null || birthDate == null || profession == null || profilePicture == null ||
            areasOfInterest == null || eventsAttended == null)
        {
            throw new IllegalArgumentException();
        }
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
     * @return the ID
     */
    public int getId() {
        return mId;
    }

    /**
     * Getter for the Facebook ID
     *
     * @return the Facebook ID
     */
    public String getFacebookId() {
        return mFacebookId;
    }

    /**
     * Getter for the user name
     *
     * @return the user name
     */
    public String getUsername() {
        return mUsername;
    }

    /**
     * Getter for the email
     *
     * @return the email
     */
    public String getEmail() {
        return mEmail;
    }

    /**
     * Getter for the last name
     *
     * @return the last name
     */
    public String getLastName() {
        return mLastName;
    }

    /**
     * Getter for the first name
     *
     * @return the first name
     */
    public String getFirstName() {
        return mFirstName;
    }

    /**
     * Getter for the mobile phone number
     *
     * @return the mobile phone number
     */
    public String getMobilePhone() {
        return mMobilePhone;
    }

    /**
     * Getter for the home phone number
     *
     * @return the home phone number
     */
    public String getHomePhone() {
        return mHomePhone;
    }

    /**
     * Getter for the address
     *
     * @return the address
     */
    public Address getAddress() {
        return mAddress;
    }

    /**
     * Getter for the locked status
     *
     * @return the locked status
     */
    public boolean getLocked() {
        return mLocked;
    }

    /**
     * Getter for the enabled status
     *
     * @return the enabled status
     */
    public boolean getEnabled() {
        return mEnabled;
    }

    /**
     * Getter for the gender
     *
     * @return the gender {@link Gender}
     */
    public Gender getGender() {
        return mGender;
    }

    /**
     * Getter for the birth date
     *
     * @return the birth date {@link Date}
     */
    public Date getBirthDate() {
        return mBirthDate;
    }

    /**
     * Getter for the profession
     *
     * @return the profession
     */
    public String getProfession() {
        return mProfession;
    }

    /**
     * Getter for the profile picture url
     *
     * @return the profile picture url
     */
    public String getProfilePicture() {
        return mProfilePicture;
    }

    /**
     * Getter for the areas of interest
     *
     * @return a collection of locations
     */
    public Collection<Location> getAreasOfInterest() {
        return new HashSet<>(mAreasOfInterest);
    }

    /**
     * Getter for the events attended
     *
     * @return a list of events
     */
    public List<Event> getEventsAttended() {
        return new ArrayList<>(mEventsAttended);
    }
}
