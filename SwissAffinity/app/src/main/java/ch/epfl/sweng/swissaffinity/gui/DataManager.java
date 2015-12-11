package ch.epfl.sweng.swissaffinity.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.sweng.swissaffinity.MainActivity;
import ch.epfl.sweng.swissaffinity.R;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.users.Registration;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.events.NetworkEventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.NetworkUserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClient;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BIRTHDAY;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FACEBOOK_ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.GENDER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOCATIONS_INTEREST;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

/**
 * Manager for the data fetched from the server. (static class)
 */
public class DataManager {

    private final static List<Event> ALL_EVENTS = new ArrayList<>();
    private final static List<Registration> REGISTRATIONS = new ArrayList<>();

    private static EventClient EVENT_CLIENT;
    private static UserClient USER_CLIENT;

    private DataManager() {
    }

    /**
     * Getter for the event client
     *
     * @return the event client
     */
    public static EventClient getEventClient() {
        if (EVENT_CLIENT == null) {
            EVENT_CLIENT = new NetworkEventClient(
                NetworkProvider.SERVER_URL,
                new DefaultNetworkProvider());
        }
        return EVENT_CLIENT;
    }

    /**
     * Setter for the event client (FOR TESTING PURPOSE ONLY)
     *
     * @param eventClient the manually prepared event client
     */
    public static void setEventClient(EventClient eventClient) {
        if (eventClient == null) {
            throw new IllegalArgumentException();
        }
        EVENT_CLIENT = eventClient;
    }

    /**
     * Getter for the user client
     *
     * @return the user client
     */
    public static UserClient getUserClient() {
        if (USER_CLIENT == null) {
            USER_CLIENT =
                new NetworkUserClient(NetworkProvider.SERVER_URL, new DefaultNetworkProvider());
        }
        return USER_CLIENT;
    }

    /**
     * Setter for the user client (FOR TESTING PURPOSE ONLY)
     *
     * @param userClient the manually prepared user client
     */
    public static void setUserClient(UserClient userClient) {
        if (userClient == null) {
            throw new IllegalArgumentException();
        }
        USER_CLIENT = userClient;
    }

    /**
     * Display an alert if there is no network
     *
     * @param context the activity context
     */
    public static void verifyNetworkConnection(final Context context) {
        if (!isNetworkConnected(context)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle(R.string.alert_no_internet)
                 .setMessage(R.string.alert_message)
                 .setPositiveButton(
                     R.string.alert_positive, new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int which) {
                             verifyNetworkConnection(context);
                             dialog.dismiss();
                         }
                     })
                 .setNegativeButton(
                     R.string.alert_negative, new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int which) {
                             dialog.dismiss();
                         }
                     });
            alert.show();
        }
    }

    /**
     * @return if there are events in the manager
     */
    public static boolean hasData() {
        return !REGISTRATIONS.isEmpty() || !ALL_EVENTS.isEmpty();
    }

    /**
     * Update the internal data of the manager<br>
     * Has to be used async.
     */
    public static void updateData(Context context) {
        if (context == null) {
            throw new IllegalArgumentException();
        }
        if (isNetworkConnected(context)) {
            List<Event> allEvents = new ArrayList<>();
            List<Registration> registrations = new ArrayList<>();
            String userName = MainActivity.getPreferences().getString(USERNAME.get(), "");
            try {
                allEvents.addAll(getEventClient().fetchAll());
                registrations.addAll(getEventClient().fetchForUser(userName));
            } catch (EventClientException e) {
                Log.e("FetchEvent", e.getMessage());
            }
            Collections.sort(allEvents);
            REGISTRATIONS.clear();
            REGISTRATIONS.addAll(registrations);
            ALL_EVENTS.clear();
            ALL_EVENTS.addAll(allEvents);
        }
    }

    /**
     * Fill the data in the given expandable list view.
     *
     * @param listView the expandable list view
     */
    public static void displayData(ExpandableListView listView) {
        if (listView == null) {
            throw new IllegalArgumentException();
        }
        if (hasData()) {
            List<Event> myEvents = getMyEvents(REGISTRATIONS);
            List<Event> upcomingEvents = filterEvents(ALL_EVENTS);
            upcomingEvents.removeAll(myEvents);
            EventExpandableListAdapter adapter =
                (EventExpandableListAdapter) listView.getExpandableListAdapter();
            adapter.setData(Arrays.asList(myEvents, upcomingEvents));
            for (int i = 0; i < adapter.getGroupCount(); ++i) {
                listView.expandGroup(i);
            }
        }
    }

    /**
     * Delete the user data (f.ex. after a log out)
     */
    public static void deleteUser() {
        REGISTRATIONS.clear();
        ALL_EVENTS.clear();
        MainActivity.getPreferences().edit()
                    .putString(FACEBOOK_ID.get(), null)
                    .putString(USERNAME.get(), null)
                    .putString(LAST_NAME.get(), null)
                    .putString(FIRST_NAME.get(), null)
                    .putString(GENDER.get(), null)
                    .putString(BIRTHDAY.get(), null)
                    .putString(EMAIL.get(), null)
                    .putStringSet(LOCATIONS_INTEREST.get(), null)
                    .apply();
    }

    /**
     * Save the user data
     *
     * @param user the user
     */
    public static void saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        }
        SharedPreferences preferences = MainActivity.getPreferences();
        Set<String> locations = preferences.getStringSet(
            LOCATIONS_INTEREST.get(),
            new HashSet<String>());
        if (locations.isEmpty()) {
            for (Location location : user.getAreasOfInterest()) {
                locations.add(location.getName());
            }
        }
        preferences.edit()
                   .putString(FACEBOOK_ID.get(), user.getFacebookId())
                   .putString(USERNAME.get(), user.getUsername())
                   .putString(LAST_NAME.get(), user.getLastName())
                   .putString(FIRST_NAME.get(), user.getFirstName())
                   .putString(GENDER.get(), user.getGender().get())
                   .putString(BIRTHDAY.get(), user.getBirthDate().toString())
                   .putString(EMAIL.get(), user.getEmail())
                   .putStringSet(LOCATIONS_INTEREST.get(), locations)
                   .apply();
    }

    /**
     * Find a registration given an event ID
     *
     * @param eventId the event ID
     * @return the registration ID
     */
    public static int getRegistrationId(int eventId) {
        for (Registration registration : REGISTRATIONS) {
            if (registration.getEvent().getId() == eventId) {
                return registration.getId();
            }
        }
        return -1;
    }

    /**
     * Return an event given its ID
     *
     * @param eventId the event ID
     * @return the event
     */
    public static Event getEvent(int eventId) {
        for (Event event : ALL_EVENTS) {
            if (event.getId() == eventId) {
                return event;
            }
        }
        return null;
    }

    /**
     * Getter for the events registered
     *
     * @param registrations the registrations
     * @return the events registered
     */
    private static List<Event> getMyEvents(List<Registration> registrations) {
        List<Event> result = new ArrayList<>();
        for (Registration registration : registrations) {
            result.add(registration.getEvent());
        }
        return result;
    }

    /**
     * Getter for upcoming events from all events according locations
     *
     * @param allEvents all the events
     * @return the upcoming events
     */
    private static List<Event> filterEvents(List<Event> allEvents) {
        Set<String> myLocations = MainActivity.getPreferences()
                                              .getStringSet(LOCATIONS_INTEREST.get(), null);
        if (myLocations != null) {
            List<Event> result = new ArrayList<>();
            for (Event event : allEvents) {
                String location = event.getLocation().getName();
                if (myLocations.contains(location)) {
                    result.add(event);
                }
            }
            return result;
        }
        return allEvents;
    }

    /**
     * @param context the activity context
     * @return if there is connection to the internet
     */
    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager =
            (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        return network != null && network.isConnected();
    }
}
