package ch.epfl.sweng.swissaffinity.utilities.parsers.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.Parser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BIRTHDAY;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.GENDER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

/**
 * Allows to build a user from facebook data.
 */
public class FacebookUserParser extends Parser<User> {

    @Override
    public User parse(SafeJSONObject jsonObject) throws ParserException {
        if (jsonObject == null) {
            throw new IllegalArgumentException();
        }
        int id = 0;
        String facebookId = jsonObject.get(ID.get(), "");
        String userName = jsonObject.get(USERNAME.get(), "");
        String firstName = jsonObject.get(FIRST_NAME.get(), "");
        String lastName = jsonObject.get(LAST_NAME.get(), "");
        String email = jsonObject.get(EMAIL.get(), "");
        String genderString = jsonObject.get(GENDER.get(), "male");
        User.Gender gender = User.Gender.getGender(genderString);
        String dateString = jsonObject.get(BIRTHDAY.get(), "12/31/2015");
        Date birthDate = DateParser.parseFromString(dateString, DateParser.FACEBOOK_DATE_FORMAT);

        return new User(
            id,
            facebookId,
            userName,
            email,
            lastName,
            firstName,
            "",
            "",
            SafeJSONObject.DEFAULT_ADDRESS,
            false,
            false,
            gender,
            birthDate,
            "",
            "",
            new HashSet<Location>(),
            new ArrayList<Event>());
    }
}
