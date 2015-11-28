package ch.epfl.sweng.swissaffinity.utilities.network.users;

/**
 * An exception class extension for UserClient
 */
public class UserClientException extends Exception {

    public UserClientException(String message) {
        super(message);
    }

    public UserClientException(Throwable throwable) {
        super(throwable);
    }
}
