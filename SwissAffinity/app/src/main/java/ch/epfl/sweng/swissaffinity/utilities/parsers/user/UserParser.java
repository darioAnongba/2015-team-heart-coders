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

    @Override
    public User parse(SafeJSONObject jsonObject) throws ParserException {

        int id = jsonObject.get(ID.get(), -1);
        long facebookId = jsonObject.get(FACEBOOK_ID.get(), -1L);
        String username = jsonObject.get(USERNAME.get(), "");
        String email = jsonObject.get(EMAIL.get(), "");
        String firstName = jsonObject.get(FIRST_NAME.get(), "");
        String lastName = jsonObject.get(LAST_NAME.get(), "");
        String mobilePhone = jsonObject.get(MOBILE_PHONE.get(), "");
        String homePhone = jsonObject.get(HOME_PHONE.get(), "");
        SafeJSONObject jsonAddress;
        try {
            jsonAddress = new SafeJSONObject(jsonObject.getJSONObject(ADDRESS.get()));
        } catch (JSONException e) {
            throw new ParserException(e);
        }
        Address address = new AddressParser().parse(jsonAddress);
        boolean locked = jsonObject.get(LOCKED.get(), true);
        boolean enabled = jsonObject.get(ENABLED.get(), true);
        Gender gender = Gender.getGender(jsonObject.get(GENDER.get(), "male"));
        String birthDate = jsonObject.get(BIRTH_DATE.get(), "");
        String profession = jsonObject.get(PROFESSION.get(), "");
        String profilePicture = jsonObject.get(PROFILE_PICTURE.get(), "");

        JSONArray areas = jsonObject.get(LOCATIONS_INTEREST.get(), new JSONArray());
        List<Location> areasOfInterest = new ArrayList<>();
        for (int i = 0; i < areas.length(); i++) {
            try {
                JSONObject jsonArea = areas.getJSONObject(i);
                Location location = new LocationParser().parse(new SafeJSONObject(jsonArea));
                areasOfInterest.add(location);
            } catch (JSONException e) {
                throw new ParserException(e);
            }
        }

        JSONArray events = jsonObject.get(EVENTS_ATTENDED.get(), new JSONArray());
        List<Event> eventsAttended = new ArrayList<>();
        for (int i = 0; i < events.length(); i++) {
            try {
                SafeJSONObject jsonEvent = new SafeJSONObject(events.getJSONObject(i));
                Parser<? extends Event> parser = ParserFactory.parserFor(jsonEvent);
                Event event = parser.parse(jsonEvent);
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
