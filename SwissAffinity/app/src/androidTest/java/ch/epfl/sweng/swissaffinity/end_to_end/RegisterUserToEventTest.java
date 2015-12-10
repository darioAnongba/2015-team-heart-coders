package ch.epfl.sweng.swissaffinity.end_to_end;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.swissaffinity.MainActivity;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.users.NetworkUserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.LocationParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ENABLED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.GENDER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOCKED;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by Joel on 11/19/2015.
 * Modified by Dario on 09.12.2015
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegisterUserToEventTest {
    private UserClient mUserClient;
    private NetworkProvider mNetworkProvider;
    private String mYoungerUserName;
    private String mOlderUserName;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() throws RuntimeException{
        mActivityRule.getActivity();
        mNetworkProvider = new DefaultNetworkProvider();
        mUserClient = new NetworkUserClient(NetworkProvider.SERVER_URL, mNetworkProvider);
        try {
            mYoungerUserName = makeTestUserOnServer("Younger").getString(USERNAME.get());
            mOlderUserName = makeTestUserOnServer("Older").getString(USERNAME.get());
        }catch (IllegalArgumentException | JSONException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void alreadyRegistered() {
        final int eventIdToRegister = 7;
        final String userToRegister = mOlderUserName;
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient(NetworkProvider.SERVER_URL, networkProvider);
        try {
            int registrationId = getRegistrationId(
                    userToRegister,
                    eventIdToRegister);
            if (registrationId > 0) {
                userClient.unregisterUser(registrationId);
            }
        } catch (UserClientException e) {
            // SUCCESS -> clean the registration for the test
        }
        try {
            if(getRegistrationId(userToRegister,eventIdToRegister) > 0) {
                fail("User: " + userToRegister + " cannot be registered to event (id) " +
                        eventIdToRegister
                        + "for this test.");
            }
        } catch (UserClientException e){
            fail(e.getMessage());
        }
        try{
            userClient.registerUser(userToRegister, eventIdToRegister);
            userClient.registerUser(userToRegister, eventIdToRegister);
        }catch (UserClientException e){
            assertEquals("You are already registered to this event",
                    e.getMessage().replace("\"","").replace("\n",""));
        }
        try {
            Integer id = getRegistrationId(userToRegister, eventIdToRegister);
            userClient.unregisterUser(id);
        } catch (UserClientException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testUnderAge() {
        final int eventIdToRegister = 7;//Christmas event is for older Users
        final String userToRegister = mYoungerUserName;
        try{
            mUserClient.registerUser(userToRegister, eventIdToRegister);
        } catch (UserClientException e){
            assertEquals("You are not in the age range of this Event." +
                            " The age range is: 26 - 46 and you are 22",
                    e.getMessage().replace("\"", "").replace("\n", ""));
        }
    }

    @Test
    public void postRegistrationToEvent() throws UserClientException{
        final int eventIdToRegister = 7;
        final String userToRegister = mOlderUserName;

        try {
            int registrationId = getRegistrationId(
                    userToRegister,
                    eventIdToRegister);
            if (registrationId > 0) {
                mUserClient.unregisterUser(registrationId);
            }
        } catch (UserClientException e) {
            // SUCCESS -> clean the registration for the test
        }
        try {
            mUserClient.registerUser(userToRegister, eventIdToRegister);
        } catch (UserClientException e) {
            fail(e.getMessage());
        }
        Integer testRegistrationId = getRegistrationId(userToRegister, eventIdToRegister);
        assertTrue("Registration was not successful.", testRegistrationId > 0);
        try {
            mUserClient.unregisterUser(testRegistrationId);
        } catch (UserClientException e) {
            fail(e.getMessage());
        }
    }

    @After
    public void tearDown() throws UserClientException{

        mUserClient.deleteUser(String.format("TestRegisterUser%s", "Younger"));
        mUserClient.deleteUser(String.format("TestRegisterUser%s", "Older"));
    }

    @Ignore
    private int getRegistrationId(String username, int eventId)
            throws UserClientException
    {
        String registrationsString;
        JSONArray registrations;
        try {
            registrationsString = mNetworkProvider.getContent(
                    NetworkProvider.SERVER_URL + "/api/users/" + username + "/registrations");
            registrations = new JSONArray(registrationsString);
        } catch (JSONException | IOException e) {
            throw new UserClientException(e);
        }
        try {
            HashMap<Integer, Integer> eventToRegistration = new HashMap<>();
            for (int i = 0; i < registrations.length(); i++) {

                JSONObject jsonRegistration = registrations.getJSONObject(i);
                JSONObject jsonEvent = jsonRegistration.getJSONObject("event");
                eventToRegistration.put(jsonEvent.getInt("id"), jsonRegistration.getInt("id"));
            }
            return eventToRegistration.get(eventId);
        } catch (Exception e) {
            return -1;
        }
    }
    @Ignore
    /**
     * Create new users for tests to be independent of the content in the server.
     */
    private JSONObject makeTestUserOnServer(String age) throws IllegalArgumentException{
        final String birthdayDDMMYY;
        final String birthdayT;
        if (age.equals("Younger")){
            birthdayDDMMYY = "18/02/1993";
            birthdayT = "1993-02-18T00:00:00+0100";
        } else if (age.equals("Older")){
            birthdayDDMMYY = "18/02/1980";
            birthdayT = "1980-02-18T00:00:00+0100";
        } else {
            throw new IllegalArgumentException();
        }
        List<Location> locationsOfInterest = new ArrayList<>();
        locationsOfInterest.add(new Location(2, "Genève"));
        locationsOfInterest.add(new Location(3, "Lausanne"));
        locationsOfInterest.add(new Location(4, "Fribourg"));
        locationsOfInterest.add(new Location(6, "Zürich"));
        locationsOfInterest.add(new Location(7, "Berne"));
        locationsOfInterest.add(new Location(8, "Bulle"));
        try {
            JSONObject jsonUser = new JSONObject();
            jsonUser.put(EMAIL.get(), String.format("testregisteruser%s@gmail.com", age));
            jsonUser.put(USERNAME.get(), String.format("TestRegisterUser%s", age));
            jsonUser.put("firstName", "Test");
            jsonUser.put("lastName", "Register");
            jsonUser.put(GENDER.get(), "male");
            jsonUser.put("birthDate", birthdayDDMMYY);
            jsonUser.put("facebookId", String.format("666%s", Integer.toString(age.length())));
            jsonUser.put("plainPassword", "testpassword");
            JSONObject responseJSON = mUserClient.postUser(jsonUser);

            List<Location> areasOfInterest = new ArrayList<>();
            JSONArray areas = responseJSON.getJSONArray("locations_of_interest");
            for (int i = 0; i < areas.length(); i++) {
                JSONObject jsonArea = areas.getJSONObject(i);
                Location location = new LocationParser().parse(new SafeJSONObject(jsonArea));
                areasOfInterest.add(location);
            }
            String fb_id = responseJSON.getString("facebook_id");
            if (!(responseJSON.getString(EMAIL.get()).equals(String.format("testregisteruser%s@gmail.com", age))) ||
                    !(responseJSON.getString(USERNAME.get()).equals(String.format("TestRegisterUser%s", age))) ||
                    !(responseJSON.getString(FIRST_NAME.get()).equals("Test")) ||
                    !(responseJSON.getString(LAST_NAME.get()).equals("Register")) ||
                    !(responseJSON.getString(GENDER.get()).equals("male")) ||
                    !(responseJSON.getString("birth_date").equals(birthdayT)) ||
                    !(fb_id.equals(String.format("666%s", Integer.toString(age.length())))) ||
                    !(responseJSON.getBoolean(LOCKED.get()) == false) ||
                    !(responseJSON.getBoolean(ENABLED.get()) == false) ||
                    !(new GetUserTest.CollectionComparator<Location>().compare(
                            locationsOfInterest,
                            areasOfInterest))
                    ){
                throw new UserClientException("User was not created successfully.");
            }
            return responseJSON;
        } catch (UserClientException | ParserException | JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
