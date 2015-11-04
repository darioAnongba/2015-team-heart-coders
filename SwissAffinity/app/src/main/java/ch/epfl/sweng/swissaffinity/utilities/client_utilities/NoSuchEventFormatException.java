package ch.epfl.sweng.swissaffinity.utilities.client_utilities;

/**
 * Created by Joel on 10/26/2015.
 * Exception thrown when an Event with unknown format needs to be processed.
 * To use only in case a parser factory is required/
 */
public class NoSuchEventFormatException extends NoSuchFormatException{
    private static final long serialVersionUID = 1L;
}
