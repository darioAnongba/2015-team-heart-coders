package ch.epfl.sweng.swissaffinity.utilities.network.users;

import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.users.User;

/**
 * An interface for a user client implementation.
 */
public interface UserClient {

    /**
     * Fetch a user regarding his user name
     *
     * @param userName the user name
     * @return the user
     * @throws UserClientException
     */
    User fetchByUsername(String userName) throws UserClientException;

    /**
     * Fetch a user regarding his ID or Facebook ID
     *
     * @param facebookId the user facebook ID
     * @return the user
     * @throws UserClientException
     */
    User fetchByFacebookID(String facebookId) throws UserClientException;

    /**
     * Post a new user to the server.
     *
     * @param jsonUserObject a json user
     * @return the json object back from server.
     * @throws UserClientException
     */
    JSONObject postUser(JSONObject jsonUserObject) throws UserClientException;

    /**
     * Delete a user from the server
     *
     * @param userName the user name
     * @throws UserClientException
     */
    void deleteUser(String userName) throws UserClientException;

    /**
     * Register a user to an event.
     *
     * @param username the name of the user.
     * @param eventId  the ID of the event.
     * @throws UserClientException
     */
    void registerUser(String username, int eventId) throws UserClientException;

    /**
     * Unregister a user from an event
     *
     * @param registrationId the registration ID {@link ch.epfl.sweng.swissaffinity.users.Registration}
     * @throws UserClientException
     */
    void unregisterUser(int registrationId) throws UserClientException;
}
