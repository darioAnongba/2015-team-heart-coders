package ch.epfl.sweng.swissaffinity.utilities.network.users;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;
import ch.epfl.sweng.swissaffinity.utilities.parsers.user.UserParser;


/**
 * Representation of a user client.
 */
public class NetworkUserClient implements UserClient {

    //DO NOT CHANGE THIS STRING!! It can cause various bugs.
    private static final String USERS = "/api/users";

    private final String mServerUrl;
    private final NetworkProvider mNetworkProvider;

    public NetworkUserClient(String serverUrl, NetworkProvider networkProvider) {
        if (networkProvider == null){
            throw new IllegalArgumentException("Null networkProvider");
        }
        mServerUrl = serverUrl;
        mNetworkProvider = networkProvider;
    }

    @Override
    public User fetchByUsername(String userName) throws UserClientException {
        return fetch(userName);
    }

    @Override
    public User fetchByFacebookID(String id) throws UserClientException {
        return fetch(id);
    }


    @Override
    public JSONObject postUser(JSONObject jsonObject) throws UserClientException {
        try {
            String content = mNetworkProvider.postContent(mServerUrl + USERS,jsonObject);
            return new JSONObject(content);
        } catch (IOException | JSONException e) {
            throw new UserClientException(e);
        }
    }


    private User fetch(String nameOrId) throws UserClientException {
        try {
            String content =
                    mNetworkProvider.getContent(mServerUrl + USERS +"/"+ nameOrId);
            SafeJSONObject jsonObject = new SafeJSONObject(content);
            return new UserParser().parse(jsonObject);
        } catch (ParserException | JSONException | IOException e) {
            throw new UserClientException(e);
        }
    }
}
