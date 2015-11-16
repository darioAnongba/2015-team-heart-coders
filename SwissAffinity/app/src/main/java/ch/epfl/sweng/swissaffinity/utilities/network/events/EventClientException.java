package ch.epfl.sweng.swissaffinity.utilities.network.events;

/**
 * An exception class extension for EventClient.
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
