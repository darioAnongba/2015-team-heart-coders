package ch.epfl.sweng.swissaffinity.users;

import ch.epfl.sweng.swissaffinity.events.Event;

/**
 * Representation of a registration. It links an event to a user.
 */
public class Registration {
    private final int mId;
    private final Event mEvent;

    /**
     * Constructor of the class
     *
     * @param id    the registration id
     * @param event the event it refers to
     */
    public Registration(int id, Event event) {
        if (id < 0 || event == null) {
            throw new IllegalArgumentException();
        }
        mId = id;
        mEvent = event;
    }

    /**
     * Getter for the event
     *
     * @return the event linked by the registration
     */
    public Event getEvent() {
        return mEvent;
    }

    /**
     * Getter for the ID
     *
     * @return the registration ID
     */
    public int getId() {
        return mId;
    }
}
