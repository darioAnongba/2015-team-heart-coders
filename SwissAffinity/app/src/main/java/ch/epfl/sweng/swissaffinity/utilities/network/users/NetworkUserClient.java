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
    private static final String REGISTRATIONS = "/api/registrations";

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
        try{
            jsonObject.get("rest_user_registration");
            throw new UserClientException("Malformed user jsonObject.");

        } catch (JSONException e1){
            try{
                if( !(jsonObject.get("email") instanceof String) ||
                        !(jsonObject.get("username") instanceof String) ||
                        !(jsonObject.get("firstName") instanceof String) ||
                        !(jsonObject.get("lastName") instanceof String) ||
                        !(jsonObject.get("gender") instanceof String) ||
                        !(jsonObject.get("birthDate") instanceof String) ||
                        !(jsonObject.get("facebookId") instanceof String) ||
                        !(jsonObject.get("plainPassword") instanceof String)){
                    throw new UserClientException("Malformed user jsonObject.");
                }
            }catch (JSONException e2){
                throw new UserClientException(e2);
            }
        }
        final JSONObject jsonRequest = new JSONObject();

        try {
            jsonRequest.put("rest_user_registration", jsonObject);
            String response = mNetworkProvider.postContent(mServerUrl + USERS, jsonRequest);
            return new JSONObject(response);
        } catch (IOException | JSONException e) {
            throw new UserClientException(e);
        }
    }

    @Override
    public String registerUser(String username, int eventId) throws UserClientException {
        if ((username == null) || eventId < 0){
            throw new UserClientException(new IllegalArgumentException());
        }
        String response;
        JSONObject registrationObject = new JSONObject();
        JSONObject requestObject = new JSONObject();

        try {
            registrationObject.put("username", username);
            registrationObject.put("eventId", Integer.toString(eventId));
            requestObject.put("rest_event_registration", registrationObject);
            response = mNetworkProvider.postContent(mServerUrl + REGISTRATIONS, requestObject );
            if (!response.equals("")){
                throw new UserClientException(response);
            }
            return response;
        } catch (IOException | JSONException e){
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
