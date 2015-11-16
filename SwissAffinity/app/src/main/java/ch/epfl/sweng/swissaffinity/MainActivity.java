package ch.epfl.sweng.swissaffinity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.gui.EventExpandableListAdapter;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.events.NetworkEventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.NetworkUserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FACEBOOK_ID;

/**
 * The main activity of the application.
 */
public class MainActivity extends AppCompatActivity {

    public static final String SERVER_URL = "http://www.beecreative.ch";

    public static final String EXTRA_EVENT = "ch.epfl.sweng.swissaffinity.event";
    public static final String EXTRA_USER = "ch.epfl.sweng.swissaffinity.user";

    public static SharedPreferences SHARED_PREFS;
    public static EventClient EVENT_CLIENT;
    public static UserClient USER_CLIENT;

    private static final String SHARED_PREFS_ID = "ch.epfl.sweng.swissaffinity.shared_prefs";

    private EventExpandableListAdapter mListAdapter;
    private User mUser;

    public static EventClient getEventClient() {
        if (EVENT_CLIENT == null) {
            return new NetworkEventClient(SERVER_URL, new DefaultNetworkProvider());
        }
        return EVENT_CLIENT;
    }

    public static void setEventClient(EventClient eventClient) {
        EVENT_CLIENT = eventClient;
    }

    public static UserClient getUserClient() {
        if (USER_CLIENT == null) {
            return new NetworkUserClient(SERVER_URL, new DefaultNetworkProvider());
        }
        return USER_CLIENT;
    }

    public static void setUserClient(UserClient userClient) {
        USER_CLIENT = userClient;
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        return network != null && network.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EVENT_CLIENT = getEventClient();
        USER_CLIENT = getUserClient();
        SHARED_PREFS = getSharedPreferences(SHARED_PREFS_ID, MODE_PRIVATE);
        mListAdapter = new EventExpandableListAdapter(this);

        if (isNetworkConnected(this)) {
            fetchUser();
            fetchEvents();
        } else if (savedInstanceState != null) {
            Toast.makeText(this, "We get saved state!", Toast.LENGTH_LONG).show();
            //TODO: get saved state of the app... (and save it also!)
        } else {
            Toast.makeText(this, "No Network", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchUser() {
        new DownloadUserTask().execute();
    }

    private void fetchEvents() {
        new DownloadEventsTask().execute();
    }

    private class DownloadEventsTask extends AsyncTask<Void, Void, List<Event>> {
        @Override
        protected List<Event> doInBackground(Void... args) {
            List<Event> result = new ArrayList<>();
            try {
                result.addAll(EVENT_CLIENT.fetchAll());
            } catch (EventClientException e) {
                Log.e("FetchEvents", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            String myEvents = getString(R.string.my_events);
            String upcomingEvents = getString(R.string.upcoming_events);
            if (mUser != null) {
                mListAdapter.addGroup(myEvents);
                // TODO : add the events the user is registered to.
            }
            mListAdapter.addGroup(upcomingEvents);
            for (Event event : events) {
                mListAdapter.addChild(upcomingEvents, event);
            }
            ExpandableListView listView =
                    (ExpandableListView) findViewById(R.id.mainEventListView);
            listView.setAdapter(mListAdapter);
            listView.setOnChildClickListener(
                    new OnChildClickListener() {
                        @Override
                        public boolean onChildClick(
                                ExpandableListView parent,
                                View v,
                                int groupPosition,
                                int childPosition,
                                long id)
                        {
                            Intent intent =
                                    new Intent(getApplicationContext(), EventActivity.class);
                            Event event =
                                    (Event) mListAdapter.getChild(groupPosition, childPosition);
                            intent.putExtra(EXTRA_EVENT, event).putExtra(EXTRA_USER, mUser);
                            startActivity(intent);
                            return true;
                        }
                    });
            if (mListAdapter.getChildrenCount(0) == 0) {
                listView.expandGroup(1);
            } else {
                listView.expandGroup(0);
            }
            super.onPostExecute(events);
        }
    }

    private class DownloadUserTask extends AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... params) {
            String facebookID = SHARED_PREFS.getString(FACEBOOK_ID.get(), null);
            if (facebookID != null) {
                try {
                    return USER_CLIENT.fetchByFacebookID(facebookID);
                } catch (UserClientException e) {
                    Log.e("FetchUser", e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            String welcomeText = getString(R.string.welcome_not_registered_text);
            if (user != null) {
                mUser = user;
                welcomeText = String.format(
                        getString(R.string.welcome_registered_text),
                        mUser.getUsername());
            }
            ((TextView) findViewById(R.id.mainWelcomeText)).setText(welcomeText);
            super.onPostExecute(user);
        }
    }
}
