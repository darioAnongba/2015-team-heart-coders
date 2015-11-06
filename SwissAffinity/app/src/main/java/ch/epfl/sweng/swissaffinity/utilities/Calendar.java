package ch.epfl.sweng.swissaffinity.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ch.epfl.sweng.swissaffinity.utilities.network.ParserException;

public abstract class Calendar extends java.util.Calendar {
    public static java.util.Calendar fromDateString(String dateString) throws ParserException {
        java.util.Calendar calendar;
        try {
            // Server date format : 2015-12-12T19:00:00+0100
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date date = formatter.parse(dateString);
            calendar = java.util.Calendar.getInstance(Locale.getDefault());
            calendar.setTime(date);
        } catch (ParseException e) {
            throw new ParserException(e);
        }
        return calendar;
    }
}
