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
import ch.epfl.sweng.swissaffinity.utilities.parsers.LocationParser;
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
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FACEBOOK_ID;
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
 * A parser for the User class.
 */
public class UserParser extends Parser<User> {

    public UserParser(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public User parse() throws ParserException {

        int id = mJsonObject.get(ID.get(), -1);
        long facebookId = mJsonObject.get(FACEBOOK_ID.get(), -1L);
        String username = mJsonObject.get(USERNAME.get(), "");
        String email = mJsonObject.get(EMAIL.get(), "");
        String firstName = mJsonObject.get(FIRST_NAME.get(), "");
        String lastName = mJsonObject.get(LAST_NAME.get(), "");
        String mobilePhone = mJsonObject.get(MOBILE_PHONE.get(), "");
        String homePhone = mJsonObject.get(HOME_PHONE.get(), "");
        JSONObject preAddress = mJsonObject.get(ADDRESS.get(), new JSONObject());
        Address address = new AddressParser(preAddress).parse();
        boolean locked = mJsonObject.get(LOCKED.get(), true);
        boolean enabled = mJsonObject.get(ENABLED.get(), true);
        Gender gender = Gender.getGender(mJsonObject.get(GENDER.get(), "male"));
        String birthDate = mJsonObject.get(BIRTH_DATE.get(), "");
        String profession = mJsonObject.get(PROFESSION.get(), "");
        String profilePicture = mJsonObject.get(PROFILE_PICTURE.get(), "");

        JSONArray areas = mJsonObject.get(LOCATIONS_INTEREST.get(), new JSONArray());
        List<Location> areasOfInterest = new ArrayList<>();
        for (int i = 0; i < areas.length(); i++) {
            try {
                JSONObject jsonArea = areas.getJSONObject(i);
                Location location = new LocationParser(jsonArea).parse();
                areasOfInterest.add(location);
            } catch (JSONException e) {
                throw new ParserException(e);
            }
        }

        JSONArray events = mJsonObject.get(EVENTS_ATTENDED.get(), new JSONArray());
        List<Event> eventsAttended = new ArrayList<>();
        for (int i = 0; i < events.length(); i++) {
            try {
                SafeJSONObject jsonEvent = new SafeJSONObject(events.getJSONObject(i));
                Parser<? extends Event> parser = ParserFactory.parserFor(jsonEvent);
                Event event = parser.parse();
                eventsAttended.add(event);
            } catch (JSONException e) {
                throw new ParserException(e);
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
                profilePicture,
                areasOfInterest,
                eventsAttended);
    }
}
