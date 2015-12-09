package ch.epfl.sweng.swissaffinity.end_to_end;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.epfl.sweng.swissaffinity.MainActivity;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.users.NetworkUserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by Joel on 11/19/2015.
 * Modified by Dario on 09.12.2015
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class GetUserTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() {
        mActivityRule.getActivity();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullNetworkProviderForUser() {
        new NetworkUserClient(NetworkProvider.SERVER_URL, null);
    }

    @Test
    public void testGetUser() {
        try {
            NetworkProvider networkProvider = new DefaultNetworkProvider();
            UserClient userClient = new NetworkUserClient(NetworkProvider.SERVER_URL, networkProvider);
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
                            new ArrayList<>(user.getAreasOfInterest()),
                            locationsOfInterest));
            assertTrue("Unexpected events attended", user.getEventsAttended().size() == 0);
        } catch (ParserException | UserClientException e) {
            fail(e.getMessage());
        }
    }

    @Ignore
    public static final class CollectionComparator<E> {
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
