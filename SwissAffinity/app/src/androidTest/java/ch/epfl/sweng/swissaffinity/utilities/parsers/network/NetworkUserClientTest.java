package ch.epfl.sweng.swissaffinity.utilities.parsers.network;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.users.NetworkUserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;
import ch.epfl.sweng.swissaffinity.utilities.parsers.user.UserParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class NetworkUserClientTest {

    private String mockServerURL;
    private NetworkProvider mockNetworkProvider;
    private NetworkUserClient networkUserClient;

    private UserParser userParser = new UserParser();
    private User testUser;
    private User returnedUser;

    private Collection<Location> areasOfInterest;
    private List<Event> eventsAttended;

    @Before
    public void setup() throws IOException, JSONException, ParserException {
        testUser = DataForTesting.createUser();
        areasOfInterest = DataForTesting.LOCATIONS;
        eventsAttended = new ArrayList<>();

        mockServerURL = "http://beecreative.ch";
        mockNetworkProvider = mock(DefaultNetworkProvider.class);
        networkUserClient = new NetworkUserClient(mockServerURL, mockNetworkProvider);

        when(mockNetworkProvider.getContent(anyString())).thenReturn(DataForTesting.userJSONcontent);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIllegalURL() {
        networkUserClient = new NetworkUserClient(null, mockNetworkProvider);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullNetworkProvider() {
        networkUserClient = new NetworkUserClient(mockServerURL, null);
    }

    @Test
    public void testFetchByUsername() throws UserClientException {
        returnedUser = networkUserClient.fetchByUsername("testUsername");

        assertEquals(testUser.getId(), returnedUser.getId());
        assertEquals(testUser.getFacebookId(), returnedUser.getFacebookId());

        assertEquals(testUser.getUsername(), returnedUser.getUsername());
        assertEquals(testUser.getFirstName(), returnedUser.getFirstName());
        assertEquals(testUser.getLastName(), returnedUser.getLastName());

        assertEquals(testUser.getHomePhone(), returnedUser.getHomePhone());
        assertEquals(testUser.getMobilePhone(), returnedUser.getMobilePhone());
        assertEquals(testUser.getEmail(), returnedUser.getEmail());
        assertEquals(testUser.getAddress().getCity(), returnedUser.getAddress().getCity());
        assertEquals(testUser.getProfession(), returnedUser.getProfession());

        assertEquals(testUser.getLocked(), returnedUser.getLocked());
        assertEquals(testUser.getEnabled(), returnedUser.getEnabled());

        assertEquals(testUser.getGender(), returnedUser.getGender());
        assertEquals(testUser.getBirthDate(), returnedUser.getBirthDate());


        Collection<Location> coll1 = testUser.getAreasOfInterest();
        Collection<Location> coll2 = returnedUser.getAreasOfInterest();
        coll1.removeAll(coll2);
        assertTrue(coll1.isEmpty());


        for (int i = 0; i < returnedUser.getEventsAttended().size(); i++) {
            Iterator<Event> iterator3 = testUser.getEventsAttended().iterator();
            Iterator<Event> iterator4 = returnedUser.getEventsAttended().iterator();

            if (iterator3.hasNext() && iterator4.hasNext()) {
                assertEquals(iterator3.next(), iterator4.next());
            }
        }
    }

    @Test
    public void testFetchByFacebookID() throws UserClientException {
        returnedUser = networkUserClient.fetchByFacebookID("1");

        assertEquals(testUser.getId(), returnedUser.getId());
        assertEquals(testUser.getFacebookId(), returnedUser.getFacebookId());

        assertEquals(testUser.getUsername(), returnedUser.getUsername());
        assertEquals(testUser.getFirstName(), returnedUser.getFirstName());
        assertEquals(testUser.getLastName(), returnedUser.getLastName());

        assertEquals(testUser.getHomePhone(), returnedUser.getHomePhone());
        assertEquals(testUser.getMobilePhone(), returnedUser.getMobilePhone());
        assertEquals(testUser.getEmail(), returnedUser.getEmail());
        assertEquals(testUser.getAddress().getCity(), returnedUser.getAddress().getCity());
        assertEquals(testUser.getProfession(), returnedUser.getProfession());

        assertEquals(testUser.getLocked(), returnedUser.getLocked());
        assertEquals(testUser.getEnabled(), returnedUser.getEnabled());

        assertEquals(testUser.getGender(), returnedUser.getGender());
        assertEquals(testUser.getBirthDate(), returnedUser.getBirthDate());

        Collection<Location> coll1 = testUser.getAreasOfInterest();
        Collection<Location> coll2 = returnedUser.getAreasOfInterest();

        coll1.removeAll(coll2);
        assertTrue(coll1.isEmpty());

        for (int i = 0; i < returnedUser.getEventsAttended().size(); i++) {
            Iterator<Event> iterator3 = testUser.getEventsAttended().iterator();
            Iterator<Event> iterator4 = returnedUser.getEventsAttended().iterator();

            if (iterator3.hasNext() && iterator4.hasNext()) {
                assertEquals(iterator3.next(), iterator4.next());
            }
        }
    }
}
