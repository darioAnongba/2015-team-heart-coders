package ch.epfl.sweng.swissaffinity.utilities.network.events;

/**
 * Created by Lionel on 05/11/15.
 */
public class EventClientException extends Exception {
    public EventClientException() {
        super();
    }

    public EventClientException(String message) {
        super(message);
    }

    public EventClientException(Throwable throwable) {
        super(throwable);
    }
}
