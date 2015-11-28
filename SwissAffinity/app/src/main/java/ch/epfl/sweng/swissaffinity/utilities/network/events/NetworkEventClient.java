package ch.epfl.sweng.swissaffinity.utilities.network.events;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.users.Registration;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserFactory;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EVENT;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;

/**
 * Representation of an event client with network.
 */
public class NetworkEventClient implements EventClient {

    private static final String EVENTS = "/api/events";
    private static final String USERS = "/api/users/";
    private static final String REGISTRATIONS = "/registrations";
    private static final String LOCATIONS = "/api/locations/";
    private static final String IMAGES = "/images/events/";

    private final String mServerUrl;
    private final NetworkProvider mNetworkProvider;

    /**
     * Constructor of the class
     *
     * @param serverUrl       the server URL
     * @param networkProvider the network provider {@link NetworkProvider}
     */
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
        fetchEvents(events, EVENTS);
        return events;
    }

    @Override
    public List<Registration> fetchForUser(String userName)
        throws EventClientException
    {
        List<Registration> registrations = new ArrayList<>();
        try {
            String url = mServerUrl + USERS + userName + REGISTRATIONS;
            String content = mNetworkProvider.getContent(url);
            JSONArray jsonRegistrations = new JSONArray(content);
            for (int i = 0; i < jsonRegistrations.length(); ++i) {
                JSONObject jsonObject = jsonRegistrations.getJSONObject(i);
                int id = jsonObject.getInt(ID.get());
                SafeJSONObject jsonEvent =
                    new SafeJSONObject(jsonObject.getJSONObject(EVENT.get()));
                Parser<? extends Event> parser = ParserFactory.parserFor(jsonEvent);
                Event event = parser.parse(jsonEvent);
                registrations.add(new Registration(id, event));
            }
        } catch (JSONException | IOException | ParserException e) {
            throw new EventClientException(e);
        }
        return registrations;
    }

    @Override
    public List<Event> fetchAllFor(Collection<Location> locations) throws EventClientException {
        if (locations == null) {
            throw new IllegalArgumentException();
        }
        List<Event> events = new ArrayList<>();
        for (Location location : locations) {
            int id = location.getId();
            fetchEvents(events, LOCATIONS + id + EVENTS);
        }
        return events;
    }

    @Override
    public Event fetchBy(int id) throws EventClientException {
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        try {
            String content = mNetworkProvider.getContent(mServerUrl + EVENTS + "/" + id);
            SafeJSONObject jsonObject = new SafeJSONObject(content);
            Parser<? extends Event> parser = ParserFactory.parserFor(jsonObject);
            return parser.parse(jsonObject);
        } catch (IOException | ParserException | JSONException e) {
            throw new EventClientException(e);
        }
    }

    @Override
    public Bitmap imageFor(String imagePath) throws EventClientException {
        if (imagePath == null) {
            throw new IllegalArgumentException();
        }
        Bitmap image;
        try {
            image =
                BitmapFactory.decodeStream(
                    mNetworkProvider.getConnection(mServerUrl + IMAGES + imagePath)
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
