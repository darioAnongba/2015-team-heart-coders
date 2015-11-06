package ch.epfl.sweng.swissaffinity.utilities.network;

import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.users.UserClientException;

public interface UserClient {
    User fetchByUsername(String userName) throws UserClientException;

    User fetchByIDOrFacebookId(int id) throws UserClientException;
}
