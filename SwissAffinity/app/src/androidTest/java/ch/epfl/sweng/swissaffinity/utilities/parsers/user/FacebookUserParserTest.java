package ch.epfl.sweng.swissaffinity.utilities.parsers.user;

import android.test.suitebuilder.annotation.LargeTest;

import org.json.JSONException;
import org.junit.Test;

import java.util.Date;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lionel on 10/12/15.
 */
@LargeTest
public class FacebookUserParserTest {

    @Test
    public void testEmptyJSON() throws ParserException {
        User user = new FacebookUserParser().parse(new SafeJSONObject());
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("", user.getFacebookId());
        assertEquals("", user.getFirstName());
        assertEquals("", user.getLastName());
        assertEquals("", user.getUsername());
        assertEquals("", user.getEmail());
        assertEquals("MALE", user.getGender().toString());
        Date birthDate = DateParser.parseFromString("12/31/2015", DateParser.FACEBOOK_DATE_FORMAT);
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(SafeJSONObject.DEFAULT_ADDRESS, user.getAddress());
        assertEquals("", user.getMobilePhone());
        assertEquals("", user.getHomePhone());
        assertEquals("", user.getProfession());
        assertFalse(user.getEnabled());
        assertFalse(user.getLocked());
        assertEquals("", user.getProfilePicture());
        assertTrue(user.getAreasOfInterest().isEmpty());
        assertTrue(user.getEventsAttended().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullJSON() throws ParserException {
        new FacebookUserParser().parse(null);
    }

    @Test
    public void testBasicData() throws JSONException, ParserException {
        SafeJSONObject jsonObject = new SafeJSONObject(DataForTesting.userJSONcontent);
        User user = new FacebookUserParser().parse(jsonObject);
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals("", user.getFacebookId());
        assertEquals("testFirstName", user.getFirstName());
        assertEquals("testLastName", user.getLastName());
        assertEquals("testUsername", user.getUsername());
        assertEquals("testEmail", user.getEmail());
        assertEquals("MALE", user.getGender().toString());
        Date birthDate = DateParser.parseFromString("12/31/2015", DateParser.FACEBOOK_DATE_FORMAT);
        assertEquals(birthDate, user.getBirthDate());
        assertEquals(SafeJSONObject.DEFAULT_ADDRESS, user.getAddress());
        assertEquals("", user.getMobilePhone());
        assertEquals("", user.getHomePhone());
        assertEquals("", user.getProfession());
        assertFalse(user.getEnabled());
        assertFalse(user.getLocked());
        assertEquals("", user.getProfilePicture());
        assertTrue(user.getAreasOfInterest().isEmpty());
        assertTrue(user.getEventsAttended().isEmpty());
    }
}