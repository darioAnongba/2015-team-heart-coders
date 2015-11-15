package ch.epfl.sweng.swissaffinity.utilities.network.users;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.user.UserParser;


/**
 * User Client parser Created by Max on 06/11/2015.
 */
public class NetworkUserClient implements UserClient {
    private static final String SERVER_API_USERS = "/api/users/";

    private final String mServerUrl;
    private final NetworkProvider mNetworkProvider;

    public NetworkUserClient(String serverUrl, NetworkProvider networkProvider) {
        mServerUrl = serverUrl;
        mNetworkProvider = networkProvider;
    }


    @Override
    public User fetchByUsername(String userName) throws UserClientException {
        User user = null;
        try {
            String content =
                    mNetworkProvider.getContent(mServerUrl + SERVER_API_USERS + userName);


            JSONObject jsonObject = new JSONObject(content);
            user = new UserParser(jsonObject).parse();

        } catch (JSONException | ParserException | IOException e) {
            throw new UserClientException(e);
        }

        return user;
    }

    @Override
    public User fetchByIDOrFacebookId(String id) throws UserClientException {
        User user = null;
        try {

            String content = mNetworkProvider.getContent(mServerUrl + SERVER_API_USERS + id);
            JSONObject jsonObject = new JSONObject(content);
            user = new UserParser(jsonObject).parse();

        } catch (JSONException | ParserException | IOException e) {
            throw new UserClientException(e);
        }

        return user;
    }


}
