package ch.epfl.sweng.swissaffinity.events;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class LocationTest extends TestCase {

    private Location location;

    @Before
    public void setUp() {
        location = new Location("Lausanne");
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Lausanne", location.getName());
    }
}