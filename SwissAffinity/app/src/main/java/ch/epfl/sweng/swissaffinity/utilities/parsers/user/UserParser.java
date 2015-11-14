package ch.epfl.sweng.swissaffinity.utilities.parsers.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.parsers.AddressParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parsable;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserFactory;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static ch.epfl.sweng.swissaffinity.users.User.Gender;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ADDRESS;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BIRTH_DATE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ENABLED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EVENTS_ATTENDED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FACEBOOKID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.GENDER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.HOME_PHONE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOCATIONS_INTEREST;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOCKED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.MOBILE_PHONE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.PROFESSION;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.PROFILE_PICTURE;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

/**
 * Created by Max on 13/11/2015.
 */
public class UserParser {

    public static User parseFromJSON(SafeJSONObject jsonObject) throws ParserException {
        User user;
        try {
            if (!(jsonObject.get("name") instanceof String)) {
                throw new JSONException("Invalid question structure");
            }
        } catch (JSONException e) {
            throw new ParserException(e);
        }
        int id = jsonObject.getInt(ID.get(), -1);
        int facebookId = jsonObject.getInt(FACEBOOKID.get(), -1);
        String username = jsonObject.getString(USERNAME.get(), "");
        String email = jsonObject.getString(EMAIL.get(), "");
        String firstName = jsonObject.getString(FIRST_NAME.get(), "");
        String lastName = jsonObject.getString(LAST_NAME.get(), "");
        String mobilePhone = jsonObject.getString(MOBILE_PHONE.get(), "");
        String homePhone = jsonObject.getString(HOME_PHONE.get(), "");
        JSONObject preAddress = jsonObject.getJSONObject(ADDRESS.get(), null);
        Address address = AddressParser.parseFromJSON(preAddress);
        boolean locked = jsonObject.getBoolean(LOCKED.get(), true);
        boolean enabled = jsonObject.getBoolean(ENABLED.get(), true);
        Gender gender = Gender.getGender(jsonObject.getString(GENDER.get(), null));
        String birthDate = jsonObject.getString(BIRTH_DATE.get(), "");
        String profession = jsonObject.getString(PROFESSION.get(), "");

        URL profilePicture;
        try {
            profilePicture = new URL(jsonObject.getString(PROFILE_PICTURE.get(), ""));
        } catch (MalformedURLException e) {
            throw new ParserException(e);
        }

        JSONArray areas = jsonObject.getJSONArray(LOCATIONS_INTEREST.get(), null);
        List<Location> areasOfInterest = new ArrayList<>();
        for (int i = 0; i < areas.length(); i++) {
            try {
                JSONObject jsonArea = areas.getJSONObject(i);
                Location location = new Location(jsonArea.toString());
                areasOfInterest.add(location);
            } catch (JSONException e) {
                throw new ParserException(e);
            }
        }
        JSONArray events = jsonObject.getJSONArray(EVENTS_ATTENDED.get(), null);
        List<Event> eventsAttended = null;
        for (int i = 0; i < events.length(); i++) {
            try {
                JSONObject jsonEvent = events.getJSONObject(i);
                Parsable<Event> parsable = (Parsable<Event>) ParserFactory.parserFor(jsonEvent);
                Event event = parsable.parseFromJSON(jsonEvent);
                eventsAttended.add(event);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        user = new User(id,
                facebookId,
                username,
                email,
                firstName,
                lastName,
                mobilePhone,
                homePhone,
                address,
                locked,
                enabled,
                gender,
                DateParser.parseFromString(birthDate),
                profession,
                profilePicture,
                areasOfInterest,
                eventsAttended);

        return user;
    }
}
