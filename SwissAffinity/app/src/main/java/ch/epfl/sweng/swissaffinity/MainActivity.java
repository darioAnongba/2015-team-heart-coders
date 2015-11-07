package ch.epfl.sweng.swissaffinity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginResult;

import java.util.List;

import ch.epfl.sweng.swissaffinity.db.UserDBAdapter;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.gui.EventExpandableListAdapter;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.EventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkEventClient;

public class MainActivity extends AppCompatActivity {

    private static final String SERVER_URL = "http://www.beecreative.ch/api/";
    private EventClient mEventClient;
    private UserDBAdapter mDbHelper;

    public static String email;
    public static String userName;
    public static boolean USER_REGISTERED = true;

    private EventExpandableListAdapter mListAdapter;

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
        mDbHelper = new UserDBAdapter(this);
        mDbHelper.open();
        mDbHelper.createData("Furkan", "sahinffurkan@gmail.com", "123456", "male");
        fillData();
        mDbHelper.close();

       TextView view =(TextView) findViewById(R.id.mainWelcomeText);
        view.setText(userName + email);

        if (!USER_REGISTERED) {
            login();
        } else {
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

    private void login() {
        TextView textView = (TextView) findViewById(R.id.mainWelcomeText);
        textView.setText(R.string.welcome_not_registered_text);
        ((Button) findViewById(R.id.mainLoginButton)).setVisibility(View.VISIBLE);
        ((Button) findViewById(R.id.mainRegisterButton)).setVisibility(View.VISIBLE);
        final Button button = (Button) findViewById(R.id.mainLoginButton);
        button.setOnClickListener(new View.OnClickListener() {
                                      public void onClick(View v) {
                                          Intent myIntent = new Intent(MainActivity.this, FacebookActivity.class);
                                          MainActivity.this.startActivity(myIntent);
                                      }
                                  }

        );
    }

    private void createData() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // fetch data
            new DownloadEventsTask().execute();
            mDbHelper.open();
            fillData();
        } else {
            // display error
            Toast.makeText(MainActivity.this, "No Network", Toast.LENGTH_LONG).show();
        }
    }

    private void fillData(){
        Cursor dataCursor = mDbHelper.fetchAllData();
        if(dataCursor != null) {

            TextView view =(TextView) findViewById(R.id.mainWelcomeText);
            // Now create a simple cursor adapter and set it to display
            dataCursor.getColumnCount();
            dataCursor.getColumnName(0);
            dataCursor.getColumnName(1);
            dataCursor.moveToFirst();
            userName = dataCursor.getString(dataCursor.getColumnIndex(mDbHelper.KEY_NAME));
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
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.mainEventListView);
        listView.setAdapter(mListAdapter);
        listView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                startActivity(new Intent(getApplicationContext(), EventActivity.class));
                //Toast.makeText(getBaseContext(), "Clicked!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listView.expandGroup(0);
    }

    @Override
    protected void onPause(){
        super.onPause();
    }


}
