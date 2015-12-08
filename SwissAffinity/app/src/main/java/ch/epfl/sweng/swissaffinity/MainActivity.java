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

import ch.epfl.sweng.swissaffinity.gui.DataManager;
import ch.epfl.sweng.swissaffinity.gui.EventExpandableListAdapter;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

/**
 * The main activity of the application.
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT = "ch.epfl.sweng.swissaffinity.event";
    public static final String EXTRA_USER = "ch.epfl.sweng.swissaffinity.user";

    private static final String SHARED_PREFS_ID = "ch.epfl.sweng.swissaffinity.shared_prefs";

    private static SharedPreferences SHARED_PREFERENCES;

    private ExpandableListView mListView;
    private ProgressDialog mDialog;

    public static SharedPreferences getPreferences() {
        if (SHARED_PREFERENCES == null) {
            throw new UnsupportedOperationException();
        }
        return SHARED_PREFERENCES;
    }

    public static ProgressDialog getLoadingDialog(Context context) {
        if (context == null ){
            throw new IllegalArgumentException();
        }
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
        SHARED_PREFERENCES = getSharedPreferences(SHARED_PREFS_ID, MODE_PRIVATE);
        mListView = (ExpandableListView) findViewById(R.id.mainEventListView);
        mListView.setAdapter(new EventExpandableListAdapter(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
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
                startActivity(new Intent(this, PreferenceActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        mDialog = null;
        super.onPause();
    }

    private void updateUI() {
        String welcome = getString(R.string.welcome_registered_text);
        String userName = SHARED_PREFERENCES.getString(USERNAME.get(), "");
        TextView textView = (TextView) findViewById(R.id.mainWelcomeText);
        textView.setText(String.format(welcome, userName));

        new DataManagerTask(!DataManager.hasData()).execute();
    }

    private final class DataManagerTask extends AsyncTask<Void, Void, Void> {
        private final boolean mWithDialog;

        DataManagerTask(boolean withDialog) {
            mWithDialog = withDialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mWithDialog) {
                mDialog = getLoadingDialog(MainActivity.this);
                mDialog.show();
            }
            DataManager.displayData(mListView);
        }

        @Override
        protected Void doInBackground(Void... params) {
            DataManager.updateData(MainActivity.this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            DataManager.displayData(mListView);
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
            super.onPostExecute(aVoid);
        }
    }
}
