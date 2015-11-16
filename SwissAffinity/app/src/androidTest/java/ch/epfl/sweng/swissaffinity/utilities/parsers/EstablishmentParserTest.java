package ch.epfl.sweng.swissaffinity.utilities.parsers;


import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.utilities.network.ServerTags;
import ch.epfl.sweng.swissaffinity.utilities.parsers.events.EstablishmentParser;

import static org.junit.Assert.assertEquals;

/**
 * Created by sahinfurkan on 13/11/15.
 */
public class EstablishmentParserTest {
    JSONObject json;
    JSONObject estJson;
    Establishment est;

    @Before
    public void setUp() throws Exception {
        json = DataForTesting.createJSONEvent();
        estJson = json.getJSONObject(ServerTags.ESTABLISHMENT.get());
        est = new EstablishmentParser(estJson).parse();
    }

    @Test
    public void idTest() throws JSONException {
        assertEquals(estJson.getInt("id"), est.getId());
    }

    @Test
    public void nameTest() throws JSONException {
        assertEquals(estJson.getString("name"), est.getName());
    }

    @Test
    public void logoPathTest() throws JSONException {
        assertEquals(estJson.getString("logo_path"), est.getLogoPath());
    }

    @Test
    public void urlTest() throws JSONException {
        assertEquals(estJson.getString("url"), est.getUrl());
    }

    @Test
    public void descriptionTest() throws JSONException {
        assertEquals(estJson.getString("description"), est.getDescription());
    }

    @Test
    public void typeTest() throws JSONException {
        assertEquals(estJson.getString("type"), est.getType().toString().toLowerCase());
    }

}
