package ch.epfl.sweng.swissaffinity.BusinessLogic;

import java.util.List;

import ch.epfl.sweng.swissaffinity.BusinessLogic.utilities.Notification;
import ch.epfl.sweng.swissaffinity.BusinessLogic.utilities.Query;
import ch.epfl.sweng.swissaffinity.users.User;

/**
 * Created by Joel on 10/25/2015.
 * The purpose of this interface is to provide
 * a query interface for user data and communication with User class.
 * All changes in persistent user data and
 * interface are responsability of the User package.
 */
public abstract class UserDataManager {


    private List<User> subscribers;

    public void subscribe(User user) {
        return;
    }
    protected User getUserById(int id){
        return null;
    }
    protected List<User> getUserBase(){
        return null;
    }
    protected List<User> getUsersByQuery(Query query){
        return null;
    }
    protected Notification sendNotification(User user){
        return null;
    }

}
