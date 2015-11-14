package ch.epfl.sweng.swissaffinity.utilities.network.events;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parsable;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserFactory;

public class NetworkEventClient implements EventClient {
    private static final String SERVER_API_EVENTS = "/api/events";
    private static final String SERVER_API_IMAGES = "/images/events/";

    private final String mServerUrl;
    private final NetworkProvider mNetworkProvider;

    public NetworkEventClient(String serverUrl, NetworkProvider networkProvider) {
        mServerUrl = serverUrl;
        mNetworkProvider = networkProvider;
    }

    @Override
    public List<Event> fetchAll() throws EventClientException {
        List<Event> events = new ArrayList<>();
        try {
            String content = mNetworkProvider.getContent(mServerUrl + SERVER_API_EVENTS);
            JSONArray jsonEvents = new JSONArray(content);
            for (int i = 0; i < jsonEvents.length(); ++i) {
                JSONObject jsonObject = jsonEvents.getJSONObject(i);
                Parsable<Event> parsable = (Parsable<Event>) ParserFactory.parserFor(jsonObject);
                Event event = parsable.parseFromJSON(jsonObject);
                events.add(event);
            }
        } catch (Exception e) {
            throw new EventClientException(e);
        }
        return events;
    }

    @Override
    public List<Event> fetchAllFor(Collection<Location> locations) throws EventClientException {
        if (locations == null) {
            throw new NullPointerException();
        }
        if (locations.isEmpty()) {
            return fetchAll();
        }
        //TODO: implement
        return null;
    }

    @Override
    public Event fetchBy(int id) throws EventClientException {
        return null;
    }

    @Override
    public Bitmap imageFor(Event event) throws EventClientException {
        String imagePath = mServerUrl + SERVER_API_IMAGES + event.getImagePath();
        Bitmap image;
        try {
            URL url = new URL(imagePath);
            image = BitmapFactory.decodeStream(
                    mNetworkProvider.getConnection(url)
                            .getInputStream());
        } catch (IOException e) {
            throw new EventClientException(e);
        }
        return image;
    }
}
