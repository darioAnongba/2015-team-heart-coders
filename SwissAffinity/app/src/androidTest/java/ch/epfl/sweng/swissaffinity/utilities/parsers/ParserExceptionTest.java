package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.junit.Test;

/**
 * Created by Lionel on 11/12/15.
 */
public class ParserExceptionTest {

    @Test(expected = ParserException.class)
    public void testDefaultConstructor() throws ParserException {
        throw new ParserException();
    }

    @Test(expected = ParserException.class)
    public void testConstructor() throws ParserException {
        throw new ParserException("test");
    }

    @Test(expected = ParserException.class)
    public void testConstructor2() throws ParserException {
        throw new ParserException(new IllegalArgumentException());
    }
}
