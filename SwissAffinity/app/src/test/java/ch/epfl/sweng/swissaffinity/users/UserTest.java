package ch.epfl.sweng.swissaffinity.users;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utils.Address;
import ch.epfl.sweng.swissaffinity.utils.Location;
import ch.epfl.sweng.swissaffinity.utils.PasswordHash;

import static org.junit.Assert.*;

/**
 * Test User Class
 * Created by dario on 19.10.2015.
 */
public class UserTest {

    private User user;
    private Address address;
    private URL profilePicture;
    private Calendar lastLogin;
    private Calendar createdAt;
    private Calendar birthDate;
    private String password;

    @Before
    public void setUp() {
        try {
            user = getStandardUser();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private User getStandardUser() throws MalformedURLException, InvalidKeySpecException, NoSuchAlgorithmException {
        int id = 1;
        String username = "speedDatingTest";
        String email = "swissaffinity@host.com";
        this.password = PasswordHash.createHash("thisIsMyPassword");

        this.lastLogin = Calendar.getInstance();
        lastLogin.set(1993, 2, 18);
        this.createdAt = Calendar.getInstance();
        createdAt.set(1993, 2, 18);

        String lastName = "Anongba Varela";
        String firstName = "Dario";
        String mobilePhone = "+41 79 000 00 00";
        String homePhone = "+41 26 000 00 00";
        this.address = new Address("ch", 1700, "Lausanne", "Vaud", 15, "Chemin des Roches");

        User.Gender gender = User.Gender.MALE;
        this.birthDate = Calendar.getInstance();
        birthDate.set(1993, 2, 18);
        String profession = "Student";
        this.profilePicture = new URL("http://img.timeinc.net/time/daily/2010/1011/poy_nomination_agassi.jpg");
        User.Relationship relationship = User.Relationship.SINGLE;
        User.Status status = User.Status.CONFIRMED;

        List<Location> areasOfInterest = new ArrayList<>();
        List<Event> eventsAttended = new ArrayList<>();

        return new User(
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
                address,
                gender,
                birthDate,
                profession,
                profilePicture,
                relationship,
                status,
                areasOfInterest,
                eventsAttended);
    }

    @Test
    public void testGetters() throws Exception {
        assertEquals("Id", 1, user.getId());
        assertEquals("Username", "speedDatingTest", user.getUsername());
        assertEquals("Email", "swissaffinity@host.com", user.getEmail());
        assertEquals("Password", password, user.getPassword());
        assertEquals("Last Login", lastLogin, user.getLastLogin());
        assertEquals("Created At", createdAt, user.getCreatedAt());
        assertEquals("Last Name", "Anongba Varela", user.getLastName());
        assertEquals("First Name", "Dario", user.getFirstName());
        assertEquals("Mobile Phone", "+41 79 000 00 00", user.getMobilePhone());
        assertEquals("Home phone", "+41 26 000 00 00", user.getHomePhone());
        assertEquals("address", address, user.getAddress());
        assertEquals("gender", User.Gender.MALE, user.getGender());
        assertEquals("birth date", birthDate, user.getBirthDate());
        assertEquals("profession", "Student", user.getProfession());
        assertEquals("profile picture link", profilePicture, user.getProfilePicture());
        assertEquals("relationShip", User.Relationship.SINGLE, user.getRelationship());
        assertEquals("status", User.Status.CONFIRMED, user.getStatus());
        assertEquals("areas of interest", new HashSet<Location>(), user.getAreasOfInterest());
        assertEquals("events attended", new HashSet<Event>(), user.getEventsAttended());
    }

    @Test
    public void testSetUsername() {
        String username = "newUsername";
        user.setUsername(username);
        assertEquals("Username", username, user.getUsername());
    }

    @Test
    public void testSetEmail() {
        String email = "dario.anongba@epfl.ch";
        user.setEmail(email);
        assertEquals("Email", email, user.getEmail());
    }

    @Test
    public void testSetPassword() throws InvalidKeySpecException, NoSuchAlgorithmException {
        String password = PasswordHash.createHash("newPassword");
        user.setPassword(password);
        assertEquals("Username", password, user.getPassword());
    }

    @Test
    public void testSetLastLogin() {
        Calendar lastLogin = Calendar.getInstance();
        lastLogin.set(2012, 5, 6);
        user.setLastLogin(lastLogin);
        assertEquals("Last login", lastLogin, user.getLastLogin());

        lastLogin.set(2012, 5, 7);
        assertNotSame("Last login immutable", lastLogin, user.getLastLogin());
    }

    @Test
    public void testSetCreatedAt() {
        Calendar createdAt = Calendar.getInstance();
        createdAt.set(2012, 5, 6);
        user.setCreatedAt(createdAt);
        assertEquals("Created at", createdAt, user.getCreatedAt());

        createdAt.set(2012, 5, 7);
        assertNotSame("Created at immutable", createdAt, user.getCreatedAt());
    }

    @Test
    public void testSetLastName() {
        String lastName = "newLastName";
        user.setLastName(lastName);
        assertEquals("Last Name", lastName, user.getLastName());
    }

    @Test
    public void testSetFirstName() {
        String firstName = "newFirstName";
        user.setFirstName(firstName);
        assertEquals("First Name", firstName, user.getFirstName());
    }

    @Test
    public void testSetMobilePhone() {
        String mobilePhone = "+41 78 000 00 00";
        user.setMobilePhone(mobilePhone);
        assertEquals("Mobile Phone", mobilePhone, user.getMobilePhone());
    }

    @Test
    public void testSetHomePhone() {
        String homePhone = "+41 22 000 00 00";
        user.setHomePhone(homePhone);
        assertEquals("Home Phone", homePhone, user.getHomePhone());
    }

    @Test
    public void testSetAddress() {
        Address address = new Address("fr", 2000, "Ville", "Province", 23, "Avenue de la france");
        user.setAddress(address);
        assertEquals("Address", address, user.getAddress());

        address.setProvince("Paris");
        assertNotSame("Address immutable", address, user.getAddress());
    }

    @Test
    public void testSetGender() {
        User.Gender gender = User.Gender.FEMALE;
        user.setGender(gender);
        assertEquals("Gender", gender, user.getGender());
    }

    @Test
    public void testSetBirthDate() {
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(2012, 5, 6);
        user.setBirthDate(birthDate);
        assertEquals("Birth date", birthDate, user.getBirthDate());

        birthDate.set(2012, 5, 7);
        assertNotSame("Birth date immutable", birthDate, user.getBirthDate());
    }

    @Test
    public void testSetProfession() throws Exception {
        String profession = "Peintre";
        user.setProfession(profession);
        assertEquals("Profession", profession, user.getProfession());
    }

    @Test
    public void testSetProfilePicture() throws Exception {
        URL profilePicture = new URL("http://newUrl.png");
        user.setProfilePicture(profilePicture);
        assertEquals("Profile picture link", profilePicture, user.getProfilePicture());
    }

    @Test
    public void testSetRelationship() throws Exception {
        User.Relationship relationship = User.Relationship.MARRIED;
        user.setRelationship(relationship);
        assertEquals("Relationship", relationship, user.getRelationship());
    }

    @Test
    public void testSetStatus() throws Exception {
        User.Status status = User.Status.BANNED;
        user.setStatus(status);
        assertEquals("Status", status, user.getStatus());
    }

    public void testSetAreasOfInterest() throws Exception {
    }

    public void testSetEventsAttended() throws Exception {
    }
}