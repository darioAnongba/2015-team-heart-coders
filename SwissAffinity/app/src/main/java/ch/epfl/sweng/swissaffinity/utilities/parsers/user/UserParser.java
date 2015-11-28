package ch.epfl.sweng.swissaffinity.utilities.parsers.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import static ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject.*;

/**
 * A parser for the User class.
 */
public class UserParser extends Parser<User> {

    @Override
    public User parse(SafeJSONObject jsonObject) throws ParserException {

        SafeJSONObject jsonAddress;
        String facebookId;
        int id;
        String userName;
        //Critical attributes
        try {
            jsonAddress = new SafeJSONObject(jsonObject.getJSONObject(ADDRESS.get()));
            facebookId = jsonObject.getString(FACEBOOK_ID.get());
            id = jsonObject.getInt(ID.get());
            userName = jsonObject.getString(USERNAME.get());
        } catch (JSONException e){
            throw new ParserException(e);
        }

        String email = jsonObject.get(EMAIL.get(), DEFAULT_STRING);
        String firstName = jsonObject.get(FIRST_NAME.get(), DEFAULT_STRING);
        String lastName = jsonObject.get(LAST_NAME.get(), DEFAULT_STRING);
        String mobilePhone = jsonObject.get(MOBILE_PHONE.get(), DEFAULT_STRING);
        String homePhone = jsonObject.get(HOME_PHONE.get(), DEFAULT_STRING);
        Address address = new AddressParser().parse(jsonAddress);
        boolean locked = jsonObject.get(LOCKED.get(), true);
        boolean enabled = jsonObject.get(ENABLED.get(), false);
        Gender gender = Gender.getGender(jsonObject.get(GENDER.get(), "male"));
        String birthDate = jsonObject.get(BIRTH_DATE.get(), DEFAULT_STRING);
        String profession = jsonObject.get(PROFESSION.get(), DEFAULT_STRING);
        String profilePicture = jsonObject.get(PROFILE_PICTURE.get(), DEFAULT_STRING);

        List<Location> areasOfInterest = new ArrayList<>();
        JSONArray areas = jsonObject.get(LOCATIONS_INTEREST.get(), new JSONArray());
        for (int i = 0; i < areas.length(); i++) {
            try {
                JSONObject jsonArea = areas.getJSONObject(i);
                Location location = new LocationParser().parse(new SafeJSONObject(jsonArea));
                areasOfInterest.add(location);
            } catch (JSONException e) {
                throw new ParserException(e);
            }
        }

        List<Event> eventsAttended = new ArrayList<>();
        JSONArray events = jsonObject.get(EVENTS_ATTENDED.get(), new JSONArray());
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
                userName,
                email,
                lastName,
                firstName,
                mobilePhone,
                homePhone,
                address,
                locked,
                enabled,
                gender,
                DateParser.parseFromString(birthDate, DateParser.SERVER_DATE_FORMAT),
                profession,
                profilePicture,
                areasOfInterest,
                eventsAttended);
    }
}
