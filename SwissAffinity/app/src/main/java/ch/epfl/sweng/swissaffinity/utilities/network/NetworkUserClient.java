package ch.epfl.sweng.swissaffinity.utilities.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.Location;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.users.UserClientException;

/**
 * Created by Max on 06/11/2015.
 */
public class NetworkUserClient implements UserClient {
    private static final String SERVER_API_USERS = "users";

    private final String mServerUrl;
    private final NetworkProvider mNetworkProvider;

    public NetworkUserClient(String serverUrl, NetworkProvider networkProvider) {
        mServerUrl = serverUrl;
        mNetworkProvider = networkProvider;
    }

    @Override
    public User fetchByUsername(String userName) throws UserClientException {
        User user =null;
        try {
            String content = mNetworkProvider.getContent(mServerUrl + SERVER_API_USERS + "/" + userName);

                JSONObject jsonUser = new JSONObject(content);

            Parsable<Event> parsable = (Parsable<Event>)ParserFactory.parserFor(jsonUser);
            Event event = parsable.parseFromJSON(jsonUser);
            } catch (JSONException | ParserException | IOException e) {
                throw new UserClientException();
            }

        return user;
    }

    @Override
    public User fetchByID(int id) {
        //TODO : fetech user by id
        return null;
    }
}
