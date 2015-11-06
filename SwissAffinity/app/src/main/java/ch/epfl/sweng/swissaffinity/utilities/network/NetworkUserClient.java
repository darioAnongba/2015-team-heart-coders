package ch.epfl.sweng.swissaffinity.utilities.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.Location;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.users.UserClientException;
import ch.epfl.sweng.swissaffinity.utilities.Address;

import static ch.epfl.sweng.swissaffinity.utilities.CalendarParser.fromDateString;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.BIRTH_DATE;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.ENABLED;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.FACEBOOKID;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.HOME_PHONE;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.LOCATIONS_INTEREST;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.LOCKED;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.MOBILE_PHONE;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.PROFESSION;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.PROFILE_PICTURE;
import static ch.epfl.sweng.swissaffinity.utilities.network.Parsable.TAGS.USERNAME;

/**
 * Created by Max on 06/11/2015.
 */
public class NetworkUserClient implements UserClient {
    private static final String SERVER_API_USERS = "users";

    private final String mServerUrl;
    private final NetworkProvider mNetworkProvider;

    public NetworkUserClient(String serverUrl, NetworkProvider networkProvider) {
        mServerUrl = serverUrl;
        mNetworkProvider = networkProvider;
    }



    public User parseFromJSON(JSONObject jsonObject) throws ParserException {
        User user = null;
        try {
            // Check that Strings are correct.
            // TODO: more to check here
            if (!(jsonObject.get("name") instanceof String)) {
                throw new JSONException("Invalid question structure");
            }

            int id = jsonObject.getInt(ID.get());
            int facebookId = jsonObject.getInt(FACEBOOKID.get());
            String username = jsonObject.getString(USERNAME.get());
            String email = jsonObject.getString(EMAIL.get());
            String firstName = jsonObject.getString(FIRST_NAME.get());
            String lastName = jsonObject.getString(LAST_NAME.get());
            String mobilePhone = jsonObject.getString(MOBILE_PHONE.get());
            String homePhone = jsonObject.getString(HOME_PHONE.get());
            Address address = null; //TODO create parser for Address
            boolean locked = jsonObject.getBoolean(LOCKED.get());
            boolean enabled = jsonObject.getBoolean(ENABLED.get());
            User.Gender gender = null; //TODO create parser for Gender
            String birthDate = jsonObject.getString(BIRTH_DATE.get());
            String profession = jsonObject.getString(PROFESSION.get());
            URL profilePicture = new URL(jsonObject.getString(PROFILE_PICTURE.get()));
            JSONArray areas = jsonObject.getJSONArray(LOCATIONS_INTEREST.get());
            List<Location> areasOfInterest= null;
            /*for(int i=0;i<areas.length();i++) {
                areasOfInterest.add(areas.getJSONObject(i).toString());
                //TODO create parser for Location
           }*/
            List<Event> eventsAttended = null; //TODO parse the events


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
                    fromDateString(birthDate),
                    profession,
                    profilePicture,
                    areasOfInterest,
                    eventsAttended);
        } catch (JSONException e) {
            throw new ParserException(e);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User fetchByUsername(String userName) throws UserClientException {
        User user =null;
        try {
            String content = mNetworkProvider.getContent(mServerUrl + SERVER_API_USERS + "/" + userName);

                JSONObject jsonUser = new JSONObject(content);

            user = parseFromJSON(jsonUser);
            } catch (JSONException | ParserException | IOException e) {
                throw new UserClientException();
            }

        return user;
    }

    @Override
    public User fetchByIDOrFacebookId(int id) throws UserClientException{
        User user =null;
        try {
            String content = mNetworkProvider.getContent(mServerUrl + SERVER_API_USERS + "/" + id);

            JSONObject jsonUser = new JSONObject(content);

            user = parseFromJSON(jsonUser);
        } catch (JSONException | ParserException | IOException e) {
            throw new UserClientException();
        }

        return user;
    }


}
