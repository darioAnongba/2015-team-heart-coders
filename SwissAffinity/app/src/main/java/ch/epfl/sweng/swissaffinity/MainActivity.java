package ch.epfl.sweng.swissaffinity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ch.epfl.sweng.swissaffinity.db.UserDBAdapter;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.gui.EventExpandableListAdapter;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.events.NetworkEventClient;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT = "ch.epfl.sweng.swissaffinity.event";
    public static final String EXTRA_IMAGE = "ch.epfl.sweng.swissaffinity.image";
    public static final String SHARED_PREF = "ch.epfl.sweng.swissaffinity.shared_pref";
    public static final String USERNAME = "user_name";
    public static final String USERID = "user_id";

    private UserDBAdapter mDbHelper;

    public static String email;
    public static String userName;

    public static boolean REGISTERED = false;

    public static final String SERVER_URL = "http://www.beecreative.ch";

    private static EventClient mEventClient;

    private EventExpandableListAdapter mListAdapter;
    private SharedPreferences sharedPreferences;


    public static EventClient getEventClient() {
        return mEventClient;
    }

    public static void setEventClient(EventClient eventClient) {
        mEventClient = eventClient;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEventClient(new NetworkEventClient(SERVER_URL, new DefaultNetworkProvider()));
        mListAdapter = new EventExpandableListAdapter(this);
        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDbHelper = new UserDBAdapter(this);

        String userName = sharedPreferences.getString(USERNAME, null);
        String userId = sharedPreferences.getString(USERID, null);
        String welcomeText = getString(R.string.main_not_registered);
        if (userName == null) {
            REGISTERED = false;
        } else {
            REGISTERED = true;
            welcomeText = String.format(getString(R.string.welcome_registered_text), userName);
        }
        ((TextView) findViewById(R.id.mainWelcomeText)).setText(welcomeText);
        createData();
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

    private void createData() {
        if (isNetworkConnected()) {
            if (mListAdapter.getGroupCount() == 0) {
                new DownloadEventsTask().execute();
                mDbHelper.open();
                try {
                    fillData();
                } catch (SQLException e) {
                    // TODO: handle exception here
                }
                mDbHelper.close();
            }
        } else {
            Toast.makeText(MainActivity.this, "No Network", Toast.LENGTH_LONG).show();
        }
    }

    private void fillData() throws SQLException {
        Cursor dataCursor = mDbHelper.fetchAllData();
        if (dataCursor != null && dataCursor.getCount() > 2) {
            dataCursor.moveToFirst();
            TextView view = (TextView) findViewById(R.id.mainWelcomeText);
            userName = dataCursor.getString(2);
            email = dataCursor.getString(dataCursor.getColumnIndex(mDbHelper.KEY_EMAIL));
            view.setText("Welcome " + userName + "\n" + email);
        }
    }


    private class DownloadEventsTask extends AsyncTask<Void, Void, List<Event>> {

        @Override
        protected List<Event> doInBackground(Void... args) {
            List<Event> result = null;
            try {
                result = mEventClient.fetchAll();
            } catch (EventClientException e) {
                // TODO: check for an error handling
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Event> result) {
            fillEvents(result);
        }
    }

    private class DownloadImageTask extends AsyncTask<Event, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Event... params) {
            Bitmap image = null;
            try {
                image = mEventClient.imageFor(params[0]);
            } catch (EventClientException e) {
            }
            return image;
        }
    }

    private void fillEvents(List<Event> result) {
        String myEvents = getResources().getString(R.string.my_events);
        String upcomingEvents = getResources().getString(R.string.upcoming_events);
        if (REGISTERED) {
            mListAdapter.addGroup(myEvents);
            // TODO : add the events the user is registered to.
        }
        mListAdapter.addGroup(upcomingEvents);
        if (result != null) {
            Collections.sort(
                    result, new Comparator<Event>() {
                        @Override
                        public int compare(Event lhs, Event rhs) {
                            return lhs.getDateBegin().compareTo(rhs.getDateBegin());
                        }
                    });
            for (Event e : result) {
                mListAdapter.addChild(upcomingEvents, e);
            }
        }
        final ExpandableListView listView =
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
                        Intent intent = new Intent(getApplicationContext(), EventActivity.class);
                        Event event = (Event) mListAdapter.getChild(groupPosition, childPosition);
                        intent.putExtra(EXTRA_EVENT, event);
                        Bitmap image = null;
                        try {
                            image = new DownloadImageTask().execute(event).get();
                            if (image != null) {
                                image = Bitmap.createScaledBitmap(image, 128, 128, true);
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            // no image...
                        }
                        intent.putExtra(EXTRA_IMAGE, image);
                        startActivity(intent);
                        return true;
                    }
                });
        if (mListAdapter.getChildrenCount(0) == 0) {
            listView.expandGroup(1);
        } else {
            listView.expandGroup(0);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        return network != null && network.isConnected();
    }
}
