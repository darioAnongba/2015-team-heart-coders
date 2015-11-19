package ch.epfl.sweng.swissaffinity.users;


import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;

import static ch.epfl.sweng.swissaffinity.DataForTesting.LOCATIONS;
import static ch.epfl.sweng.swissaffinity.DataForTesting.speedDatingEventCreator;
import static ch.epfl.sweng.swissaffinity.DataForTesting.userCreator;
import static org.junit.Assert.assertEquals;

public class UserTest {

    private User user;
    private Address address;
    private List<Location> locations;
    private List<Event> events;
    private Date birthday;
    private URL url;

    @Before
    public void setUp() throws MalformedURLException, ParserException {
        user = userCreator();
        address = new Address("Switzerland",1000,"Lausanne","Vaud",1,"Rue du Test");
        locations = new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1)));
        events = new ArrayList<Event>(Arrays.asList(speedDatingEventCreator()));
        birthday = DateParser.parseFromString("1983-11-16T16:00:00+0100");
        url = new URL("http://testUrl.com");
    }

    @Test
    public void testGetId() {
        assertEquals(1, user.getId());
    }

    @Test
    public void testGetFacebookId() {
        assertEquals(2001, user.getFacebookId());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("testFirstName", user.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("testLastName", user.getLastName());
    }

    @Test
    public void testGetGender() {
        assertEquals(User.Gender.MALE, user.getGender());
    }

    @Test
    public void testGetEmail() {
        assertEquals("testEmail", user.getEmail());
    }

    @Test
    public void testGetMobilePhone() {
        assertEquals("testPhone", user.getMobilePhone());
    }

    @Test
    public void testGetHomePhone() {
        assertEquals("testHomePhone", user.getHomePhone());
    }

    @Test
    public void testGetUsername() {
        assertEquals("testUsername", user.getUsername());
    }

    @Test
    public void testGetProfession() {
        assertEquals("testProfession", user.getProfession());
    }

/*    @Test
    public void testGetAreaOfInterest() {
        for (int i = 0; i < locations.size(); i++) {
            assertEquals(locations.get(i), user.getAreasOfInterest().get(i));
        }
    }

    @Test
         public void testGetAddress() {
        assertEquals(address, user.getAddress());
    }*/

    @Test
    public void testGetBirthDate() {
        assertEquals(birthday, user.getBirthDate());
    }

    @Test
    public void testGetEventsAttended() {
        for (int i = 0; i < events.size(); i++) {
            assertEquals(events.get(i), user.getEventsAttended().get(i));
        }
    }

    @Test
    public void testGetLocked() {
        assertEquals(false, user.getLocked());
    }

    @Test
    public void testGetEnabled() {
        assertEquals(true, user.getEnabled());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIDException() throws ParserException {
        user = new User(-10,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFacebookIDException() throws ParserException {
        user = new User(1,
                -10,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUsernameException() throws ParserException {
        user = new User(1,
                2001,
                null,
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmailException() throws ParserException {
        user = new User(1,
                2001,
                "testUsername",
                null,
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "imgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLastNameException() throws ParserException {
        user = new User(1,
                2001,
                "testUsername",
                "testEmail",
                null,
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFirstNameException() throws ParserException {
        user = new User(1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                null,
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPhoneException() throws ParserException {
        user = new User(1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                null,
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHomePhoneException() throws ParserException {
        user = new User(1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                null,
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddressException() throws ParserException {

        new User(1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                null,
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenderException() throws ParserException {
        user = new User(1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                null,
                birthday,
                "testProfession",
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBirthdayException() throws ParserException {
        user = new User(1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                null,
                "testProfession",
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProfessionException() throws ParserException {
        user = new User(1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                null,
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testURLException() throws ParserException {
        user = new User(1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                null,
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAreasOfInterestException() throws ParserException {
        user = new User(1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "ImgURL",
                null,
                new ArrayList<Event>(Arrays.asList(speedDatingEventCreator())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEventAttendedException() throws ParserException {
        user = new User(1,
                2001,
                "testUsername",
                "testEmail",
                "testLastName",
                "testFirstName",
                "testPhone",
                "testHomePhone",
                new Address("Switzerland", 1000, "Lausanne", "Vaud", 1, "Rue du Test"),
                false,
                true,
                User.Gender.MALE,
                birthday,
                "testProfession",
                "ImgURL",
                new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1))),
                null);
    }



}
