package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.json.JSONException;
import org.junit.Test;

import ch.epfl.sweng.swissaffinity.DataForTesting;

import static org.junit.Assert.fail;

/**
 * Created by Lionel on 11/12/15.
 */
public class ParserFactoryTest {

    @Test(expected = ParserException.class)
    public void testParserUnknownEventType() throws ParserException {
        ParserFactory.parserFor(new SafeJSONObject());
    }

    @Test
    public void testParserSpeedDatingEvent() throws ParserException {
        try {
            ParserFactory.parserFor(new SafeJSONObject(DataForTesting.createJSONEvent()));
        } catch (JSONException e) {
            fail(e.getMessage());
        }
    }
}
