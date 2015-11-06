package ch.epfl.sweng.swissaffinity.utilities.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.Location;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.utilities.client_utilities.EventParseException;

/**
 * Created by Lionel on 06/11/15.
 */
public class SpeedDatingEventParser {
    private static final String
            ID = "id",
            NAME = "name",
            LOCATION = "location",
            MAX_PEOPLE = "max_people",
            DATE_BEGIN = "date_start",
            DATE_END = "date_end",
            BASE_PRICE = "base_price",
            STATE = "state",
            DESCRIPTION = "description",
            IMAGE_PATH = "image_path",
            LAST_UPDATE = "updated_at",
            MEN_SEATS = "men_seats",
            WOMEN_SEATS = "women_seats",
            MEN_REGISTERED = "num_men_registered",
            WOMEN_REGISTERED = "num_women_registered",
            MIN_AGE = "min_age",
            MAX_AGE = "max_age",
            ESTABLISHMENT = "establishment";

    public static Event parseFromJSON(JSONObject jsonObject) throws EventParseException {
        try {
            // Check that Strings are correct.
            // TODO: more to check here
            if (!(jsonObject.get("name") instanceof String) ||
                !(jsonObject.get("description") instanceof String)) {
                throw new JSONException("Invalid question structure");
            }

            int id = jsonObject.getInt(ID);
            String name = jsonObject.getString(NAME);
            String location = jsonObject.getJSONObject(LOCATION).getString(NAME);
            int maxPeople = jsonObject.getInt(MAX_PEOPLE);
            String dateBegin = jsonObject.getString(DATE_BEGIN);
            String dateEnd = jsonObject.getString(DATE_END);
            double basePrice = jsonObject.getDouble(BASE_PRICE);
            String state = jsonObject.getString(STATE);
            String description = jsonObject.getString(DESCRIPTION);
            String imageUrl = jsonObject.getString(IMAGE_PATH);
            String lastUpdate = jsonObject.getString(LAST_UPDATE);
            int menSeats = jsonObject.getInt(MEN_SEATS);
            int womenSeats = jsonObject.getInt(WOMEN_SEATS);
            int menRegistered = jsonObject.getInt(MEN_REGISTERED);
            int womenRegistered = jsonObject.getInt(WOMEN_REGISTERED);
            int minAge = jsonObject.getInt(MIN_AGE);
            int maxAge = jsonObject.getInt(MAX_AGE);
            // TODO : parse establishement.
            Establishment establishment = null;

            return new SpeedDatingEvent(id,
                                        name,
                                        new Location(location),
                                        maxPeople,
                                        fromDateString(dateBegin),
                                        fromDateString(dateEnd),
                                        basePrice,
                                        Event.State.getState(state),
                                        description,
                                        imageUrl,
                                        fromDateString(lastUpdate),
                                        menSeats,
                                        womenSeats,
                                        menRegistered,
                                        womenRegistered,
                                        minAge,
                                        maxAge,
                                        establishment);
        } catch (JSONException e) {
            throw new EventParseException(e);
        }
    }

    private static Calendar fromDateString(String stringDate) throws EventParseException {
        Calendar calendar;
        try { // 2015-12-12T19:00:00+0100
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date date = formatter.parse(stringDate);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        } catch (ParseException e) {
            throw new EventParseException(e);
        }
        return calendar;
    }
}
