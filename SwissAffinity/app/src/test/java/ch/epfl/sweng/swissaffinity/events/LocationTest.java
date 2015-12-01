package ch.epfl.sweng.swissaffinity.events;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ch.epfl.sweng.swissaffinity.BuildConfig;
import ch.epfl.sweng.swissaffinity.utilities.Location;

import static ch.epfl.sweng.swissaffinity.DataForTesting.*;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class LocationTest {

    private Location location;

    @Before
    public void setUp() {
        location = LOCATIONS.get(1);
    }

    @Test
    public void testGetID() throws Exception {
        assertEquals(3, location.getId());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("Lausanne", location.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIDException(){
        location = new Location(-1, "Genève");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNameException(){
        location = new Location(1, null);
    }
}