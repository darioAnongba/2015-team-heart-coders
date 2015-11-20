package ch.epfl.sweng.swissaffinity.events;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;

import static org.junit.Assert.assertEquals;

public class SpeedDatingEventTest {

    private SpeedDatingEvent speedDatingEvent;
    private SpeedDatingEvent.Builder builder;
    private Date date1;
    private Date date2;

    @Before
    public void setup() throws ParserException {
        speedDatingEvent = DataForTesting.createSpeedDatingEvent();
        builder = new SpeedDatingEvent.Builder();

        try {
            date1 = DateParser.parseFromString("2015-10-31T20:00:00+0100");
            date2 = DateParser.parseFromString("2015-10-31T23:59:59+0100");
        } catch (ParserException e) {
            throw new ParserException("Problem with Date creation");
        }
    }

    @Test
    public void testGetDescription() {
        assertEquals("test description for Halloween", speedDatingEvent.getDescription());
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


    @Test(expected = IllegalArgumentException.class)
    public void testIDException() {
        builder.setId(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNameException() {
        builder.setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLocationException() {
        builder.setLocation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxPeopleException() {
        builder.setMaxPeople(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDateBeginException() {
        builder.setDateBegin(null).setDateEnd(date2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDateEndException() {
        builder.setDateBegin(date1).setDateEnd(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBasePriceException() {
        builder.setBasePrice(-10.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStateException() {
        builder.setState(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDescriptionException() {
        builder.setDescription(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIamgePathException() {
        builder.setImagePath(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLastUpdateException() {
        builder.setLastUpdate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMenSeatsException() {
        builder.setMenSeats(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWomenSeatsException() {
        builder.setWomenSeats(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMenRegisteredException() {
        builder.setMenRegistered(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWomenRegisteredException() {
        builder.setWomenRegistered(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinAgeException() {
        builder.setMinAge(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxAgeException() {
        builder.setMaxAge(-10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxSuperiorToMinException() {
        builder.setMinAge(20).setMaxAge(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEstablishmentException() {
        builder.setEstablishment(null);
    }
}
