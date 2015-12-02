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
     *
     * @return the user
     *
     * @throws UserClientException if a problem occurs
     */
    User fetchByUsername(String userName) throws UserClientException;

    /**
     * Fetch a user reagrding his ID or Facebook ID
     *
     * @param id the user facebook ID
     *
     * @return the user
     *
     * @throws UserClientException if a problem occurs
     */
    User fetchByFacebookID(String id) throws UserClientException;

    JSONObject postUser(JSONObject jsonUserObject) throws UserClientException;

    //TODO: as code develops, calls to this method should be restrained to the User class. It will probably be relocated.
    String registerUser(String username, int eventId) throws UserClientException;
}
