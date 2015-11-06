package ch.epfl.sweng.swissaffinity.events;

import junit.framework.TestCase;

import org.junit.Before;

/**
 * Created by Max on 26/10/2015.
 */
public class LocationTest extends TestCase {

    private Location location;

    @Before
    public void setUp() {
        location = new Location("Lausanne");
    }

    public void testGetCity() throws Exception {
        assertEquals("City","Lausanne",location.getName());
    }
}