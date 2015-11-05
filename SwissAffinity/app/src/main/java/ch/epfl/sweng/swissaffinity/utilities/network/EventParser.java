package ch.epfl.sweng.swissaffinity.utilities.network;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ch.epfl.sweng.swissaffinity.events.DefaultEvent;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.Location;
import ch.epfl.sweng.swissaffinity.utilities.client_utilities.EventParseException;

public class EventParser {
    private static final String
            ID = "id",
            NAME = "name",
            LOCATION = "location",
            MAX_PEOPLE = "max_people",
            DATE_START = "date_start",
            DATE_END = "date_end",
            BASE_PRICE = "base_price",
            STATE = "state",
            DESCRIPTION = "description",
            IMAGE_URL = "image_path",
            DATE_CREATED = "created_at";

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
            String dateStart = jsonObject.getString(DATE_START);
            String dateEnd = jsonObject.getString(DATE_END);
            int basePrice = jsonObject.getInt(BASE_PRICE);
            String state = jsonObject.getString(STATE);
            String description = jsonObject.getString(DESCRIPTION);
            String imageUrl = jsonObject.getString(IMAGE_URL);
            String dateCreated = jsonObject.getString(DATE_CREATED);

            return new DefaultEvent(
                    id,
                    new Location(location),
                    name,
                    maxPeople,
                    fromDateString(dateStart),
                    fromDateString(dateEnd),
                    basePrice,
                    null,
                    Event.State.getState(state),
                    description,
                    imageUrl,
                    fromDateString(dateCreated)
            );
        } catch (JSONException e) {
            throw new EventParseException(e);
        }
    }

    public static Calendar fromDateString(String stringDate) throws EventParseException {
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
