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
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parser;
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
public class UserParser extends Parser<User> {

    public UserParser(JSONObject jsonObject) {
        super(jsonObject);
    }

    public User parse() throws ParserException {
        User user;

        int id = mJsonObject.getInt(ID.get(), -1);
        long facebookId = mJsonObject.getLong(FACEBOOKID.get(), -1);
        String username = mJsonObject.getString(USERNAME.get(), "");
        String email = mJsonObject.getString(EMAIL.get(), "");
        String firstName = mJsonObject.getString(FIRST_NAME.get(), "");
        String lastName = mJsonObject.getString(LAST_NAME.get(), "");
        String mobilePhone = mJsonObject.getString(MOBILE_PHONE.get(), "");
        String homePhone = mJsonObject.getString(HOME_PHONE.get(), "");
        JSONObject preAddress = mJsonObject.getJSONObject(ADDRESS.get(), null);
        Address address = new AddressParser(preAddress).parse();
        boolean locked = mJsonObject.getBoolean(LOCKED.get(), true);
        boolean enabled = mJsonObject.getBoolean(ENABLED.get(), true);
        Gender gender = Gender.getGender(mJsonObject.getString(GENDER.get(), null));
        String birthDate = mJsonObject.getString(BIRTH_DATE.get(), "");
        String profession = mJsonObject.getString(PROFESSION.get(), "");
        String profilePicture = mJsonObject.getString(PROFILE_PICTURE.get(), null);

        URL profilePictureURL = null;
        if (profilePicture != null) {
            try {
                profilePictureURL = new URL(profilePicture);
            } catch (MalformedURLException e) {
                throw new ParserException(e);
            }
        }

        JSONArray areas = mJsonObject.getJSONArray(LOCATIONS_INTEREST.get(), new JSONArray());
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
        JSONArray events = mJsonObject.getJSONArray(EVENTS_ATTENDED.get(), new JSONArray());
        List<Event> eventsAttended = new ArrayList<>();
        for (int i = 0; i < events.length(); i++) {
            try {
                SafeJSONObject jsonEvent = new SafeJSONObject(events.getJSONObject(i));
                Parser<? extends Event> parser = ParserFactory.parserFor(jsonEvent);
                Event event = parser.parse();
                eventsAttended.add(event);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return new User(
                id,
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
                profilePictureURL,
                areasOfInterest,
                eventsAttended);
    }
}
