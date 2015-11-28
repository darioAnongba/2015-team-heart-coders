package ch.epfl.sweng.swissaffinity.users;

import ch.epfl.sweng.swissaffinity.events.Event;

/**
 * Created by Lionel on 27/11/15.
 */
public class Registration {
    private final int mId;
    private final Event mEvent;

    public Registration(int id, Event event) {
        if (id < 0 || event == null) {
            throw new IllegalArgumentException();
        }
        mId = id;
        mEvent = event;
    }

    public Event getEvent() {
        return mEvent;
    }

    public int getId() {
        return mId;
    }
}
