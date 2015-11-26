package ch.epfl.sweng.swissaffinity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.Toast;

import ch.epfl.sweng.swissaffinity.gui.EventExpandableListAdapter;
import ch.epfl.sweng.swissaffinity.utilities.DataManager;

/**
 * The main activity of the application.
 */
public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_EVENT = "ch.epfl.sweng.swissaffinity.event";
    public static final String EXTRA_USER = "ch.epfl.sweng.swissaffinity.user";

    private static SharedPreferences mSharedPrefs;

    private static final String SHARED_PREFS_ID = "ch.epfl.sweng.swissaffinity.shared_prefs";

    private DataManager mDataManager;
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
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

        mSharedPrefs = getSharedPreferences(SHARED_PREFS_ID, MODE_PRIVATE);

        mListView = (ExpandableListView) findViewById(R.id.mainEventListView);
        EventExpandableListAdapter adapter = new EventExpandableListAdapter(this);
        mListView.setAdapter(adapter);
        mDataManager = new DataManager(this, mListView);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mDataManager.saveInstance(outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mDataManager.saveInstance(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(
            Bundle savedInstanceState, PersistableBundle persistentState)
    {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        if (persistentState != null) {
            mDataManager.restoreInstance(persistentState);
        }
        mDataManager.updateData();
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
}
