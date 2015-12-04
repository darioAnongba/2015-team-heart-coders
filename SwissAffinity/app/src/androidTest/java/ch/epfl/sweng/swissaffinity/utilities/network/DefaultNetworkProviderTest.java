package ch.epfl.sweng.swissaffinity.utilities.network;

import android.test.suitebuilder.annotation.LargeTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.events.NetworkEventClient;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserFactory;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;
import ch.epfl.sweng.swissaffinity.utilities.parsers.events.SpeedDatingEventParser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@LargeTest
public class DefaultNetworkProviderTest {

    private DefaultNetworkProvider defaultNetworkProvider = new DefaultNetworkProvider();
    private String wrongURL = "test";

    @Test(expected = IllegalArgumentException.class)
    public void testGetConnectionWithWrongURL() throws IOException {
        defaultNetworkProvider.getConnection(wrongURL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetContentWithWrongURL() throws IOException {
        defaultNetworkProvider.getContent(wrongURL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostContentWithWrongURL() throws IOException, JSONException {
        defaultNetworkProvider.postContent(wrongURL, new JSONObject("{\"value\": \"test\"}"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPostContentWithNullJSONObject() throws IOException {
        defaultNetworkProvider.postContent(NetworkProvider.SERVER_URL, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteContentWithWrongURL() throws IOException {
        defaultNetworkProvider.deleteContent(wrongURL);
    }


}
