package ch.epfl.sweng.swissaffinity.end_to_end;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.HashMap;

import ch.epfl.sweng.swissaffinity.MainActivity;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.users.NetworkUserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;

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

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() {
        mActivityRule.getActivity();
    }

    @Test
    public void alreadyRegistered() {
        final int eventIdToRegister = 7;
        final String userToRegister = "lio";
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient(NetworkProvider.SERVER_URL, networkProvider);
        try {
            int registrationId = getRegistrationId(
                    userToRegister,
                    eventIdToRegister,
                    networkProvider);
            if (registrationId > 0) {
                userClient.unregisterUser(registrationId);
            }
        } catch (UserClientException e) {
            // SUCCESS -> clean the registration for the test
        }
        try {
            if(getRegistrationId(userToRegister,eventIdToRegister,networkProvider) > 0) {
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
            Integer id = getRegistrationId(userToRegister, eventIdToRegister, networkProvider);
            userClient.unregisterUser(id);
        } catch (UserClientException e){
            fail(e.getMessage());
        }
    }

    @Test
    public void testUnderAge() {
        final int eventIdToRegister = 7;
        final String userToRegister = "Admin";
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient(NetworkProvider.SERVER_URL, networkProvider);
        try{
            userClient.registerUser(userToRegister, eventIdToRegister);
        } catch (UserClientException e){
            assertEquals("You are not in the age range of this Event." +
                            " The age range is: 26 - 46 and you are 22",
                    e.getMessage().replace("\"", "").replace("\n", ""));
        }
    }

    @Test
    public void postRegistrationToEvent() throws UserClientException{
        final int eventIdToRegister = 7;
        final String userToRegister = "lio";
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient(NetworkProvider.SERVER_URL, networkProvider);
        try {
            int registrationId = getRegistrationId(
                    userToRegister,
                    eventIdToRegister,
                    networkProvider);
            if (registrationId > 0) {
                userClient.unregisterUser(registrationId);
            }
        } catch (UserClientException e) {
            // SUCCESS -> clean the registration for the test
        }
        try {
            userClient.registerUser(userToRegister, eventIdToRegister);
        } catch (UserClientException e) {
            fail(e.getMessage());
        }
        Integer testRegistrationId = getRegistrationId(userToRegister,eventIdToRegister,networkProvider);
        assertTrue("Registration was not successful.", testRegistrationId > 0);
        try {
            userClient.unregisterUser(testRegistrationId);
        } catch (UserClientException e) {
            fail(e.getMessage());
        }
    }

    @Ignore
    private int getRegistrationId(String username, int eventId, NetworkProvider networkProvider)
            throws UserClientException
    {
        String registrationsString;
        JSONArray registrations;
        try {
            registrationsString = networkProvider.getContent(
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
}
