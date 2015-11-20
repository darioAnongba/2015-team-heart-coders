package ch.epfl.sweng.swissaffinity.utilities.parsers.events;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static org.junit.Assert.assertEquals;

/**
 * Created by sahinfurkan on 18/11/15.
 */
public class SpeedDatingEventParserTest {

    JSONObject json;
    SpeedDatingEvent event;
    @Before
    public void setUp() throws Exception {
        json = DataForTesting.createJSONEvent();
        event = new SpeedDatingEventParser().parse(new SafeJSONObject(json));
    }

    @Test
    public void menSeatsCheck() throws JSONException {
        assertEquals(json.getInt("men_seats"), event.getMenSeats());
        assertEquals(event.getMenSeats(), 15);
    }

    @Test
    public void womenSeatsCheck() throws JSONException{
        assertEquals(json.getInt("women_seats"), event.getWomenSeats());
        assertEquals(event.getWomenSeats(), 15);
    }

    @Test
    public void menRegisteredTest() throws JSONException {
        assertEquals(json.getInt("num_men_registered"), event.getMenRegistered());
        assertEquals(event.getMenRegistered(), 0);
    }

    @Test
    public void womenRegisteredTest() throws JSONException {
        assertEquals(json.getInt("num_women_registered"), event.getWomenRegistered());
        assertEquals(event.getWomenRegistered(), 0);
    }

    @Test(expected = ParserException.class)
    public void nullTest() throws ParserException, JSONException {
        try {
            json = new JSONObject("{\"id\":4}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        event = new SpeedDatingEventParser().parse(new SafeJSONObject(json));
    }
}
