package ch.epfl.sweng.swissaffinity.events;

import java.util.Calendar;

/**
 * Created by Lionel on 05/11/15.
 */
public interface Event {
    enum State {
        PENDING("pending"),
        CONFIRMED("confirmed"),
        CANCELLED("cancelled");

        private String mState;

        State(String state) {
            mState = state;
        }

        public static State getState(String name) {
            for (State s : State.values()) {
                if (s.mState.equals(name)) {
                    return s;
                }
            }
            return null;
        }
    }

    String getName();

    String getDesription();

    Calendar getDate();

    Location getLocation();
}
