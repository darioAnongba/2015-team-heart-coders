package ch.epfl.sweng.swissaffinity.utilities.network;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Allows to parse for getting Date instance.
 */
public class DateParser {
    private static final String SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * Parse the given string for a date.
     *
     * @param dateString a string formatted date.
     *
     * @return a date {@link Date}
     *
     * @throws ParserException if an error occurs while parsing the string.
     */
    public static Date parseFromString(String dateString) throws ParserException {
        try {
            return new SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault()).parse(dateString);
        } catch (ParseException e) {
            throw new ParserException(e);
        }
    }
}
