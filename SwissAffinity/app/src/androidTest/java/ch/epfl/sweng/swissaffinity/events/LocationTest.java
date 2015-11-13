package ch.epfl.sweng.swissaffinity.events;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.swissaffinity.utilities.Location;

import static ch.epfl.sweng.swissaffinity.DataForTesting.*;

public class LocationTest extends TestCase {

    private Location location;

    @Before
    public void setUp() {
        location = LOCATIONS.get(1);
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Lausanne", location.getName());
    }

    @Test(expected = NullPointerException.class)
    public void testGetNameException() throws Exception {
        location = null;
    }
}