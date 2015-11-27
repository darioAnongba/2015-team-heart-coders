package ch.epfl.sweng.swissaffinity.EndToEndTests;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.ServerTags;
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

import static junit.framework.Assert.assertTrue;

/**
 * Created by Joel on 11/19/2015.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class NetworkEndToEndTest {
    /**
     * Admin (Dario) is used as test user here.
     */

    @Test(expected = IllegalArgumentException.class)
    public void testNullNetworkProviderForUser() {
        NetworkProvider networkProvider = null;
        new NetworkUserClient("http://beecreative.ch", networkProvider);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullNetworkProviderForEvent() {
        NetworkProvider networkProvider = null;
        new NetworkUserClient("http://beecreative.ch", networkProvider);
    }

    @Test
    public void testGetUser() throws UserClientException {
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient("http://beecreative.ch", networkProvider);
        User user = userClient.fetchByFacebookID(Integer.toString(1271175799)); //Dario's fb id
        List<Location> locationsOfInterest = new ArrayList<>();
        locationsOfInterest.add(new Location(3, "Lausanne"));
        Date birthDay;
        try {
            birthDay = DateParser.parseFromString("1993-02-02T00:00:00+0100");
        } catch (ParserException e) {
            throw new UserClientException(e);
        }

        assertTrue("Unexpected username", user.getUsername().equals("Admin"));
        assertTrue("Unexpected first name", user.getFirstName().equals("Dario"));
        assertTrue("Unexpected last name", user.getLastName().equals("Anongba"));
        assertTrue("Unexpected profession", user.getProfession().equals("Student"));
        assertTrue("Unexpected home phone", user.getHomePhone().equals(SafeJSONObject.DEFAULT_STRING));
        assertTrue("Unexpected mobile phone", user.getMobilePhone().equals("+41799585615"));
        assertTrue("Unexpected profile picture", user.getProfilePicture().equals(SafeJSONObject.DEFAULT_STRING));
        assertTrue("Unexpected email", user.getEmail().equals("dario.anongba@epfl.ch"));
        assertTrue("Unexpected id", user.getId() == 1);
        assertTrue("Unexpected fb id", user.getFacebookId() == 1271175799);
        assertTrue("Unexpected, user should be enabled", user.getEnabled());
        assertTrue("Unexpected, user should not be locked", !user.getLocked());
        assertTrue("Unexpected Adress", new Address("CH", 1007, "Lausanne", "Vaud", 15, "Avenue de Cour")
                .equals(user.getAddress()));
        assertTrue("Unexpected birthday", birthDay.compareTo(user.getBirthDate()) == 0);
        assertTrue("Unexpected gender", User.Gender.MALE.equals(user.getGender()));
        assertTrue("Unexpected areas of interest",
                new CollectionComparator<Location>().compare(new ArrayList<Location>(user.getAreasOfInterest()), locationsOfInterest));
        assertTrue("Unexpected events attended", user.getEventsAttended().size() == 0);

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
            if (e.getMessage().equals("You are already registered to this event")) {
                //SUCCESS
            }
        }
        String registrationsString;
        JSONArray registrations;
        try{
            registrationsString  = networkProvider.getContent("http://beecreative.ch/api/users/"+ userToRegister +"/registrations");
            registrations = new JSONArray(registrationsString);
        } catch (JSONException | IOException e){
            throw new UserClientException(e);
        }


        HashMap<Integer, Integer> eventToRegistration = new HashMap<>();
        for (int i = 0; i < registrations.length(); i++) {
            try {
                JSONObject jsonRegistration = registrations.getJSONObject(i);
                JSONObject jsonEvent = jsonRegistration.getJSONObject("event");
                eventToRegistration.put(jsonEvent.getInt("id"), jsonRegistration.getInt("id"));
            } catch (JSONException e){
                throw new UserClientException(e);
            }
        }
        Integer testRegistrationId = eventToRegistration.get(eventIdToRegister);
        assertTrue("Registration was not successful.", testRegistrationId != null);

        URL url;
        HttpURLConnection conn;
        int respCode;
        try {
            url = new URL("http://beecreative.ch/api/registrations/" + testRegistrationId.toString());
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            respCode = conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void postUserTest() throws UserClientException {
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient("http://beecreative.ch", networkProvider);
        List<Location> locationsOfInterest = new ArrayList<>();
        locationsOfInterest.add(new Location(2, "Genève"));
        locationsOfInterest.add(new Location(3, "Lausanne"));
        locationsOfInterest.add(new Location(4, "Fribourg"));
        locationsOfInterest.add(new Location(6, "Zürich"));
        locationsOfInterest.add(new Location(7, "Berne"));
        locationsOfInterest.add(new Location(8, "Bulle"));

        JSONObject jsonUser = new JSONObject();
        SafeJSONObject confirmationObject;
        JSONObject responseJSON;
        try {
            jsonUser.put("email", "dumbuser666@gmail.com");
            jsonUser.put("username", "DumbUser666");
            jsonUser.put("firstName", "Dumb");
            jsonUser.put("lastName", "User");
            jsonUser.put("gender", "male");
            jsonUser.put("birthDate", "18/02/1993");
            jsonUser.put("facebookId", "666");
            jsonUser.put("plainPassword", "dumbpassword");
            responseJSON = userClient.postUser(jsonUser);
            confirmationObject = new SafeJSONObject(responseJSON);
        } catch (JSONException e) {
            throw new UserClientException(e);
        }

        List<Location> areasOfInterest = new ArrayList<>();
        JSONArray areas;
        try {
            areas = confirmationObject.get(ServerTags.LOCATIONS_INTEREST.get(), new JSONArray());
            for (int i = 0; i < areas.length(); i++) {
                JSONObject jsonArea = areas.getJSONObject(i);
                Location location = new LocationParser().parse(new SafeJSONObject(jsonArea));
                areasOfInterest.add(location);
            }
        } catch (JSONException | ParserException e) {
            throw new UserClientException(e);
        }
        int fb_id;
        try {
            fb_id = confirmationObject.getInt(ServerTags.FACEBOOK_ID.get());
        } catch (JSONException e) {
            throw new UserClientException(e);
        }

        assertTrue("Unexpected email", confirmationObject.get(ServerTags.EMAIL.get(),
                SafeJSONObject.DEFAULT_STRING).equals("dumbuser666@gmail.com"));
        assertTrue("Unexpected username", confirmationObject.get(ServerTags.USERNAME.get(),
                SafeJSONObject.DEFAULT_STRING).equals("DumbUser666"));
        assertTrue("Unexpected firstName", confirmationObject.get(ServerTags.FIRST_NAME.get(),
                SafeJSONObject.DEFAULT_STRING).equals("Dumb"));
        assertTrue("Unexpected lastName", confirmationObject.get(ServerTags.LAST_NAME.get(),
                SafeJSONObject.DEFAULT_STRING).equals("User"));
        assertTrue("Unexpected gender", confirmationObject.get(ServerTags.GENDER.get(),
                SafeJSONObject.DEFAULT_STRING).equals("male"));
        assertTrue("Unexpected birthDate", confirmationObject.get(ServerTags.BIRTH_DATE.get(),
                SafeJSONObject.DEFAULT_STRING).equals("1993-02-18T00:00:00+0100"));
        assertTrue("Unexpected facebookId", fb_id == 666);
        assertTrue("Unexpected User should not be locked",
                !confirmationObject.get(ServerTags.LOCKED.get(), true));
        assertTrue("Unexpected User should be enable",
                !confirmationObject.get(ServerTags.ENABLED.get(), false));
        assertTrue("Unexpected locations of preference",
                new CollectionComparator<Location>().compare(locationsOfInterest, areasOfInterest));

        URL url;
        HttpURLConnection conn;
        int respCode;
        try {
            url = new URL("http://beecreative.ch/api/users/DumbUser666");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            respCode = conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
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
            beginDate = DateParser.parseFromString("2016-10-15T21:00:00+0200");
            endDate = DateParser.parseFromString("2016-10-16T00:00:00+0200");
        } catch (ParserException e) {
            throw new EventClientException(e);
        }

        assertTrue("Unexpected event name", event.getName().equals("Let's celebrate Oktoberfest"));
        assertTrue("Unexpected event description", event.getDescription().equals("Come and join us" +
                " to celebrate the Oktoberfest , and use this occasion to meet new People . The event" +
                " will take place to Forum , a bar in the center of Zürich."));
        assertTrue("Unexcpected event image path", event.getImagePath().equals("5647627162808.jpg"));
        assertTrue("Unexcpected min age", event.getMinAge() == 21);
        assertTrue("Unexpected max age", event.getMaxAge() == 32);
        assertTrue("Unexcpected max people", event.getMaxPeople() == 40);
        assertTrue("Unexpected men seats", event.getMenSeats() == 20);
        assertTrue("Unexpected women seats", event.getWomenSeats() == 20);
        assertTrue("Unexpected women registered", event.getWomenRegistered() == 0);

        assertTrue("Unexpected base price", event.getBasePrice() == 35.0);
        assertTrue("Unexpected begin date", event.getDateBegin().equals(beginDate));
        assertTrue("Unexpected end date", event.getDateEnd().equals(endDate));
        assertTrue("Unexpected Location", event.getLocation().equals(
                new Location(6, "Zürich")));
        assertTrue("Unexpected Establishment", event.getEstablishment().equals(
                        new Establishment(2, "Forum", Establishment.Type.BAR,
                                new Address("CH", 8004, "Zürich", "Zürich", 120, "Badenerstrasse"),
                                "+41 43 243 88 88",
                                "Located at the corner of Badenerstrasse, Forum is an airy lounge bar and restaurant, ideal for kicking back and unwinding.",
                                SafeJSONObject.DEFAULT_STRING,
                                250,
                                SafeJSONObject.DEFAULT_STRING))
        );
    }

    private class CollectionComparator<E> {
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
