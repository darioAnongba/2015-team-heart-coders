package ch.epfl.sweng.swissaffinity.utilities.network.events;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.ServerTags;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserFactory;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.*;

/**
 * Representation of an event client with network.
 */
public class NetworkEventClient implements EventClient {

    private static final String API = "/api";
    private static final String EVENTS = "/events";
    private static final String USERS = "/users/";
    private static final String REGISTRATIONS = "/registrations";
    private static final String LOCATIONS = "/locations/";
    private static final String IMAGES = "/images/events/";

    private final String mServerUrl;
    private final NetworkProvider mNetworkProvider;

    public NetworkEventClient(String serverUrl, NetworkProvider networkProvider) {
        if (networkProvider == null || serverUrl == null) {
            throw new IllegalArgumentException();
        }
        mServerUrl = serverUrl;
        mNetworkProvider = networkProvider;
    }

    @Override
    public List<Event> fetchAll() throws EventClientException {
        List<Event> events = new ArrayList<>();

        fetchEvents(events, API + EVENTS);
        return events;
    }

    @Override
    public List<Event> fetchAllForUser(String userName) throws EventClientException {
        List<Event> events = new ArrayList<>();
        try {
            String content = mNetworkProvider.getContent(mServerUrl + API + USERS + userName + REGISTRATIONS);
            JSONArray jsonRegistrations = new JSONArray(content);
            for (int i = 0; i < jsonRegistrations.length(); ++i) {
                SafeJSONObject jsonObject =
                        new SafeJSONObject(jsonRegistrations.getJSONObject(i).getJSONObject(EVENT.get()));
                Parser<? extends Event> parser = ParserFactory.parserFor(jsonObject);
                Event event = parser.parse(jsonObject);
                events.add(event);
            }
        } catch (ParserException | JSONException | IOException e) {
            throw new EventClientException(e);
        }
        return events;
    }

    @Override
    public List<Event> fetchAllFor(Collection<Location> locations) throws EventClientException {
        if (locations == null) {
            throw new IllegalArgumentException();
        }
        List<Event> events = new ArrayList<>();
        for (Location location : locations) {
            int id = location.getId();
            fetchEvents(events, API + LOCATIONS + id + EVENTS);
        }
        return events;
    }

    @Override
    public Event fetchBy(int id) throws EventClientException {
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        try {
            String content = mNetworkProvider.getContent(mServerUrl + API + EVENTS + "/" + id);
            SafeJSONObject jsonObject = new SafeJSONObject(content);
            Parser<? extends Event> parser = ParserFactory.parserFor(jsonObject);
            return parser.parse(jsonObject);
        } catch (IOException | ParserException | JSONException e) {
            throw new EventClientException(e);
        }
    }

    @Override
    public Bitmap imageFor(Event event) throws EventClientException {
        if (event == null) {
            throw new IllegalArgumentException();
        }
        Bitmap image;
        try {
            String imagePath = mServerUrl + IMAGES + event.getImagePath();
            URL url = new URL(imagePath);
            image = BitmapFactory.decodeStream(
                    mNetworkProvider.getConnection(url)
                            .getInputStream());
        } catch (IOException e) {
            throw new EventClientException(e);
        }
        return image;
    }

    private void fetchEvents(List<Event> events, String apiUrl) throws EventClientException {
        try {
            String content = mNetworkProvider.getContent(mServerUrl + apiUrl);
            JSONArray jsonEvents = new JSONArray(content);
            for (int i = 0; i < jsonEvents.length(); ++i) {
                SafeJSONObject jsonObject = new SafeJSONObject(jsonEvents.getJSONObject(i));
                Parser<? extends Event> parser = ParserFactory.parserFor(jsonObject);
                Event event = parser.parse(jsonObject);
                events.add(event);
            }
        } catch (ParserException | JSONException | IOException e) {
            throw new EventClientException(e);
        }
    }
}
