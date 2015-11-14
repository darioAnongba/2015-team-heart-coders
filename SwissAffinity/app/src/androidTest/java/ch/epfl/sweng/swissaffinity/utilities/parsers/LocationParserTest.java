package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.utilities.Location;

import static org.junit.Assert.assertEquals;

/**
 * Created by sahinfurkan on 14/11/15.
 */
public class LocationParserTest {

    JSONObject json;
    Location location;

    @Before
    public void setUp() throws ParserException, JSONException {
        json = (new DataForTesting()).createJSONEvent().getJSONObject("location");
        location = new LocationParser(json).parse();
    }

    @Test
    public void nameTest() throws JSONException {
        assertEquals(location.getName(), json.getString("name"));
    }
}
