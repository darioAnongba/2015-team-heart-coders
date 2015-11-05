package ch.epfl.sweng.swissaffinity.utilities.network;

import ch.epfl.sweng.swissaffinity.users.User;

public interface UserClient {
    User fetchUserBy(String userName);

    User fetchUserBy(int id);
}
