package ch.epfl.sweng.swissaffinity.NetworkTests;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import ch.epfl.sweng.swissaffinity.utilities.parsers.user.UserParser;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Joel on 11/19/2015.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class NetworkEndToEndTest {
    /**Admin (Dario) is used as test user here.
     * */
    @Test
    public void testGetUser() throws UserClientException{
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        UserClient userClient = new NetworkUserClient("http://beecreative.ch", networkProvider);
        User user = userClient.fetchByFacebookID(Integer.toString(1271175799)); //Dario's fb id
        Set<Location> locationsOfInterest = new HashSet<Location>();
        locationsOfInterest.add(new Location(3, "Lausanne"));
        Date birthDay;
        try{
            birthDay = DateParser.parseFromString("1993-02-02T00:00:00+0100");
        } catch(ParserException e){
            throw new UserClientException(e);
        }

        assertTrue("Unexpected username",user.getUsername().equals("Admin"));
        assertTrue("Unexpected first name",user.getFirstName().equals("Dario"));
        assertTrue("Unexpected last name",user.getLastName().equals("Anongba"));
        assertTrue("Unexpected profession",user.getProfession().equals("Student"));
        assertTrue("Unexpected home phone",user.getHomePhone().equals(UserParser.DEFAULT_STRING));
        assertTrue("Unexpected mobile phone",user.getMobilePhone().equals("+41799585615"));
        assertTrue("Unexpected profile picture",user.getProfilePicture().equals(UserParser.DEFAULT_STRING));
        assertTrue("Unexpected email",user.getEmail().equals("dario.anongba@epfl.ch"));
        assertTrue("Unexpected id",user.getId() == 1);
        assertTrue("Unexpected fb id",user.getFacebookId()==1271175799);
        assertTrue("Unexpected, user should be enabled",user.getEnabled());
        assertTrue("Unexpected, user should not be locked",!user.getLocked());
        assertTrue("Unexpected Adress", new Address("CH", 1007, "Lausanne", "Vaud", 15, "Avenue de Cour")
                .equals(user.getAddress()));
        assertTrue("Unexpected birthday", birthDay.compareTo(user.getBirthDate()) == 0);
        assertTrue("Unexpected gender", User.Gender.MALE.equals(user.getGender()));
        assertTrue("Unexpected areas of interest",
                new CollectionComparator<Location>().compare(user.getAreasOfInterest(),locationsOfInterest));
        assertTrue("Unexpected events attended",user.getEventsAttended().size()==0);

    }
    /**
    @Test
    public void testGetEvent() throws EventClientException{
        return;
    }
    **/
    private class CollectionComparator<E>{
        boolean compare (Collection<E> coll1, Collection<E> coll2){
            if (coll1.size()!=coll2.size()){
                return false;
            }
            List coll1List = new ArrayList(coll1);
            List coll2List = new ArrayList(coll2);
            for (int i = 0; i < coll1List.size() ; i++) {
                if(!coll1List.get(i).equals(coll2List.get(i))){
                    return false;
                }
            }
            return true;
        }
    }
}

