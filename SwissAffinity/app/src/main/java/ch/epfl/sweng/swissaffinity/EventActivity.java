package ch.epfl.sweng.swissaffinity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.gui.DataManager;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;
import static ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser.dateToString;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;
import static java.net.HttpURLConnection.HTTP_OK;

public class EventActivity extends AppCompatActivity {

    private int mEventId;
    private String mUserName;
    private int mRegistrationId;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mButton = (Button) findViewById(R.id.eventRegistration);
        mEventId = getIntent().getIntExtra(MainActivity.EXTRA_EVENT, -1);
        Event event = DataManager.getEvent(mEventId);
        if (event != null) {
            new DownloadImageTask().execute(event.getImagePath());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    public void register(View view) {
        mButton.setEnabled(false);
        if (mUserName.isEmpty()) {
            startActivity(new Intent(EventActivity.this, AboutActivity.class));
        } else {
            new RegisterEventTask().execute();
        }
    }

    private void updateUI() {
        mUserName = MainActivity.getPreferences().getString(USERNAME.get(), "");
        mRegistrationId = DataManager.getRegistrationId(mEventId);
        mButton.setEnabled(true);
        mButton.setText(mRegistrationId > 0 ? R.string.event_unregister : R.string.event_register);
        Event event = DataManager.getEvent(mEventId);
        if (event != null) {
            ((TextView) findViewById(R.id.eventName)).setText(event.getName());
            ((TextView) findViewById(R.id.eventDateBegin)).setText(dateToString(event.getDateBegin()));
            ((TextView) findViewById(R.id.eventDateEnd)).setText(dateToString(event.getDateEnd()));
            TextView location = (TextView) findViewById(R.id.eventLocation);
            location.setText(event.getLocation().getName());
            ((TextView) findViewById(R.id.eventDescription)).setText(event.getDescription());
            String price = String.format(getString(R.string.event_price), event.getBasePrice());
            ((TextView) findViewById(R.id.eventPrice)).setText(price);
            String maxPeople =
                    String.format(getString(R.string.event_max_people), event.getMaxPeople());
            ((TextView) findViewById(R.id.eventMaxPeople)).setText(maxPeople);
            if (event instanceof SpeedDatingEvent) {
                SpeedDatingEvent speedDatingEvent = (SpeedDatingEvent) event;
                location.setText(speedDatingEvent.getEstablishment().toString());
                int men = speedDatingEvent.getMenRegistered();
                int maxMen = speedDatingEvent.getMenSeats();
                int women = speedDatingEvent.getWomenRegistered();
                int maxWomen = speedDatingEvent.getWomenSeats();
                String menWomen = String.format(
                        getString(R.string.event_registered_people), women, maxWomen, men, maxMen);
                ((TextView) findViewById(R.id.eventRegisteredPeople)).setText(menWomen);
            }
        }
    }

    private final class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap image = null;
            String imagePath = params[0];
            try {
                image = DataManager.getEventClient().imageFor(imagePath);
            } catch (EventClientException e) {
                // no image.
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                ImageView imageView = (ImageView) findViewById(R.id.eventPicture);
                imageView.setContentDescription("");
                imageView.setImageBitmap(bitmap);
            }
            super.onPostExecute(bitmap);
        }
    }

    private final class RegisterEventTask extends AsyncTask<String, Void, Integer> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = MainActivity.getLoadingDialog(EventActivity.this);
            dialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            int response = -1;
            try {
                if (mRegistrationId > 0) {
                    response = DataManager.getUserClient().unregisterUser(mRegistrationId);
                } else {
                    response = DataManager.getUserClient().registerUser(mUserName, mEventId);
                }
            } catch (UserClientException e) {
                Log.e("RegisterEventTask", e.getMessage());
            }
            DataManager.updateData(EventActivity.this);
            return response;
        }

        @Override
        protected void onPostExecute(Integer response) {
            String message;
            switch (response) {
                case HTTP_NO_CONTENT:
                case HTTP_OK:
                    message = getString(R.string.event_registration_success);
                    break;
                default:
                    message = getString(R.string.event_registration_problem);
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(EventActivity.this, message, Toast.LENGTH_SHORT).show();
            updateUI();
            super.onPostExecute(response);
        }
    }
}
