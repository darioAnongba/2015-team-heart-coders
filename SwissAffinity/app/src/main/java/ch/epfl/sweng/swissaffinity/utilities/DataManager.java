package ch.epfl.sweng.swissaffinity.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.swissaffinity.MainActivity;
import ch.epfl.sweng.swissaffinity.R;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.gui.EventExpandableListAdapter;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.events.NetworkEventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.NetworkUserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClient;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

/**
 * Manager for the data fetched from the server.
 */
public class DataManager {
    private static final String MY_EVENTS = "ch.epfl.sweng.swissaffinity.MY_EVENTS";
    private static final String UPCOMING_EVENTS = "ch.epfl.sweng.swissaffinity.UPCOMING_EVENTS";

    private static EventClient EVENT_CLIENT;
    private static UserClient USER_CLIENT;

    private final HashMap<String, Event> mMyEvents;
    private final HashMap<String, Event> mUpcomingEvents;
    private final Context mContext;
    private final ExpandableListView mListView;

    public DataManager(Context context, ExpandableListView listView) {
        if (context == null) {
            throw new IllegalArgumentException();
        }
        mMyEvents = new HashMap<>();
        mUpcomingEvents = new HashMap<>();
        mContext = context;
        mListView = listView;
    }


    public static EventClient getEventClient() {
        if (EVENT_CLIENT == null) {
            return new NetworkEventClient(NetworkProvider.SERVER_URL, new DefaultNetworkProvider());
        }
        return EVENT_CLIENT;
    }

    public static void setEventClient(EventClient eventClient) {
        EVENT_CLIENT = eventClient;
    }

    public static UserClient getUserClient() {
        if (USER_CLIENT == null) {
            return new NetworkUserClient(NetworkProvider.SERVER_URL, new DefaultNetworkProvider());
        }
        return USER_CLIENT;
    }

    public static void setUserClient(UserClient userClient) {
        USER_CLIENT = userClient;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        return network != null && network.isConnected();
    }

    public List<Event> getMyEvents() {
        return getList(mMyEvents.values());
    }

    public List<Event> getUpcomingEvents() {
        return getList(mUpcomingEvents.values());
    }

    public void saveInstance(Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException();
        }
        bundle.putSerializable(MY_EVENTS, mMyEvents);
        bundle.putSerializable(UPCOMING_EVENTS, mUpcomingEvents);
    }

    public void restoreInstance(Bundle bundle) {
        if (bundle == null) {
            throw new IllegalArgumentException();
        }
        Map<String, Event> myEvents = (Map<String, Event>) bundle.getSerializable(MY_EVENTS);
        Map<String, Event> upcomingEvents =
                (Map<String, Event>) bundle.getSerializable(UPCOMING_EVENTS);
        if (myEvents != null && !myEvents.isEmpty()) {
            mMyEvents.putAll(myEvents);
        }
        if (upcomingEvents != null && !upcomingEvents.isEmpty()) {
            mUpcomingEvents.putAll(upcomingEvents);
        }
        setData();
    }

    public void updateData() {
        if (!mUpcomingEvents.isEmpty() || !mMyEvents.isEmpty()) {
            setData();
        }
        new DownloadEventsTask().execute();
    }

    private void setData() {
        ProgressDialog dialog = MainActivity.getLoadingDialog(mContext);
        dialog.show();
        EventExpandableListAdapter adapter =
                (EventExpandableListAdapter) mListView.getExpandableListAdapter();
        adapter.setData(getMyEvents(), getUpcomingEvents());
        for (int i = 0; i < adapter.getGroupCount(); ++i) {
            mListView.expandGroup(i);
        }
        dialog.dismiss();
    }

    private static List<Event> getList(Collection<Event> events) {
        List<Event> listOfEvents = new ArrayList<>(events);
        Collections.sort(listOfEvents);
        return listOfEvents;
    }

    private final class DownloadEventsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            List<Event> myEvents = new ArrayList<>();
            List<Event> upcomingEvents = new ArrayList<>();
            String userName = MainActivity.getSharedPrefs().getString(USERNAME.get(), "");
            try {
                myEvents.addAll(getEventClient().fetchAllForUser(userName));
                upcomingEvents.addAll(getEventClient().fetchAll());
            } catch (EventClientException e) {
                Log.e("FetchEvent", e.getMessage());
            }
            upcomingEvents.removeAll(myEvents);
            for (Event event : myEvents) {
                mMyEvents.put(event.getId(), event);
            }
            for (Event event : upcomingEvents) {
                mUpcomingEvents.put(event.getId(), event);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setData();
            super.onPostExecute(aVoid);
        }
    }
}
