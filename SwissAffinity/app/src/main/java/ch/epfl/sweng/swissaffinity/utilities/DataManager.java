package ch.epfl.sweng.swissaffinity.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ch.epfl.sweng.swissaffinity.MainActivity;
import ch.epfl.sweng.swissaffinity.R;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.gui.EventExpandableListAdapter;
import ch.epfl.sweng.swissaffinity.users.Registration;
import ch.epfl.sweng.swissaffinity.users.User;
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
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

/**
 * Manager for the data fetched from the server.
 */
public class DataManager {

    private final static List<Event> MY_EVENTS = new ArrayList<>();
    private final static List<Event> UPCOMING_EVENTS = new ArrayList<>();
    private final static List<Registration> REGISTRATIONS = new ArrayList<>();

    private static EventClient EVENT_CLIENT;
    private static UserClient USER_CLIENT;

    private DataManager() {
    }

    public static EventClient getEventClient() {
        if (EVENT_CLIENT == null) {
            EVENT_CLIENT = new NetworkEventClient(
                    NetworkProvider.SERVER_URL,
                    new DefaultNetworkProvider());
        }
        return EVENT_CLIENT;
    }

    public static void setEventClient(EventClient eventClient) {
        EVENT_CLIENT = eventClient;
    }

    public static UserClient getUserClient() {
        if (USER_CLIENT == null) {
            USER_CLIENT =
                    new NetworkUserClient(NetworkProvider.SERVER_URL, new DefaultNetworkProvider());
        }
        return USER_CLIENT;
    }

    public static void setUserClient(UserClient userClient) {
        USER_CLIENT = userClient;
    }

    public static boolean hasData() {
        return !MY_EVENTS.isEmpty() || !UPCOMING_EVENTS.isEmpty();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        return network != null && network.isConnected();
    }

    public static void displayAlert(final Context context) {
        if (!isConnected(context)) {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle(R.string.alert_no_internet)
                 .setMessage(R.string.alert_message)
                 .setPositiveButton(
                         R.string.alert_positive, new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog, int which) {
                                 displayAlert(context);
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

    public static void updateData() {
        List<Event> myEvents = new ArrayList<>();
        List<Event> upcomingEvents = new ArrayList<>();
        List<Registration> registrations = new ArrayList<>();
        String userName = MainActivity.getSharedPrefs().getString(USERNAME.get(), "");
        try {
            upcomingEvents.addAll(getEventClient().fetchAll());
            registrations.addAll(getEventClient().fetchForUser(userName));
        } catch (EventClientException e) {
            Log.e("FetchEvent", e.getMessage());
        }
        for (Registration registration : registrations) {
            myEvents.add(registration.getEvent());
        }
        upcomingEvents.removeAll(myEvents);
        Collections.sort(myEvents);
        Collections.sort(upcomingEvents);
        REGISTRATIONS.clear();
        REGISTRATIONS.addAll(registrations);
        MY_EVENTS.clear();
        MY_EVENTS.addAll(myEvents);
        UPCOMING_EVENTS.clear();
        UPCOMING_EVENTS.addAll(upcomingEvents);
    }

    private static List<String> getGroups(Context context) {
        String myEvents = context.getString(R.string.my_events);
        String upcomingEvents = context.getString(R.string.upcoming_events);
        return Arrays.asList(myEvents, upcomingEvents);
    }

    public static void displayData(ExpandableListView listView) {
        if (hasData()) {
            EventExpandableListAdapter adapter =
                    (EventExpandableListAdapter) listView.getExpandableListAdapter();
            adapter.setData(getGroups(listView.getContext()), MY_EVENTS, UPCOMING_EVENTS);
            for (int i = 0; i < adapter.getGroupCount(); ++i) {
                listView.expandGroup(i);
            }
        }
    }

    public static void deleteUser() {
        REGISTRATIONS.clear();
        MY_EVENTS.clear();
        UPCOMING_EVENTS.clear();
        MainActivity.getSharedPrefs().edit()
                    .putString(FACEBOOK_ID.get(), null)
                    .putString(USERNAME.get(), null)
                    .putString(LAST_NAME.get(), null)
                    .putString(FIRST_NAME.get(), null)
                    .putString(GENDER.get(), null)
                    .putString(BIRTHDAY.get(), null)
                    .putString(EMAIL.get(), null)
                    .apply();
    }

    public static void saveUser(User user) {
        MainActivity.getSharedPrefs().edit()
                    .putString(FACEBOOK_ID.get(), user.getFacebookId())
                    .putString(USERNAME.get(), user.getUsername())
                    .putString(LAST_NAME.get(), user.getLastName())
                    .putString(FIRST_NAME.get(), user.getFirstName())
                    .putString(GENDER.get(), user.getGender().get())
                    .putString(BIRTHDAY.get(), user.getBirthDate().toString())
                    .putString(EMAIL.get(), user.getEmail())
                    .apply();
    }

    public static int getRegistrationId(int eventId) {
        for (Registration registration : REGISTRATIONS) {
            if (registration.getEvent().getId() == eventId) {
                return registration.getId();
            }
        }
        return -1;
    }

    public static Event getEvent(int eventId) {
        for (Event event : MY_EVENTS) {
            if (event.getId() == eventId) {
                return event;
            }
        }
        for (Event event : UPCOMING_EVENTS) {
            if (event.getId() == eventId) {
                return event;
            }
        }
        return null;
    }
}
