package ch.epfl.sweng.swissaffinity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static List<String> HEADERS = Arrays.asList("My Events :", "Upcoming Events :");

    private ExpandableListAdapter<String, String> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListAdapter = new ExpandableListAdapter<String, String>(this);
        createData();

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
        for (String header : HEADERS) {
            for (int i = 0; i < 2; i++) {
                mListAdapter.addChild(header, "Test" + i);
            }
        }
    }
}
