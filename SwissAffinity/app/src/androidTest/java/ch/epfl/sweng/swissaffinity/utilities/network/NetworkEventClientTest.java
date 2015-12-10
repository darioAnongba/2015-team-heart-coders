package ch.epfl.sweng.swissaffinity.utilities.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.events.NetworkEventClient;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserFactory;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;
import ch.epfl.sweng.swissaffinity.utilities.parsers.events.SpeedDatingEventParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SuppressWarnings("unchecked")
public class NetworkEventClientTest {

    private Collection<Location> testLocations;

    private JSONArray jsonEvents;
    private SafeJSONObject safeJSONObject;

    private String event4;
    private String eventsByLocation;
    private String allEvents;

    private String mockServerURL;
    private DefaultNetworkProvider mockNetworkProvider;
    private NetworkEventClient networkEventClient;

    private SpeedDatingEvent testOneEvent;
    private List<Event> testEventByLocationList;
    private List<Event> testAllEventList;

    private List<Event> eventList;

    private SpeedDatingEventParser speedDatingEventParser;

    @Before
    public void setup() throws ParserException, JSONException {
        // Location = Lausanne
        testLocations = new ArrayList<Location>(Arrays.asList(DataForTesting.LOCATIONS.get(1)));

        event4 = DataForTesting.event4JSONcontent;
        eventsByLocation = DataForTesting.eventsLausanneJSONcontent;
        allEvents = DataForTesting.allEventsJSONcontent;

        testAllEventList = new ArrayList<Event>();
        jsonEvents = new JSONArray(allEvents);
        for (int i = 0; i < jsonEvents.length(); ++i) {
            SafeJSONObject jsonObject = new SafeJSONObject(jsonEvents.getJSONObject(i));
            Parser<? extends Event> parser = ParserFactory.parserFor(jsonObject);
            Event event = parser.parse(jsonObject);
            testAllEventList.add(event);
        }

        testEventByLocationList = new ArrayList<>();
        jsonEvents = new JSONArray(eventsByLocation);
        for (int i = 0; i < jsonEvents.length(); ++i) {
            SafeJSONObject jsonObject = new SafeJSONObject(jsonEvents.getJSONObject(i));
            Parser<? extends Event> parser = ParserFactory.parserFor(jsonObject);
            Event event = parser.parse(jsonObject);
            testEventByLocationList.add(event);
        }

        safeJSONObject = new SafeJSONObject(DataForTesting.event4JSONcontent);
        speedDatingEventParser = new SpeedDatingEventParser();
        testOneEvent = speedDatingEventParser.parse(safeJSONObject);

        mockServerURL = "http://beecreative.ch";
        mockNetworkProvider = mock(DefaultNetworkProvider.class);
        networkEventClient = new NetworkEventClient(mockServerURL, mockNetworkProvider);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalURL() {
        networkEventClient = new NetworkEventClient(null, mockNetworkProvider);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullNetworkProvider() {
        networkEventClient = new NetworkEventClient(mockServerURL, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFetchAllForException() throws EventClientException {
        networkEventClient.fetchAllFor(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFetchByException() throws EventClientException {
        networkEventClient.fetchBy(-1);
    }

    @Test
    public void testFetchAll() throws EventClientException, IOException {
        when(mockNetworkProvider.getContent(anyString())).thenReturn(allEvents);
        eventList = networkEventClient.fetchAll();

        for (int i = 0; i < eventList.size(); i++) {
            assertEquals(testAllEventList.get(i).getName(), eventList.get(i).getName());
            assertEquals(
                testAllEventList.get(i).getDescription(),
                eventList.get(i).getDescription());
            assertEquals(testAllEventList.get(i).getImagePath(), eventList.get(i).getImagePath());
            assertEquals(
                testAllEventList.get(i).getBasePrice(),
                eventList.get(i).getBasePrice(),
                0.009);
            assertEquals(testAllEventList.get(i).getDateBegin(), eventList.get(i).getDateBegin());
            assertEquals(testAllEventList.get(i).getDateEnd(), eventList.get(i).getDateEnd());
            assertEquals(testAllEventList.get(i).getMaxPeople(), eventList.get(i).getMaxPeople());
            assertEquals(
                testAllEventList.get(i).getLocation().getId(),
                eventList.get(i).getLocation().getId());
        }
    }

    @Test
    public void testFetchAllFor() throws EventClientException, IOException {
        when(mockNetworkProvider.getContent(anyString())).thenReturn(eventsByLocation);
        eventList = networkEventClient.fetchAllFor(testLocations);

        for (int i = 0; i < eventList.size(); i++) {
            assertEquals(testEventByLocationList.get(i).getName(), eventList.get(i).getName());
            assertEquals(
                testEventByLocationList.get(i).getDescription(),
                eventList.get(i).getDescription());
            assertEquals(
                testEventByLocationList.get(i).getImagePath(),
                eventList.get(i).getImagePath());
            assertEquals(
                testEventByLocationList.get(i).getBasePrice(),
                eventList.get(i).getBasePrice(),
                0.009);
            assertEquals(
                testEventByLocationList.get(i).getDateBegin(),
                eventList.get(i).getDateBegin());
            assertEquals(
                testEventByLocationList.get(i).getDateEnd(),
                eventList.get(i).getDateEnd());
            assertEquals(
                testEventByLocationList.get(i).getMaxPeople(),
                eventList.get(i).getMaxPeople());
            assertEquals(
                testEventByLocationList.get(i).getLocation().getId(),
                eventList.get(i).getLocation().getId());
        }
    }

    @Test
    public void testFetchForUser() throws EventClientException {
        networkEventClient.fetchForUser("Lionel Fleury");
    }

    @Test
    public void testFetchBy() throws EventClientException, IOException {
        when(mockNetworkProvider.getContent(anyString())).thenReturn(event4);
        Event event = networkEventClient.fetchBy(4);
        Event testEvent = testOneEvent;

        assertEquals(testEvent.getName(), event.getName());
        assertEquals(testEvent.getDescription(), event.getDescription());
        assertEquals(testEvent.getImagePath(), event.getImagePath());
        assertEquals(testEvent.getBasePrice(), event.getBasePrice(), 0.009);
        assertEquals(testEvent.getDateBegin(), event.getDateBegin());
        assertEquals(testEvent.getDateEnd(), event.getDateEnd());
        assertEquals(testEvent.getMaxPeople(), event.getMaxPeople());
        assertEquals(testEvent.getLocation().getId(), event.getLocation().getId());
    }

    @Test
    public void testFetchAllException() throws IOException, EventClientException {
        when(mockNetworkProvider.getConnection(anyString())).thenThrow(IOException.class);
        List<Event> events = networkEventClient.fetchAll();
        assertTrue(events.isEmpty());
    }

    @Test(expected = EventClientException.class)
    public void testFetchByException2() throws IOException, EventClientException {
        when(mockNetworkProvider.getConnection(anyString())).thenThrow(IOException.class);
        networkEventClient.fetchBy(4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImageForNull() throws EventClientException, IOException {
        networkEventClient.imageFor(null);
    }

    @Test
    public void testImageFor() throws EventClientException {
        networkEventClient.imageFor(testAllEventList.get(0).getImagePath());
    }

    @Test(expected = EventClientException.class)
    public void testImageForException() throws IOException, EventClientException {
        when(mockNetworkProvider.getConnection(anyString())).thenThrow(IOException.class);
        networkEventClient.imageFor(testAllEventList.get(0).getImagePath());
    }
}
