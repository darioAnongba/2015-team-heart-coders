package ch.epfl.sweng.swissaffinity.utilities.parsers;

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
     * Parse the given string for a date
     *
     * @param dateString a string formatted date
     *
     * @return a date {@link Date}
     *
     * @throws ParserException if an error occurs while parsing the string
     */
    public static Date parseFromString(String dateString) throws ParserException {
        if (dateString == null) {
            throw new IllegalArgumentException();
        }
        try {
            return new SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault()).parse(dateString);
        } catch (ParseException e) {
            throw new ParserException(e);
        }
    }

    /**
     * Get a string representation of a given date.
     *
     * @param date the date instance. {@link Date}
     *
     * @return the string representation of the date. "dd MMMM yyyy - HH:mm"
     */
    public static String dateToString(Date date) {
        if (date == null) {
            throw new IllegalArgumentException();
        }
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd MMMM yyyy - HH:mm", Locale.getDefault());
        return dateFormat.format(date.getTime());
    }
}
