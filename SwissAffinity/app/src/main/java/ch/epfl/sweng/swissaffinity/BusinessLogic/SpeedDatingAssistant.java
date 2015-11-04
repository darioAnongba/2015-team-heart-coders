package ch.epfl.sweng.swissaffinity.BusinessLogic;

import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.users.User;

/**
 * Created by Joel on 10/25/2015.
 */
public final class SpeedDatingAssistant extends EventDataManager {
    private List<User> waitingList;

    public SpeedDatingAssistant(Event event) {
        super(event);
    }

    private boolean satisfiesRequirements(User user){
        return true;
    }
    private void putOnWaitingList(User user){
        return;
    }
}
