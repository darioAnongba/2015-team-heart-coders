package ch.epfl.sweng.swissaffinity.utilities.network;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

interface Parsable<A> {

    enum TAGS {
        ID("id"),
        NAME("name"),
        LOCATION("location"),
        MAX_PEOPLE("max_people"),
        DATE_BEGIN("date_start"),
        DATE_END("date_end"),
        BASE_PRICE("base_price"),
        STATE("state"),
        DESCRIPTION("description"),
        IMAGE_PATH("image_path"),
        LAST_UPDATE("updated_at"),
        MEN_SEATS("men_seats"),
        WOMEN_SEATS("women_seats"),
        MEN_REGISTERED("num_men_registered"),
        WOMEN_REGISTERED("num_women_registered"),
        MIN_AGE("min_age"),
        MAX_AGE("max_age"),
        ESTABLISHMENT("establishment"),
        TYPE("discr"),
        SPEED_DATING_TYPE("speed_dating");

        private final String mName;

        TAGS(String name) {
            mName = name;
        }

        String get() {
            return mName;
        }
    }

    A parseFromJSON(JSONObject jsonObject) throws ParserException;

    static Calendar fromDateString(String dateString) throws ParserException {
        Calendar calendar;
        try {
            // Server date format : 2015-12-12T19:00:00+0100
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date date = formatter.parse(dateString);
            calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTime(date);
        } catch (ParseException e) {
            throw new ParserException(e);
        }
        return calendar;
    }
}
