package ch.epfl.sweng.swissaffinity.events;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;

import static org.junit.Assert.assertEquals;

/**
 * Created by yannick on 13.11.15.
 */
public class SpeedDatingEventTest {

    private SpeedDatingEvent speedDatingEvent;
    Date date1;
    Date date2;

    @Before
    public void setup() throws ParserException {
        speedDatingEvent = DataForTesting.speedDatingEventCreator();
        try {
            date1 = DateParser.parseFromString("2015-10-31T20:00:00+0100");
            date2 = DateParser.parseFromString("2015-10-31T23:59:59+0100");
        } catch (ParserException e) {
            throw new ParserException("Problem with Date creation");
        }
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

/*    @Test
    public void testGetBasePrice() {
        assertEquals( 49.95, speedDatingEvent.getBasePrice());
    }*/

    @Test
    public void testGetDateBegin() {
        assertEquals(date1, speedDatingEvent.getDateBegin());
    }

    @Test
    public void testGetDateEnd() {
        assertEquals(date2, speedDatingEvent.getDateEnd());
    }

    @Test
    public void testGetLocation() {
        assertEquals(DataForTesting.LOCATIONS.get(0).getName(), speedDatingEvent.getLocation().getName());
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
