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
import ch.epfl.sweng.swissaffinity.utilities.Address;

import static org.junit.Assert.assertEquals;

/**
 * Created by sahinfurkan on 14/11/15.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class AddressParserTest {

    Address address;
    JSONObject json;
    @Before
    public void setUp() throws JSONException, ParserException {
        json = DataForTesting.createJSONEvent().getJSONObject("establishment").getJSONObject("address");
        address = new AddressParser().parse(new SafeJSONObject(json));
    }

    @Test
    public void countryTest() throws JSONException {
        assertEquals(json.getString("country"), address.getCountry());
    }
    @Test
    public void zipCodeTest() throws JSONException {
        assertEquals(json.getInt("zip_code"), address.getZipCode());

    }
    @Test
    public void cityTest() throws JSONException {
        assertEquals(json.getString("city"), address.getCity());

    }
    @Test
    public void provinceTest() throws JSONException {
        assertEquals(json.getString("province"), address.getProvince());

    }
    @Test
    public void streetNumTest() throws JSONException {
        assertEquals(json.getInt("street_number"), address.getStreetNumber());

    }
    @Test
    public void streetTest() throws JSONException {
        assertEquals(json.getString("street"), address.getStreet());

    }
}
