package ch.epfl.sweng.swissaffinity.gui;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.MainActivity;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.users.Registration;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.ServerTags;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.users.NetworkUserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClient;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BIRTHDAY;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FACEBOOK_ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.GENDER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOCATIONS_INTEREST;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lionel on 05/12/15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DataManagerTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        mActivityRule.getActivity();
        DataManager.setEventClient(new DummyEventClient());
    }

    @Test
    public void testGetEventClient() throws Exception {
        EventClient eventClient = DataManager.getEventClient();
        assertEquals(DummyEventClient.class, eventClient.getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetEventClient() throws Exception {
        DataManager.setEventClient(null);
    }

    @Test
    public void testGetUserClient() throws Exception {
        UserClient userClient = DataManager.getUserClient();
        assertEquals(NetworkUserClient.class, userClient.getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetUserClient() throws Exception {
        DataManager.setUserClient(null);
    }

    @Test
    public void testIsEmpty() throws Exception {
        DataManager.deleteUser();
        assertEquals(false, DataManager.hasData());
    }

    @Test
    public void testDeleteUser() throws Exception {
        SharedPreferences prefs = MainActivity.getPreferences();
        DataManager.deleteUser();
        assertTrue(!DataManager.hasData());
        assertNull(prefs.getString(FACEBOOK_ID.get(), null));
        assertNull(prefs.getString(ServerTags.USERNAME.get(), null));
        assertNull(prefs.getString(LAST_NAME.get(), null));
        assertNull(prefs.getString(FIRST_NAME.get(), null));
        assertNull(prefs.getString(GENDER.get(), null));
        assertNull(prefs.getString(BIRTHDAY.get(), null));
        assertNull(prefs.getString(EMAIL.get(), null));
        assertNull(prefs.getStringSet(LOCATIONS_INTEREST.get(), null));
    }

    @Test
    public void testSaveUser() throws Exception {
        SharedPreferences prefs = MainActivity.getPreferences();
        DataManager.deleteUser();
        DataManager.saveUser(DataForTesting.createUser());
        assertEquals("2001", prefs.getString(FACEBOOK_ID.get(), ""));
        assertEquals("testUsername", prefs.getString(USERNAME.get(), ""));
        assertEquals("testLastName", prefs.getString(LAST_NAME.get(), ""));
        assertEquals("testFirstName", prefs.getString(FIRST_NAME.get(), ""));
        assertEquals("male", prefs.getString(GENDER.get(), ""));
        assertEquals("Wed Nov 16 16:00:00 GMT+01:00 1983", prefs.getString(BIRTHDAY.get(), ""));
        assertEquals("testEmail", prefs.getString(EMAIL.get(), ""));
        List<String> locations =
                Arrays.asList(
                        DataForTesting.LOCATIONS.get(1).getName(),
                        DataForTesting.LOCATIONS.get(0).getName());
        Set<String>
                savedLocations =
                prefs.getStringSet(LOCATIONS_INTEREST.get(), new HashSet<String>());
        savedLocations.removeAll(locations);
        assertEquals(0, savedLocations.size());
    }

    @Test
    public void testGetRegistrationId() throws Exception {
        assertEquals(-1, DataManager.getRegistrationId(0));
    }

    @Test
    public void testGetEvent() throws Exception {
        assertNull(DataManager.getEvent(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateNull() throws Exception {
        DataManager.updateData(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDisplayNull() throws Exception {
        DataManager.displayData(null);
    }

    @Ignore
    private class DummyEventClient implements EventClient {
        @Ignore
        public List<Event> fetchAll() throws EventClientException {
            return new ArrayList<>();
        }

        @Ignore
        public List<Registration> fetchForUser(String userName) throws EventClientException {
            return new ArrayList<>();
        }

        @Ignore
        public List<Event> fetchAllFor(Collection<Location> locations) throws EventClientException {
            return new ArrayList<>();
        }

        @Ignore
        public Event fetchBy(int id) throws EventClientException {
            throw new EventClientException();
        }

        @Ignore
        public Bitmap imageFor(String imagePath) throws EventClientException {
            throw new EventClientException();
        }
    }
}