package ch.epfl.sweng.swissaffinity.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Joel on 10/27/2015.
 */
public final class CalendarFactory {
    /**
     *
     * @param stringDate in format "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
     */
    public static Calendar fromDateString(String stringDate) throws ParseException{
        Calendar calendar;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date = formatter.parse(stringDate);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        }catch (ParseException exc){
            throw new ParseException("Invalid date format. Input should of the form" +
                    "yyyy-MM-dd'T'HH:mm:ss.SSSZ",exc.getErrorOffset());
        }
        return calendar;
    }
    public static int ageFromBdate(String birthdate) throws ParseException {
        Calendar cBirthDate = fromDateString(birthdate);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - cBirthDate.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < cBirthDate.get(Calendar.DAY_OF_YEAR))
            age--;
        return age;
    }
}
