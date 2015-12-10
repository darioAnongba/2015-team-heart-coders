package ch.epfl.sweng.swissaffinity.utilities.network.events;

import org.junit.Test;

/**
 * Created by Lionel on 10/12/15.
 */
public class EventClientExceptionTest {

    @Test(expected = EventClientException.class)
    public void testDefaultConstructor() throws EventClientException {
        throw new EventClientException();
    }

    @Test(expected = EventClientException.class)
    public void testConstructor() throws EventClientException {
        throw new EventClientException(new Exception());
    }
}