package ch.epfl.sweng.swissaffinity.BusinessLogic;

import java.util.List;

import ch.epfl.sweng.swissaffinity.BusinessLogic.utilities.Confirmation;
import ch.epfl.sweng.swissaffinity.BusinessLogic.utilities.Notification;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.users.BaseUser;
import ch.epfl.sweng.swissaffinity.users.Contributor;
import ch.epfl.sweng.swissaffinity.users.User;

/**
 * Created by Joel on 10/25/2015.
 * Interface intended for handling basic event management
 * and communicate with Contributor when necessary.
 */
public abstract class EventDataManager {
    private final Event event;
    private List<User> users;
    private List<Contributor> contributors;

    public EventDataManager(Event event){
        this.event = event;
    }
    public void subscribe (BaseUser baseUser){
        return;
    }
    public Notification getWaitingList(){return null;}
    protected Confirmation sendConfirmation(User user){
        return null;
    }
    protected Notification updateContributor(Contributor contributor){
        return null;
    }
}
