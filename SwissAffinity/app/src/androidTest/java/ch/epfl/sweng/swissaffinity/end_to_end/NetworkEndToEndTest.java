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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.swissaffinity.MainActivity;
import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.events.NetworkEventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.NetworkUserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
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
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class NetworkEndToEndTest {
    /**
     * Admin (Dario) is used as test user here.
     */

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() {
        mActivityRule.getActivity();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullNetworkProviderForUser() {
        new NetworkUserClient("http://beecreative.ch", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullNetworkProviderForEvent() {
        new NetworkEventClient("http://beecreative.ch", null);
    }

    @Test
    public void testGetUser() {
        try {
            NetworkProvider networkProvider = new DefaultNetworkProvider();
            UserClient userClient = new NetworkUserClient("http://beecreative.ch", networkProvider);
            User user = userClient.fetchByFacebookID(Integer.toString(1271175799)); //Dario's fb id
            List<Location> locationsOfInterest = new ArrayList<>();
            locationsOfInterest.add(new Location(3, "Lausanne"));
            Date birthDay = DateParser.parseFromString(
                    "1993-02-02T00:00:00+0100",
                    DateParser.SERVER_DATE_FORMAT);

            assertTrue("Unexpected username", user.getUsername().equals("Admin"));
            assertTrue("Unexpected first name", user.getFirstName().equals("Dario"));
            assertTrue("Unexpected last name", user.getLastName().equals("Anongba"));
            assertTrue("Unexpected profession", user.getProfession().equals("Student"));
            assertTrue(
                    "Unexpected home phone",
                    user.getHomePhone().equals(SafeJSONObject.DEFAULT_STRING));
            assertTrue("Unexpected mobile phone", user.getMobilePhone().equals("+41799585615"));
            assertTrue(
                    "Unexpected profile picture",
                    user.getProfilePicture().equals(SafeJSONObject.DEFAULT_STRING));
            assertTrue("Unexpected email", user.getEmail().equals("dario.anongba@epfl.ch"));
            assertTrue("Unexpected id", user.getId() == 1);
            assertTrue("Unexpected fb id", user.getFacebookId().equals("1271175799"));
            assertTrue("Unexpected, user should be enabled", user.getEnabled());
            assertTrue("Unexpected, user should not be locked", !user.getLocked());
            assertTrue(
                    "Unexpected Adress",
                    new Address("CH", 1007, "Lausanne", "Vaud", 15, "Avenue de Cour")
                            .equals(user.getAddress()));
            assertTrue("Unexpected birthday", birthDay.compareTo(user.getBirthDate()) == 0);
            assertTrue("Unexpected gender", User.Gender.MALE.equals(user.getGender()));
            assertTrue(
                    "Unexpected areas of interest",
                    new CollectionComparator<Location>().compare(
                            new ArrayList<Location>(user.getAreasOfInterest()),
                            locationsOfInterest));
            assertTrue("Unexpected events attended", user.getEventsAttended().size() == 0);
        } catch (ParserException | UserClientException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void alreadyRegistered() {
        final int eventIdToRegister = 7;
        final String userToRegister = "lio";
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient("http://beecreative.ch", networkProvider);
        try{
            if(getRegistrationId(userToRegister,eventIdToRegister,networkProvider) != null) {
                fail("User: " + userToRegister + " cannot be registered to event (id) " + eventIdToRegister
                        + "for this test.");
            }
        } catch (UserClientException e){
           fail(e.getMessage());
        }
        try{
            userClient.registerUser(userToRegister,eventIdToRegister);
            userClient.registerUser(userToRegister,eventIdToRegister);
        }catch (UserClientException e){
            assertEquals("You are already registered to this event", e.getMessage().replace("\"","").replace("\n",""));
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
        UserClient userClient = new NetworkUserClient("http://beecreative.ch", networkProvider);
        try{
            userClient.registerUser(userToRegister,eventIdToRegister);
        } catch (UserClientException e){
            assertEquals("You are not in the age range of this Event." +
                    " The age range is: 26 - 46 and you are 22", e.getMessage().replace("\"","").replace("\n",""));
        }
    }

    @Test
    public void postRegistrationToEvent() throws UserClientException{
        final int eventIdToRegister = 7;
        final String userToRegister = "lio";
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient("http://beecreative.ch", networkProvider);

        try {
            userClient.registerUser(userToRegister, eventIdToRegister);
        } catch (UserClientException e) {
            fail(e.getMessage());
        }
        Integer testRegistrationId = getRegistrationId(userToRegister,eventIdToRegister,networkProvider);
        assertTrue("Registration was not successful.", testRegistrationId != null);
        try {
            userClient.unregisterUser(testRegistrationId);
        } catch (UserClientException e) {
            fail(e.getMessage());
        }
    }
    @Ignore
    private Integer getRegistrationId(String username, int eventId, NetworkProvider networkProvider) throws UserClientException{
        String registrationsString;
        JSONArray registrations;
        try {
            registrationsString = networkProvider.getContent(
                    "http://beecreative.ch/api/users/" + username + "/registrations");
            registrations = new JSONArray(registrationsString);
        } catch (JSONException | IOException e) {
            throw new UserClientException(e);
        }


        HashMap<Integer, Integer> eventToRegistration = new HashMap<>();
        for (int i = 0; i < registrations.length(); i++) {
            try {
                JSONObject jsonRegistration = registrations.getJSONObject(i);
                JSONObject jsonEvent = jsonRegistration.getJSONObject("event");
                eventToRegistration.put(jsonEvent.getInt("id"), jsonRegistration.getInt("id"));
            } catch (JSONException e) {
                throw new UserClientException(e);
            }
        }
        return eventToRegistration.get(eventId);
    }

    //TODO: Server responses come sometimes in french other times in english.
    @Test
    public void postUserMalformat() {
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient(NetworkProvider.SERVER_URL, networkProvider);
        JSONObject jsonUser = new JSONObject();
        try {
            /**
             Should first check if DumbUser666 exists, which SHOULD NOT
             HAPPEN as this is a test only user.
             Using userClient.deleteUser("DumbUser666"); if it doesn't
             throws an exception and makes the test fail.
            */
            jsonUser.put(EMAIL.get(), "dumbuser666@gmail.com");
            jsonUser.put(USERNAME.get(), "DumbUser666");
            jsonUser.put("firstName", "Dumb");
            jsonUser.put("lastName", "User");
            jsonUser.put(GENDER.get(), "maleo");//test incorrect gender format
            jsonUser.put("birthDate", "18-02-1993");//test incorrect bday format
            jsonUser.put("facebookId", "666");
            jsonUser.put("plainPassword", "dumbpassword");
        } catch (JSONException e){
            fail(e.getMessage());
        }
        try{
            userClient.postUser(jsonUser);
        } catch (UserClientException e){
            StringBuilder expectedMessage= new StringBuilder();
            expectedMessage.append("Validation Failed: ");
            // make explicit the server response, it does not come with \s.
            expectedMessage.append("Le choix du sexe n'est pas valide." + " ");
            expectedMessage.append("This value is not valid." + " ");
            assertEquals(expectedMessage.toString(),e.getMessage());
        }
    }

    @Test
    public void postUserEmailUnameTaken() {
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient(NetworkProvider.SERVER_URL, networkProvider);
        JSONObject jsonUser = new JSONObject();
        try {
            jsonUser.put(EMAIL.get(), "dario.anongba@epfl.ch");//email should be taken
            jsonUser.put(USERNAME.get(), "Admin"); //username should be taken
            jsonUser.put("firstName", "SomeFirstName");
            jsonUser.put("lastName", "SomeLastName");
            jsonUser.put(GENDER.get(), "male");//test incorrect gender format
            jsonUser.put("birthDate", "18/02/1993");//test incorrect bday format
            jsonUser.put("facebookId", "666");
            jsonUser.put("plainPassword", "dumbpassword");
        } catch (JSONException e){
            fail(e.getMessage());
        }
        try{
            userClient.postUser(jsonUser);
        } catch (UserClientException e){
            StringBuilder expectedMessage= new StringBuilder();
            expectedMessage.append("Validation Failed: ");
            // make explicit the server response, it does not come with \s.
            expectedMessage.append("The email is already used" + " ");
            expectedMessage.append("The username is already used" + " ");
            assertEquals(expectedMessage.toString(),e.getMessage());
        }
    }

    @Test
    public void postUserTest() {
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient(NetworkProvider.SERVER_URL, networkProvider);
        List<Location> locationsOfInterest = new ArrayList<>();
        locationsOfInterest.add(new Location(2, "Genève"));
        locationsOfInterest.add(new Location(3, "Lausanne"));
        locationsOfInterest.add(new Location(4, "Fribourg"));
        locationsOfInterest.add(new Location(6, "Zürich"));
        locationsOfInterest.add(new Location(7, "Berne"));
        locationsOfInterest.add(new Location(8, "Bulle"));
        try {
            JSONObject jsonUser = new JSONObject();
            /**
             Should first check if DumbUser666 exists, which SHOULD NOT
             HAPPEN as this is a test only user.
             Using userClient.deleteUser("DumbUser666"); if it doesn't
             throws an exception and makes the test fail.
             */
            jsonUser.put(EMAIL.get(), "dumbuser666@gmail.com");
            jsonUser.put(USERNAME.get(), "DumbUser666");
            jsonUser.put("firstName", "Dumb");
            jsonUser.put("lastName", "User");
            jsonUser.put(GENDER.get(), "male");
            jsonUser.put("birthDate", "18/02/1993");
            jsonUser.put("facebookId", "666");
            jsonUser.put("plainPassword", "dumbpassword");
            JSONObject responseJSON = userClient.postUser(jsonUser);
            List<Location> areasOfInterest = new ArrayList<>();
            JSONArray areas = responseJSON.getJSONArray("locations_of_interest");
            for (int i = 0; i < areas.length(); i++) {
                JSONObject jsonArea = areas.getJSONObject(i);
                Location location = new LocationParser().parse(new SafeJSONObject(jsonArea));
                areasOfInterest.add(location);
            }
            String fb_id = responseJSON.getString("facebook_id");
            assertEquals(responseJSON.getString(EMAIL.get()), "dumbuser666@gmail.com");
            assertEquals(responseJSON.getString(USERNAME.get()), "DumbUser666");
            assertEquals(responseJSON.getString(FIRST_NAME.get()), "Dumb");
            assertEquals(responseJSON.getString(LAST_NAME.get()), "User");
            assertEquals(responseJSON.getString(GENDER.get()), "male");
            assertEquals(
                    responseJSON.getString("birth_date"),
                    "1993-02-18T00:00:00+0100");
            assertEquals(fb_id, "666");
            assertEquals(responseJSON.getBoolean(LOCKED.get()), false);
            assertEquals(responseJSON.getBoolean(ENABLED.get()), false);
            assertTrue(
                    "Unexpected locations of preference",
                    new CollectionComparator<Location>().compare(
                            locationsOfInterest,
                            areasOfInterest));
            userClient.deleteUser("DumbUser666");
        } catch (UserClientException | ParserException | JSONException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testGetSpeedDatingEvent() throws EventClientException {
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        EventClient eventClient = new NetworkEventClient("http://beecreative.ch", networkProvider);
        SpeedDatingEvent event = (SpeedDatingEvent) eventClient.fetchBy(6);
        //Oktoberfest event at Zurich.
        Date beginDate;
        Date endDate;
        try {
            beginDate = DateParser.parseFromString(
                    "2016-10-15T21:00:00+0200",
                    DateParser.SERVER_DATE_FORMAT);
            endDate = DateParser.parseFromString(
                    "2016-10-16T00:00:00+0200",
                    DateParser.SERVER_DATE_FORMAT);
        } catch (ParserException e) {
            throw new EventClientException(e);
        }

        assertEquals(event.getName(), "Let's celebrate Oktoberfest");
        assertEquals(
                event.getDescription(),
                "Come and join us" +
                " to celebrate the Oktoberfest , and use this occasion to meet new People . The event" +
                " will take place to Forum , a bar in the center of Zürich.");
        assertEquals(event.getImagePath(), "5647627162808.jpg");
        assertEquals(event.getMinAge(), 21);
        assertEquals(event.getMaxAge(), 32);
        assertEquals(event.getMaxPeople(), 40);
        assertEquals(event.getMenSeats(), 20);
        assertEquals(event.getWomenSeats(), 20);
        assertEquals(event.getWomenRegistered(), 0);

        assertEquals(event.getBasePrice(), 35.0, 0.000001);
        assertEquals(event.getDateBegin().toString(), beginDate.toString());
        assertEquals(event.getDateEnd().toString(), endDate.toString());
        assertEquals(event.getLocation().getName(), new Location(6, "Zürich").getName());
        assertEquals(
                event.getEstablishment(),
                new Establishment(
                        2,
                        "Forum",
                        Establishment.Type.BAR,
                        new Address("CH", 8004, "Zürich", "Zürich", 120, "Badenerstrasse"),
                        "+41 43 243 88 88",
                        "Located at the corner of Badenerstrasse, Forum is an airy lounge bar and restaurant, ideal for kicking back and unwinding.",
                        SafeJSONObject.DEFAULT_STRING,
                        250,
                        SafeJSONObject.DEFAULT_STRING)
                    );
    }

    @Ignore
    private static final class CollectionComparator<E> {
        @Ignore
        boolean compare(List<E> coll1, List<E> coll2) {
            if (coll1.size() != coll2.size()) {
                return false;
            }

            for (int i = 0; i < coll1.size(); i++) {
                if (!coll1.get(i).equals(coll2.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }
}
