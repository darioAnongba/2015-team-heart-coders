package ch.epfl.sweng.swissaffinity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        listView.expandGroup(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
        mListAdapter = new ExpandableListAdapter<String, String>(this);

        for (int i = 1; i < 3; i++) {
            for (String header : HEADERS) {
                mListAdapter.addChild(header, "Test" + i);
            }
        }
    }
}
