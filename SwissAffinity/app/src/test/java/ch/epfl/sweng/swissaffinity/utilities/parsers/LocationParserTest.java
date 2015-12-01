package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import ch.epfl.sweng.swissaffinity.BuildConfig;
import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.ServerTags;

import static org.junit.Assert.assertEquals;

/**
 * Created by sahinfurkan on 14/11/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class LocationParserTest {

    JSONObject jsonLocation;
    Location location;

    @Before
    public void setUp() throws ParserException, JSONException {
        jsonLocation = DataForTesting.createJSONEvent().getJSONObject(ServerTags.LOCATION.get());
        location = new LocationParser().parse(new SafeJSONObject(jsonLocation));
    }

    @Test
    public void nameTest() throws JSONException {
        assertEquals(location.getName(), jsonLocation.getString(ServerTags.NAME.get()));
    }
}
