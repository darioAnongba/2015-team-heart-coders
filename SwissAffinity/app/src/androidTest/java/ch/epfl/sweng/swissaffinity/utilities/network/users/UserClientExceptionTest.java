package ch.epfl.sweng.swissaffinity.utilities.network.users;

import org.junit.Test;

/**
 * Created by Lionel on 10/12/15.
 */
public class UserClientExceptionTest {

    @Test(expected = UserClientException.class)
    public void testDefaultConstructor() throws UserClientException {
        throw new UserClientException("test");
    }

    @Test(expected = UserClientException.class)
    public void testConstructor() throws UserClientException {
        throw new UserClientException(new Exception());
    }
}