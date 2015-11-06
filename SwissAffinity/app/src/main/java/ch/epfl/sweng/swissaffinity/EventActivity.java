package ch.epfl.sweng.swissaffinity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import ch.epfl.sweng.swissaffinity.events.Event;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //TODO: reflect all event details.
        Event event = (Event) getIntent().getSerializableExtra(MainActivity.EXTRA_EVENT);
        ((TextView) findViewById(R.id.eventNameTextView)).setText(event.getName());
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("dd/MM/yyyy - HH:mm -> ");
        String date = df.format(event.getDateBegin().getTime());
        ((TextView) findViewById(R.id.eventDateBegin)).setText(date);
        df.applyPattern("HH:mm");
        date = df.format(event.getDateEnd().getTime());
        ((TextView) findViewById(R.id.eventDateEnd)).setText(date);
        ((TextView) findViewById(R.id.eventLocation)).setText(event.getLocation().getName());
        ((TextView) findViewById(R.id.eventDescription)).setText(event.getDesription());
    }

}
