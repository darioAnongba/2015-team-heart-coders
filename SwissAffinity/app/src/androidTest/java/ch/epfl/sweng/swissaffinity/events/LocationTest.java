package ch.epfl.sweng.swissaffinity.events;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.swissaffinity.utilities.Location;

import static ch.epfl.sweng.swissaffinity.DataForTesting.*;
import static org.junit.Assert.assertEquals;

public class LocationTest {

    private Location location;

    @Before
    public void setUp() {
        location = LOCATIONS.get(1);
    }

    @Test
    public void testGetID() throws Exception {
        assertEquals(1, location.getId());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Lausanne", location.getName());
    }

    @Test(expected = NullPointerException.class)
    public void testIDException() throws Exception {
        location = new Location(-1, "Genève");
    }

    @Test(expected = NullPointerException.class)
    public void testNameException() throws Exception {
        location = new Location(1, null);
    }
}