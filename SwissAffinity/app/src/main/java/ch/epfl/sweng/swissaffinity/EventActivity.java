package ch.epfl.sweng.swissaffinity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.events.NetworkEventClient;

import static ch.epfl.sweng.swissaffinity.MainActivity.*;
import static ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser.dateToString;

public class EventActivity extends AppCompatActivity {

    private Event mEvent;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        new DownloadImageTask().execute();
        Intent intent = getIntent();
        mEvent = (Event) intent.getSerializableExtra(EXTRA_EVENT);
        mUser = (User) intent.getSerializableExtra(EXTRA_USER);
        fillEventData();
    }

    private void fillEventData() {
        ((TextView) findViewById(R.id.eventName)).setText(mEvent.getName());
        ((TextView) findViewById(R.id.eventDateBegin)).setText(dateToString(mEvent.getDateBegin()));
        ((TextView) findViewById(R.id.eventDateEnd)).setText(dateToString(mEvent.getDateEnd()));
        ((TextView) findViewById(R.id.eventLocation)).setText(mEvent.getLocation().getName());
        ((TextView) findViewById(R.id.eventDescription)).setText(mEvent.getDescription());
        String price = String.format(getString(R.string.event_price), mEvent.getBasePrice());
        ((TextView) findViewById(R.id.eventPrice)).setText(price);
        String maxPeople =
                String.format(getString(R.string.event_max_people), mEvent.getMaxPeople());
        ((TextView) findViewById(R.id.eventMaxPeople)).setText(maxPeople);
        if (mEvent instanceof SpeedDatingEvent) {
            SpeedDatingEvent event = (SpeedDatingEvent) mEvent;
            int men = event.getMenRegistered();
            int maxMen = event.getMenSeats();
            int women = event.getWomenRegistered();
            int maxWomen = event.getWomenSeats();
            String menWomen = String.format(
                    getString(R.string.event_registered_people), women, maxWomen, men, maxMen);
            ((TextView) findViewById(R.id.eventRegisteredPeople)).setText(menWomen);
        }
        //TODO: reflect all event details.
    }

    public void register(View view) {
        if (mUser == null) {
            startActivity(new Intent(EventActivity.this, AboutActivity.class));
        } else {
            Toast.makeText(EventActivity.this, "Not implemented yet :(", Toast.LENGTH_SHORT).show();
            //TODO: add the registration logic here.
        }
    }

    private class DownloadImageTask extends AsyncTask<Void, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap image = null;
            try {
                image = EVENT_CLIENT.imageFor(mEvent);
            } catch (EventClientException e) {
                // no image.
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                ((ImageView) findViewById(R.id.eventPicture)).setImageBitmap(bitmap);
            }
            super.onPostExecute(bitmap);
        }
    }
}
