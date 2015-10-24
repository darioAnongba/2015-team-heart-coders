package ch.epfl.sweng.swissaffinity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private final static List<String> HEADERS =
            Arrays.asList(new String[]{"My Events :", "Upcoming Events :"});

    private ExpandableListAdapter<String, String> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createData();

        ExpandableListView listView = (ExpandableListView) findViewById(R.id.mainEventListView);
        listView.setAdapter(mListAdapter);
        listView.expandGroup(0);

//        Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
//        myIntent.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
//        MainActivity.this.startActivity(myIntent);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        this.setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void createData() {
        mListAdapter = new ExpandableListAdapter<String, String>(this);

        for (int i = 1; i < 3; i++) {
            for (String header : HEADERS) {
                mListAdapter.addChild(header, "Test" + i);
            }
        }
    }
}
