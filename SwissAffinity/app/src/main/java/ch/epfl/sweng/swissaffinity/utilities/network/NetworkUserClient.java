package ch.epfl.sweng.swissaffinity.utilities.network;

import org.json.JSONArray;
import org.json.JSONObject;

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
        //TODO : fectch user by username
     return null;
    }

    @Override
    public User fetchByID(int id) {
        //TODO : fetech user by id
        return null;
    }
}
