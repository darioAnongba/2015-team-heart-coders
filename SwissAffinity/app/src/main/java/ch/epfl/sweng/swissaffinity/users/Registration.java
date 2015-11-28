package ch.epfl.sweng.swissaffinity.users;

/**
 * Created by Lionel on 27/11/15.
 */
public class Registration {
    private final int mId;
    private final int mEventId;

    public Registration(int id, int eventId) {
        if (id < 0 || eventId < 0) {
            throw new IllegalArgumentException();
        }
        mId = id;
        mEventId = eventId;
    }

    public int getEventId() {
        return mEventId;
    }

    public int getId() {
        return mId;
    }
}
