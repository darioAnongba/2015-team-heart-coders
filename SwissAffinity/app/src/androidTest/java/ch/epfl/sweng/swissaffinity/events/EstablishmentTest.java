package ch.epfl.sweng.swissaffinity.events;

import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.swissaffinity.DataForTesting;

import static org.junit.Assert.assertEquals;

public class EstablishmentTest {

    // IllegalArgumentException

    private Establishment establishment;

    @Before
    public void setup() {
        establishment = DataForTesting.ESTABLISHMENTS.get(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIDException() {
        establishment = new Establishment(
                -1,
                "King Size Pub",
                Establishment.Type.BAR,
                DataForTesting.ADRESSES.get(1),
                "phoneTest",
                "testDescription",
                "",
                100,
                "logoTest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNameException() {
        establishment = new Establishment(
                1,
                null,
                Establishment.Type.BAR,
                DataForTesting.ADRESSES.get(1),
                "phoneTest",
                "testDescription",
                "",
                100,
                "logoTest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTypeException() {
        establishment = new Establishment(
                1,
                "King Size Pub",
                null,
                DataForTesting.ADRESSES.get(1),
                "phoneTest",
                "testDescription",
                "",
                100,
                "logoTest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddressException() {
        establishment = new Establishment(
                1,
                "King Size Pub",
                Establishment.Type.BAR,
                null,
                "phoneTest",
                "testDescription",
                "",
                100,
                "logoTest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPhoneNumberException() {
        establishment = new Establishment(
                1,
                "King Size Pub",
                Establishment.Type.BAR,
                DataForTesting.ADRESSES.get(1),
                null,
                "testDescription",
                "",
                100,
                "logoTest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDescriptionException() {
        establishment = new Establishment(
                1,
                "King Size Pub",
                Establishment.Type.BAR,
                DataForTesting.ADRESSES.get(1),
                "phoneTest",
                null,
                "",
                100,
                "logoTest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxSeatException() {
        establishment = new Establishment(
                1,
                "King Size Pub",
                Establishment.Type.BAR,
                DataForTesting.ADRESSES.get(1),
                "phoneTest",
                "testDescription",
                "",
                -10,
                "logoTest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLogoPathException() {
        establishment = new Establishment(
                1,
                "King Size Pub",
                Establishment.Type.BAR,
                DataForTesting.ADRESSES.get(1),
                "phoneTest",
                "testDescription",
                "",
                100,
                null);
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
        assertEquals("", establishment.getUrl());
    }

}
