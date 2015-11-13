package ch.epfl.sweng.swissaffinity.events;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.swissaffinity.DataForTesting;

import static org.junit.Assert.assertEquals;

/**
 * Created by yannick on 13.11.15.
 */
public class EstablishmentTest {

    private Establishment establishment;

    @Before
    public void setup() {
        establishment = DataForTesting.ESTABLISHMENTS.get(0);
    }

    @Test
    public void testGetName() {
        assertEquals("Cafe Cuba", establishment.getName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("testDescription", establishment.getDescription());
    }

    @Test
    public void testGetLogoPath() {
        assertEquals("logoTest", establishment.getLogoPath());
    }

    @Test
    public void testGetAddress() {
        assertEquals(DataForTesting.ADRESSES.get(0), establishment.getAddress());
    }

    @Test
    public void testGetType() {
        assertEquals(Establishment.Type.BAR, establishment.getType());
    }

    @Test
    public void testGetUrl() {
        assertEquals("http://urlTest.com", establishment.getUrl().toString());
    }

}
