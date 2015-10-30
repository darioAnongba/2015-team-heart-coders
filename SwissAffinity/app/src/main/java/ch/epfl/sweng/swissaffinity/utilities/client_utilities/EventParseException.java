package ch.epfl.sweng.swissaffinity.utilities.client_utilities;

/**
 * Created by Joel on 10/26/2015.
 */
public class EventParseException extends SAParseException{

    private static final long serialVersionUID = 1L;

    public EventParseException() {
        super();
    }

    public EventParseException(Throwable throwable) {
        super(throwable);
    }

    public EventParseException(String message) {
        super(message);
    }
}
