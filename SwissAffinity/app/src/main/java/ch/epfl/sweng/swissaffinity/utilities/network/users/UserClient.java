package ch.epfl.sweng.swissaffinity.utilities.network.users;

import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.users.User;

/**
 * An interface for a user client implementation.
 */
public interface UserClient {

    /**
     * Fetch
     *
     * @param userName the user name
     *
     * @return the user
     *
     * @throws UserClientException if a problem occurs
     */
    User fetchByUsername(String userName) throws UserClientException;

    /**
     * Fetch
     *
     * @param id the user facebook ID
     *
     * @return the user
     *
     * @throws UserClientException if a problem occurs
     */
    User fetchByFacebookID(String id) throws UserClientException;

    JSONObject postUser(String url , JSONObject jsonObject) throws UserClientException;
}
