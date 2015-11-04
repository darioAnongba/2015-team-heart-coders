package ch.epfl.sweng.swissaffinity.utilities;

/**
 * Created by Joel on 10/29/2015.
 */
public class ClientException extends Exception {

    private static final long serialVersionUID = 1L;

    public ClientException() {
        super();
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(Throwable throwable) {
        super(throwable);
    }
}

