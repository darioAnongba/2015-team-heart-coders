package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * Created by sahinfurkan on 18/11/15.
 */
public class DateParserTest {

    Date testDate;

    @Before
    public void setUp() throws ParserException {
        testDate =
            DateParser.parseFromString(
                "2014-06-09T10:11:12+0100",
                DateParser.SERVER_DATE_FORMAT); //GMT + 1 time in Switzerland.
    }

    @Test(expected = ParserException.class)
    public void dateExceptionTest() throws ParserException {
        testDate =
            DateParser.parseFromString("2014-06-09A10:11:12+0100", DateParser.SERVER_DATE_FORMAT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void dateNullTest() {
        DateParser.dateToString(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFromStringNullString() throws ParserException {
        DateParser.parseFromString(null, DateParser.FACEBOOK_DATE_FORMAT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseFromStringNullFormat() throws ParserException {
        DateParser.parseFromString("", null);
    }
}
