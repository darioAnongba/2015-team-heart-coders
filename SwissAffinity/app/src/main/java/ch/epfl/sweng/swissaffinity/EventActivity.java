package ch.epfl.sweng.swissaffinity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ch.epfl.sweng.swissaffinity.events.Event;

import static ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser.*;

public class EventActivity extends AppCompatActivity {

    private Event mEvent;
    private Bitmap mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent intent = getIntent();

        mEvent = (Event) intent.getSerializableExtra(MainActivity.EXTRA_EVENT);
        mImage = intent.getParcelableExtra(MainActivity.EXTRA_IMAGE);
        //TODO: reflect all event details.
        fillEventData();
    }

    private void fillEventData() {
        ((TextView) findViewById(R.id.eventNameTextView)).setText(mEvent.getName());
        ((TextView) findViewById(R.id.eventDateBegin)).setText(dateToString(mEvent.getDateBegin()));
        ((TextView) findViewById(R.id.eventDateEnd)).setText(dateToString(mEvent.getDateEnd()));
        ((TextView) findViewById(R.id.eventLocation)).setText(mEvent.getLocation().getName());
        ((TextView) findViewById(R.id.eventDescription)).setText(mEvent.getDesription());
        String price = String.format(getString(R.string.envent_price), mEvent.getBasePrice());
        ((TextView) findViewById(R.id.eventPrice)).setText(price);
        if (mImage != null) {
            ((ImageView) findViewById(R.id.eventPicture)).setImageBitmap(mImage);
        }
    }

    public void register(View view) {
        if (!MainActivity.REGISTERED) {
            startActivity(new Intent(EventActivity.this, AboutActivity.class));
        } else {
            //TODO: add the registration logic here.
        }
    }

}
