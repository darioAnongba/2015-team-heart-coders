package ch.epfl.sweng.swissaffinity.users;

import java.net.URL;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utils.Address;
import ch.epfl.sweng.swissaffinity.utils.Location;

/**
 * Class that represents a client User
 *
 * Created by dario on 17.10.2015.
 */
public final class User extends AbstractUser {
    public enum Gender {MALE, FEMALE}
    public enum Relationship {DIVORCED, SINGLE, MARRIED}
    public enum Status {LOCKED, BANNED, CONFIRMED, PENDING}

    private Gender gender;
    private Calendar birthDate;
    private String profession;
    private URL profilePicture;
    private Relationship relationship;
    private Status status;

    private Set<Location> areasOfInterest;
    private Set<Event> eventsAttended;

    /**
     * Create a new client User
     *
     * @param gender the gender
     * @param birthDate the birth date
     * @param profession the job
     * @param profilePicture the profile picture link on the server
     * @param relationship the relationship status
     * @param status the status of the account
     */
    public User(int id,
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
                Gender gender,
                Calendar birthDate,

                String profession,
                URL profilePicture,
                Relationship relationship,
                Status status,
                List<Location> areasOfInterest,
                List<Event> eventsAttended) {
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
        this.gender = gender;
        this.birthDate = birthDate;
        this.profession = profession;
        this.profilePicture = profilePicture;
        this.relationship = relationship;
        this.status = status;
        this.areasOfInterest = new HashSet<>(areasOfInterest);
        this.eventsAttended = new HashSet<>(eventsAttended);
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
     * Get relationship status
     * @return relationship status
     */
    public Relationship getRelationship() {
        return relationship;
    }

    /**
     * Set relationship status
     * @param relationship the relationship status
     */
    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    /**
     * Get status
     * @return status of the account
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set status
     * @param status The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Get areas of interest
     * @return areas of interest
     */
    public Set<Location> getAreasOfInterest() {
        return areasOfInterest;
    }

    /**
     * Set areas of interest
     *
     * @param areasOfInterest the areas of intereat
     */
    public void setAreasOfInterest(Set<Location> areasOfInterest) {
        this.areasOfInterest = areasOfInterest;
    }

    /**
     * Get the events the user attended
     * @return the events attended
     */
    public Set<Event> getEventsAttended() {
        return eventsAttended;
    }

    /**
     * Set the events attended
     * @param eventsAttended The events attended
     */
    public void setEventsAttended(Set<Event> eventsAttended) {
        this.eventsAttended = eventsAttended;
    }
}
