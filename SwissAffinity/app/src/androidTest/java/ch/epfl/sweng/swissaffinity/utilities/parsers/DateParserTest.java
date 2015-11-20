package ch.epfl.sweng.swissaffinity.utilities.parsers;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by sahinfurkan on 18/11/15.
 */
public class DateParserTest {
    Date testDate;
    @Before
    public void setUp() throws ParserException {
        testDate = DateParser.parseFromString("2014-06-09T10:11:12+0100");//GMT + 1 time in Switzlerand.
    }

    @Test
    public void dateFromStringTest(){
        String str = DateParser.dateToString(testDate);
        assertEquals(str, "09 June 2014 - 11:11");
    }

    @Test (expected = ParserException.class)
    public void dateExceptionTest() throws ParserException {
        testDate = DateParser.parseFromString("2014-06-09A10:11:12+0100");
    }

    @Test(expected = IllegalArgumentException.class)
    public void dateNullTest(){
        String str = DateParser.dateToString(null);
    }

}
