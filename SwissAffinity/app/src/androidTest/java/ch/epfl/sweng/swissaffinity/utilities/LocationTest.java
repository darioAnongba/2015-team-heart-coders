package ch.epfl.sweng.swissaffinity.utilities;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import ch.epfl.sweng.swissaffinity.DataForTesting;

import static org.junit.Assert.assertEquals;

/**
 * Created by sahinfurkan on 10/12/15.
 */
public class LocationTest {
    List<Location> locations;
    int testId;
    int maliciousId;
    String testName;
    String maliciousName;
    @Before
    public void setUp(){
        locations = DataForTesting.LOCATIONS;
        testId = 1;
        maliciousId = -1;

        testName = "Test";
        maliciousName = null;
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void constructorExceptionTest(){
        Location location = new Location(maliciousId, testName);
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructorExceptionTest2(){
        Location location = new Location(testId, maliciousName);
    }

    @Test
    public void constructorSuccess(){
        Location location = new Location(testId, testName);
    }

    @Test
    public void equalsTest(){
        for (int i = 0; i < locations.size(); i++) {
            Location locationTest = new Location(locations.get(i).getId(), locations.get(i).getName());
            if (!locations.get(i).equals(locationTest)) throw new AssertionError();
        }
    }

    @Test
    public void notEqualsTest(){
        Location testLocation = new Location(testId, testName);
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).equals(testLocation)) throw new AssertionError();
        }
    }

    @Test
    public void getIdTest(){
        Location testLocation = new Location(testId, testName);
        assertEquals(testLocation.getId(), testId);
    }

    @Test
    public void getNameTest(){
        Location testLocation = new Location(testId, testName);
        assertEquals(testLocation.getName(), testName);
    }

}
