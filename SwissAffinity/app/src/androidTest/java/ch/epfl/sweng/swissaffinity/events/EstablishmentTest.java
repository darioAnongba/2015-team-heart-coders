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
        assertEquals("Cafe Cuba", establishment.getmName());
    }

    @Test
    public void testGetDescription() {
        assertEquals("testDescription", establishment.getmDescription());
    }

    @Test
    public void testGetLogoPath() {
        assertEquals("logoTest", establishment.getmLogoPath());
    }

    @Test
    public void testGetAddress() {
        assertEquals(DataForTesting.ADRESSES.get(0), establishment.getmAddress());
    }

    @Test
    public void testGetType() {
        assertEquals(Establishment.Type.BAR, establishment.getmType());
    }

    @Test
    public void testGetUrl() {
        assertEquals("http://urlTest.com", establishment.getmUrl().toString());
    }

}
