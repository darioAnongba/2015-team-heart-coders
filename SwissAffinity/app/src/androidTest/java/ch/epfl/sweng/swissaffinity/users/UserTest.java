package ch.epfl.sweng.swissaffinity.users;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;

import static ch.epfl.sweng.swissaffinity.DataForTesting.*;


/**
 * Created by yannick on 13.11.15.
 */
public class UserTest extends TestCase {

    private User user;
    private Address address;
    private Collection<Location> locations;
    private Collection<Event> events;

    @Before
    public void setUp() throws MalformedURLException, ParserException {
        user = userCreator();
        address = new Address("Switzerland",1000,"Lausanne","Vaud",1,"Rue du Test");
        locations = new ArrayList<>(Arrays.asList(LOCATIONS.get(0), LOCATIONS.get(1)));
        events = new ArrayList<Event>(Arrays.asList(speedDatingEventCreator()));
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

/*
    @Test
    public void testGetAreaOfInterest() {
        assertEquals(locations, user.getAreasOfInterest());
    }

    @Test
    public void testGetAddress() {
        assertEquals(address, user.getAddress());
    }

    @Test
    public void testGetBirthDate() {
        assertEquals(new Date(), user.getBirthDate());
    }

    @Test
    public void testGetEventsAttended() {
        assertEquals(events, user.getEventsAttended());
    }
*/

    @Test
    public void testGetLocked() {
        assertEquals(false, user.getLocked());
    }

    @Test
    public void testGetEnabled() {
        assertEquals(true, user.getEnabled());
    }


}
