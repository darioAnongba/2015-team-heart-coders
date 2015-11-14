package ch.epfl.sweng.swissaffinity.utilities.network.users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.parsers.AddressParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;
import ch.epfl.sweng.swissaffinity.utilities.parsers.user.UserParser;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BIRTH_DATE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ENABLED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FACEBOOKID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.GENDER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.HOME_PHONE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOCATIONS_INTEREST;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOCKED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MOBILE_PHONE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.PROFESSION;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.PROFILE_PICTURE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

/**
 * User Client parser Created by Max on 06/11/2015.
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
        User user = null;
        try {
            String content =
                    mNetworkProvider.getContent(mServerUrl + SERVER_API_USERS + "/" + userName);

            JSONObject jsonUser = new JSONObject(content);
            user = new UserParser(jsonUser).parse();

        } catch (JSONException | ParserException | IOException e) {
            throw new UserClientException();
        }

        return user;
    }

    @Override
    public User fetchByIDOrFacebookId(int id) throws UserClientException {
        User user = null;
        try {
            String content = mNetworkProvider.getContent(mServerUrl + SERVER_API_USERS + "/" + id);

            JSONObject jsonUser = new JSONObject(content);
            user = new UserParser(jsonUser).parse();

        } catch (JSONException | ParserException | IOException e) {
            throw new UserClientException();
        }

        return user;
    }


}
