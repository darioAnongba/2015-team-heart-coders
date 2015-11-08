package ch.epfl.sweng.swissaffinity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import java.util.List;

import ch.epfl.sweng.swissaffinity.db.userDBAdapter;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.gui.EventExpandableListAdapter;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.events.NetworkEventClient;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT = "ch.epfl.sweng.swissaffinity.event";
    public static final String SHARED_PREF = "ch.epfl.sweng.swissaffinity.shared_pref";
    public static final String USERNAME = "user_name";
    public static final String USERID = "user_id";
    private static final String SERVER_URL = "http://www.beecreative.ch";

    private EventClient mEventClient;

    private userDBAdapter mDbHelper;

    public static String email;
    public static String userName;
    public static boolean USER_REGISTERED = false;

    private EventExpandableListAdapter mListAdapter;
    private SharedPreferences sharedPreferences;


    public EventClient getEventClient() {
        return mEventClient;
    }

    public void setEventClient(EventClient eventClient) {
        mEventClient = eventClient;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEventClient(new NetworkEventClient(SERVER_URL, new DefaultNetworkProvider()));
        mListAdapter = new EventExpandableListAdapter(this);
        sharedPreferences =
                getApplicationContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mDbHelper = new userDBAdapter(this);

        String userName = sharedPreferences.getString(USERNAME, null);
        String userId = sharedPreferences.getString(USERID, null);

        if (userName == null) {
            startActivity(new Intent(this, AboutActivity.class));
        } else {
            String welcomeText =
                    String.format(getString(R.string.welcome_registered_text), userName);
            ((TextView) findViewById(R.id.mainWelcomeText)).setText(welcomeText);
            createData();
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

    private void createData() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            new DownloadEventsTask().execute();
            mDbHelper.open();
            try {
                fillData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            mDbHelper.close();
        } else {
            // display error
            Toast.makeText(MainActivity.this, "No Network", Toast.LENGTH_LONG).show();
        }
    }

    private void fillData() throws SQLException {
        Cursor dataCursor = mDbHelper.fetchAllData();
        if(dataCursor != null) {
            dataCursor.moveToFirst();
            TextView view =(TextView) findViewById(R.id.mainWelcomeText);
            // Now create a simple cursor adapter and set it to display
            userName = dataCursor.getString(2);
            email = dataCursor.getString(dataCursor.getColumnIndex(mDbHelper.KEY_EMAIL));
            view.setText("Welcome " + userName + "\n" +  email);

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
            displayEvents(result);
        }
    }

    private void displayEvents(List<Event> result) {

        String myEvents = getResources().getString(R.string.my_events);
        String upcomingEvents = getResources().getString(R.string.upcoming_events);

        mListAdapter.addGroup(myEvents);
        mListAdapter.addGroup(upcomingEvents);
        if (result != null) {
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
                        intent.putExtra(
                                EXTRA_EVENT,
                                (Event) mListAdapter.getChild(groupPosition, childPosition));
                        startActivity(intent);
                        Toast.makeText(getBaseContext(), "Clicked!", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
        listView.expandGroup(0);
        listView.expandGroup(1);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }


}
