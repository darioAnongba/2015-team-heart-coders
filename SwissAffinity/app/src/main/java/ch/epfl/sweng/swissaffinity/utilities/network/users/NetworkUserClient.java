package ch.epfl.sweng.swissaffinity.utilities.network.users;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;
import ch.epfl.sweng.swissaffinity.utilities.parsers.user.UserParser;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EVENT_ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.REST_EVENT_REGISTRATION;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.REST_USER_REGISTRATION;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;


/**
 * Representation of a user client.
 */
public class NetworkUserClient implements UserClient {

    private static final String USERS = "/api/users";
    private static final String REGISTRATIONS = "/api/registrations";

    private final String mServerUrl;
    private final NetworkProvider mNetworkProvider;

    public static final String FIELD_SUBMIT_OK = "Reg-field fmt correct.";

    /**
     * Constructor of the class
     *
     * @param serverUrl       the server url
     * @param networkProvider the network provider {@link NetworkProvider}
     */
    public NetworkUserClient(String serverUrl, NetworkProvider networkProvider) {
        if (networkProvider == null || serverUrl == null) {
            throw new IllegalArgumentException();
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

    /**
     *
     * @param jsonObject
     * @return User object formatted as json by the server.
     * @throws UserClientException when one of the Register fields does not have a correct format or other errors.
     * In the case, the cause for the exception is format all errors are appended to be displayed in the toast
     * of RegisterActivity.
     */
    @Override
    public JSONObject postUser(JSONObject jsonObject) throws UserClientException {
        if (jsonObject == null) {
            throw new IllegalArgumentException();
        }
        JSONObject jsonRequest = new JSONObject();
        JSONObject jsonResponse;

        /*
        DO NOT CHANGE/CLEAN THIS CODE WITHOUT ASKING OR COMPLETELY UNDERSTAND THE EXCEPTIONS FLOW.
         */
        try {
            jsonRequest.put(REST_USER_REGISTRATION.get(), jsonObject);
            String response = mNetworkProvider.postContent(mServerUrl + USERS, jsonRequest);
            jsonResponse = new JSONObject(response);
        } catch (IOException | JSONException e) {
            throw new UserClientException(e);
        }

        StringBuilder error = new StringBuilder();
        try{
            error.append(jsonResponse.get("message")+": ");
        } catch (JSONException e){
            return jsonResponse;
        }

        try{
            JSONObject errorsJSON = jsonResponse.getJSONObject("errors").getJSONObject("children");
            try {
                error.append(errorsJSON.getJSONObject("email").getJSONArray("errors").getString(0) + " ");
            } catch (JSONException e){
                Log.e(FIELD_SUBMIT_OK,"EMAIL");
            }
            try {
                error.append(errorsJSON.getJSONObject("username").getJSONArray("errors").getString(0) + " ");
            } catch (JSONException e){
                Log.e(FIELD_SUBMIT_OK,"USERNAME");
            }
            try {
                error.append(errorsJSON.getJSONObject("firstName").getJSONArray("errors").getString(0)+ " ");
            } catch (JSONException e){
                Log.e(FIELD_SUBMIT_OK,"FIRST_NAME");
            }
            try {
                error.append(errorsJSON.getJSONObject("lastName").getJSONArray("errors").getString(0)+ " ");
            } catch (JSONException e){
                Log.e(FIELD_SUBMIT_OK,"LAST_NAME");
            }
            try {
                error.append(errorsJSON.getJSONObject("gender").getJSONArray("errors").getString(0)+ " ");
            } catch (JSONException e){
                Log.e(FIELD_SUBMIT_OK,"GENDER");
            }
            try {
                error.append(errorsJSON.getJSONObject("birthDate").getJSONArray("errors").getString(0)+ " ");
            } catch (JSONException e){
                Log.e(FIELD_SUBMIT_OK,"BIRTH_DATE");
            }
            try {
                error.append(errorsJSON.getJSONObject("facebookId").getJSONArray("errors").getString(0)+ " ");
            } catch (JSONException e){
                Log.e(FIELD_SUBMIT_OK,"FACEBOOK_ID");
            }
            try {
                error.append(errorsJSON.getJSONObject("plainPassword").getJSONArray("errors").getString(0)+ " ");
            } catch (JSONException e){
                Log.e(FIELD_SUBMIT_OK,"PASSWORD");
            }

            throw new UserClientException(error.toString());
        } catch (JSONException e){
            throw new UserClientException(e);
        }

    }

    @Override
    public int deleteUser(String userName) throws UserClientException {
        if (userName == null) {
            throw new IllegalArgumentException();
        }
        try {
            return mNetworkProvider.deleteContent(mServerUrl + USERS + "/" + userName);
        } catch (IOException e) {
            throw new UserClientException(e);
        }
    }

    @Override
    public int registerUser(String username, int eventId) throws UserClientException {
        if (username == null || eventId < 0) {
            throw new IllegalArgumentException();
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonObject.put(USERNAME.get(), username);
            jsonObject.put(EVENT_ID.get(), Integer.toString(eventId));
            jsonRequest.put(REST_EVENT_REGISTRATION.get(), jsonObject);
            String response = mNetworkProvider.postContent(mServerUrl + REGISTRATIONS, jsonRequest);
            return response.equals("") ? HTTP_NO_CONTENT : -1;
        } catch (IOException | JSONException e) {
            throw new UserClientException(e);
        }
    }

    @Override
    public int unregisterUser(int registrationId) throws UserClientException {
        if (registrationId < 0) {
            throw new IllegalArgumentException();
        }
        try {
            String url = mServerUrl + REGISTRATIONS + "/" + registrationId;
            return mNetworkProvider.deleteContent(url);
        } catch (IOException e) {
            throw new UserClientException(e);
        }
    }

    /**
     * Fetch a user
     *
     * @param nameOrId its name or id
     * @return the user
     * @throws UserClientException
     */
    private User fetch(String nameOrId) throws UserClientException {
        try {
            String content = mNetworkProvider.getContent(mServerUrl + USERS + "/" + nameOrId);
            SafeJSONObject jsonObject = new SafeJSONObject(content);
            return new UserParser().parse(jsonObject);
        } catch (ParserException | JSONException | IOException e) {
            throw new UserClientException(e);
        }
    }
}
