package ch.epfl.sweng.swissaffinity.events;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;

/**
 * Created by yannick on 13.11.15.
 */
public class SpeedDatingEventTest extends TestCase {

    private SpeedDatingEvent speedDatingEvent;

    @Before
    public void setup() throws ParserException {
        speedDatingEvent = DataForTesting.speedDatingEventCreator();
    }

    @Test
    public void testGetDescription() {
        assertEquals("test description for Halloween", speedDatingEvent.getDesription());
    }

    @Test
    public void testGetImagePath() {
        assertEquals("image test Path", speedDatingEvent.getImagePath());
    }

    @Test
    public void testGetName() {
        assertEquals("Halloween Speed Dating Geneva", speedDatingEvent.getName());
    }

    @Test
    public void testGetBasePrice() {
        assertEquals(49.95, speedDatingEvent.getBasePrice());
    }

    @Test
    public void testGetDateBegin() {
        assertEquals(new Date(), speedDatingEvent.getDateBegin());
    }

    @Test
    public void testGetDateEnd() {
        assertEquals(new Date(), speedDatingEvent.getDateEnd());
    }

    @Test
    public void testGetLocation() {
        assertEquals(DataForTesting.LOCATIONS.get(0), speedDatingEvent.getLocation());
    }

    @Test
    public void testGetMaxPeople() {
        assertEquals(20, speedDatingEvent.getMaxPeople());
    }

    @Test
    public void testGetMenRegistered() {
        assertEquals(0, speedDatingEvent.getMenRegistered());
    }

    @Test
    public void testGetWomenRegistered() {
        assertEquals(0, speedDatingEvent.getWomenRegistered());
    }

    @Test
    public void testGetMenSeats() {
        assertEquals(10, speedDatingEvent.getMenSeats());
    }

    @Test
    public void testGetWomenSeats() {
        assertEquals(10, speedDatingEvent.getWomenSeats());
    }

}
