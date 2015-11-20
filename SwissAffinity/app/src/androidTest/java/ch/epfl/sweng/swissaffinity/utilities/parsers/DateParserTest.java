package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Created by sahinfurkan on 18/11/15.
 */
public class DateParserTest {
    Date dateFromString;
    @Before
    public void setUp() throws ParserException {
        dateFromString = new DateParser().parseFromString("2014-06-09T10:11:12+0100");
    }

    @Test
    public void dateFromStringTest(){
        String str = DateParser.dateToString(dateFromString);
        assertEquals(str, "09 June 2014 - 11:11");
    }

    @Test (expected = ParserException.class)
    public void dateExceptionTest() throws ParserException {
        dateFromString = new DateParser().parseFromString("2014-06-09A10:11:12-0900");
    }

    @Test
    public void dateToStringTest(){
        String str = new DateParser().dateToString(dateFromString);
        assertEquals(str, "09 June 2014 - 11:11");
    }


    /* TODO dateParser class's dateToString method has a TODO on null check!
     * This should be modified after that TODO!
     */
    @Test(expected = IllegalArgumentException.class)
    public void dateNullTest(){
        String str = new DateParser().dateToString(null);
    }

}
