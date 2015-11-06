package ch.epfl.sweng.swissaffinity.events;

import java.io.Serializable;
import java.util.Calendar;

import ch.epfl.sweng.swissaffinity.utilities.Location;

/**
 * Event interface.
 */
public interface Event extends Serializable {

    /**
     * The possible states of an event.
     */
    enum State {
        PENDING("pending"),
        CONFIRMED("confirmed"),
        CANCELLED("cancelled");

        private String mState;

        /**
         * Internal contructor of the enum.
         *
         * @param state the string representing the state.
         */
        State(String state) {
            mState = state;
        }

        /**
         * Getter for the state of an event.
         *
         * @param state the server API state.
         *
         * @return the state.
         */
        public static State getState(String state) {
            for (State s : State.values()) {
                if (s.mState.equals(state)) {
                    return s;
                }
            }
            return null;
        }
    }

    /**
     * @return the name of the event.
     */
    String getName();

    /**
     * @return the description of the event.
     */
    String getDesription();

    /**
     * @return the date of the event.
     */
    Calendar getDate();

    /**
     * @return the location of the event.
     */
    Location getLocation();
}
