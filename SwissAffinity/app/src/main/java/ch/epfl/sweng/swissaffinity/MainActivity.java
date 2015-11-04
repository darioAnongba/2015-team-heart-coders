package ch.epfl.sweng.swissaffinity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static boolean USER_REGISTERED = false;

    private ExpandableListAdapter<String, String> mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    private void createData() {
        String myEvents = getResources().getString(R.string.my_events);
        String upcomingEvents = getResources().getString(R.string.upcoming_events);
        List<String> HEADERS = Arrays.asList("My Events :", "Upcoming Events :");
        mListAdapter = new ExpandableListAdapter<String, String>(this);
        mListAdapter.addChild(myEvents, "No event yet...");
        mListAdapter.addChild(upcomingEvents, "Test 00");
        mListAdapter.addChild(upcomingEvents, "Test 01");
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

}
