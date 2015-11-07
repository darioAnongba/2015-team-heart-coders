package ch.epfl.sweng.swissaffinity.utilities.network.users;

import ch.epfl.sweng.swissaffinity.users.User;

public interface UserClient {
    User fetchByUsername(String userName) throws UserClientException;

    User fetchByIDOrFacebookId(int id) throws UserClientException;
}
