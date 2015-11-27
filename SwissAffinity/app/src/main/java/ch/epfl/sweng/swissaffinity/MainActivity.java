package ch.epfl.sweng.swissaffinity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import ch.epfl.sweng.swissaffinity.gui.EventExpandableListAdapter;
import ch.epfl.sweng.swissaffinity.utilities.DataManager;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

/**
 * The main activity of the application.
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT = "ch.epfl.sweng.swissaffinity.event";
    public static final String EXTRA_USER = "ch.epfl.sweng.swissaffinity.user";

    private static final String SHARED_PREFS_ID = "ch.epfl.sweng.swissaffinity.shared_prefs";

    public static EventExpandableListAdapter mListAdapter;
    public static User mUser;               // made public and static for testing purposes!
    public static Context mContext;         // made public and static for testing purposes!
    public static SharedPreferences mSharedPrefs;


    private ExpandableListView mListView;

    public static SharedPreferences getSharedPrefs() {
        return mSharedPrefs;
    }

    public static ProgressDialog getLoadingDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(context.getString(R.string.loading));
        dialog.setIndeterminate(true);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPrefs = getSharedPreferences(SHARED_PREFS_ID, MODE_PRIVATE);
        mListView = (ExpandableListView) findViewById(R.id.mainEventListView);
        mListView.setAdapter(new EventExpandableListAdapter(this));

<<<<<<< HEAD
        EVENT_CLIENT = getEventClient();
        USER_CLIENT = getUserClient();
        SHARED_PREFS = getSharedPreferences(SHARED_PREFS_ID, MODE_PRIVATE);
        mListAdapter = new EventExpandableListAdapter(this);
        mContext = this;

        if (isNetworkConnected(this)) {
            fetchUser();
            fetchEvents();
        } else if (savedInstanceState != null) {
            Toast.makeText(this, "We get saved state!", Toast.LENGTH_LONG).show();
            //TODO: get saved state of the app... (and save it also!)
        } else {
            Toast.makeText(this, "No Network", Toast.LENGTH_LONG).show();
        }
=======
        updateUI();
>>>>>>> origin/max-dev
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

    private void updateUI() {
        String welcome = getString(R.string.welcome_registered_text);
        String userName = getSharedPrefs().getString(USERNAME.get(), "");
        TextView textView = (TextView) findViewById(R.id.mainWelcomeText);
        textView.setText(String.format(welcome, userName));

        boolean withDialog = true;
        if (DataManager.hasData()) {
            DataManager.setData(mListView);
            withDialog = false;
        }
        if (DataManager.isConnected(this)) {
            new DownloadEventsTask().execute(withDialog);
        } else {
            DataManager.displayAlert(this);
        }
    }

    private final class DownloadEventsTask extends AsyncTask<Boolean, Boolean, Void> {
        private final ProgressDialog dialog = getLoadingDialog(MainActivity.this);

        @Override
        protected void onProgressUpdate(Boolean... values) {
            if (values[0].equals(Boolean.TRUE)) {
                dialog.show();
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Boolean... params) {
            publishProgress(params[0]);
            DataManager.updateData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            DataManager.setData(mListView);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            super.onPostExecute(aVoid);
        }
    }
}
