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


/**
 * Representation of a user client.
 * Modified by Dario on 09.12.2015
 */
public class NetworkUserClient implements UserClient {

    private static final String USERS = "/api/users";
    private static final String REGISTRATIONS = "/api/registrations";
    private static final String EVENT_REGISTRATION = "/api/users/registrations";

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
     * @param jsonObject The JSON object representing the new User.
     * @return User object formatted as json by the server.
     * @throws UserClientException when one of the Register fields does not have a correct format or other errors.
     *                             In the case, the cause for the exception is format all errors are appended to be displayed in the toast
     *                             of RegisterActivity.
     */
    @Override
    public JSONObject postUser(JSONObject jsonObject) throws UserClientException {
        if (jsonObject == null) {
            throw new IllegalArgumentException();
        }
        JSONObject jsonRequest = new JSONObject();
        JSONObject jsonResponse;

        try {
            jsonRequest.put(REST_USER_REGISTRATION.get(), jsonObject);
            String response = mNetworkProvider.postContent(mServerUrl + USERS, jsonRequest);
            jsonResponse = new JSONObject(response);
        } catch (IOException | JSONException e) {
            throw new UserClientException(e);
        }
        StringBuilder error = new StringBuilder();
        try {
            error.append(jsonResponse.get("message")).append(": ");
        } catch (JSONException e) {
            return jsonResponse;
        }

        try {
            JSONObject errorsJSON = jsonResponse.getJSONObject("errors").getJSONObject("children");
            try {
                error.append(errorsJSON.getJSONObject("email").getJSONArray("errors").getString(0))
                     .append(" ");
            } catch (JSONException e) {
                Log.e(FIELD_SUBMIT_OK, "EMAIL");
            }
            try {
                error.append(
                    errorsJSON.getJSONObject("username")
                              .getJSONArray("errors")
                              .getString(0)).append(" ");
            } catch (JSONException e) {
                Log.e(FIELD_SUBMIT_OK, "USERNAME");
            }
            try {
                error.append(
                    errorsJSON.getJSONObject("firstName")
                              .getJSONArray("errors")
                              .getString(0)).append(" ");
            } catch (JSONException e) {
                Log.e(FIELD_SUBMIT_OK, "FIRST_NAME");
            }
            try {
                error.append(
                    errorsJSON.getJSONObject("lastName")
                              .getJSONArray("errors")
                              .getString(0)).append(" ");
            } catch (JSONException e) {
                Log.e(FIELD_SUBMIT_OK, "LAST_NAME");
            }
            try {
                error.append(errorsJSON.getJSONObject("gender").getJSONArray("errors").getString(0))
                     .append(" ");
            } catch (JSONException e) {
                Log.e(FIELD_SUBMIT_OK, "GENDER");
            }
            try {
                error.append(
                    errorsJSON.getJSONObject("birthDate")
                              .getJSONArray("errors")
                              .getString(0)).append(" ");
            } catch (JSONException e) {
                Log.e(FIELD_SUBMIT_OK, "BIRTH_DATE");
            }
            try {
                error.append(
                    errorsJSON.getJSONObject("facebookId")
                              .getJSONArray("errors")
                              .getString(0)).append(" ");
            } catch (JSONException e) {
                Log.e(FIELD_SUBMIT_OK, "FACEBOOK_ID");
            }
            try {
                error.append(
                    errorsJSON.getJSONObject("plainPassword")
                              .getJSONArray("errors")
                              .getString(0)).append(" ");
            } catch (JSONException e) {
                Log.e(FIELD_SUBMIT_OK, "PASSWORD");
            }

            throw new UserClientException(error.toString());
        } catch (JSONException e) {
            throw new UserClientException(e);
        }

    }

    @Override
    public void deleteUser(String userName) throws UserClientException {
        if (userName == null) {
            throw new IllegalArgumentException();
        }
        try {
            String response = mNetworkProvider.deleteContent(mServerUrl + USERS + "/" + userName);
            if (response != null && !response.equals("")) {
                SafeJSONObject jsonResponse;
                String errorMessage;
                try {
                    jsonResponse = new SafeJSONObject(response);
                    errorMessage = jsonResponse.get("message", SafeJSONObject.DEFAULT_STRING);
                } catch (JSONException e) {
                    throw new UserClientException(e);
                }
                throw new UserClientException(errorMessage);
            }
        } catch (IOException e) {
            throw new UserClientException(e);
        }
    }

    @Override
    public void registerUser(String username, int eventId) throws UserClientException {
        if (username == null || eventId < 0) {
            throw new IllegalArgumentException();
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonObject.put(USERNAME.get(), username);
            jsonObject.put(EVENT_ID.get(), Integer.toString(eventId));
            jsonRequest.put(REST_EVENT_REGISTRATION.get(), jsonObject);
            //An error log represents an exception for the service the UserClient is supposed to deliver.
            //This is not the case in the layer below e.g. NetworkProvider.
            String
                response =
                mNetworkProvider.postContent(mServerUrl + EVENT_REGISTRATION, jsonRequest);
            if (response == null || !response.equals("")) {
                throw new UserClientException(response);
            }
        } catch (IOException | JSONException e) {
            throw new UserClientException(e);
        }
    }

    @Override
    public void unregisterUser(int registrationId) throws UserClientException {
        if (registrationId < 0) {
            throw new IllegalArgumentException();
        }
        try {
            String url = mServerUrl + REGISTRATIONS + "/" + registrationId;
            String response = mNetworkProvider.deleteContent(url);

            if (response != null && !response.equals("")) {
                SafeJSONObject jsonResponse;
                String errorMessage;
                try {
                    jsonResponse = new SafeJSONObject(response);
                    errorMessage = jsonResponse.get("message", SafeJSONObject.DEFAULT_STRING);
                } catch (JSONException e) {
                    throw new UserClientException(e);
                }
                throw new UserClientException(errorMessage);
            }
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
