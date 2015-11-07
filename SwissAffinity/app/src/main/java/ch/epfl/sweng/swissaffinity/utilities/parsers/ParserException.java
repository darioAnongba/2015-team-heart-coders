package ch.epfl.sweng.swissaffinity.utilities.parsers;

/**
 * Exception raised in parser operations.
 */
public class ParserException extends Exception {
    public ParserException() {
        super();
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(Throwable throwable) {
        super(throwable);
    }
}
