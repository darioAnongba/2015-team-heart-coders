package ch.epfl.sweng.swissaffinity.utilities.network;

/**
 * Created by Joel on 10/26/2015.
 */
public class UserClientException extends Exception{

    private static final long serialVersionUID = 1L;

    public UserClientException() {
        super();
    }

    public UserClientException(String message) {
        super(message);
    }

    public UserClientException(Throwable throwable) {
        super(throwable);
    }
}
