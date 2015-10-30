package ch.epfl.sweng.swissaffinity.utilities.client_utilities;

/**
 * Created by Joel on 10/26/2015.
 * Exception thrown when received User string violates required format.
 */
public class UserParseException extends  SAParseException{
    private static final long serialVersionUID = 1L;

    public UserParseException() {
        super();
    }

    public UserParseException(Throwable throwable) {
        super(throwable);
    }

    public UserParseException(String message) {
        super(message);
    }

}