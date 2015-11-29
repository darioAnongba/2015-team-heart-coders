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
import static ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject.DEFAULT_STRING;
import static java.net.HttpURLConnection.HTTP_NO_CONTENT;

public class EventActivity extends AppCompatActivity {
    private static final String REGISTER_OP = "register";
    private static final String UNREGISTER_OP = "unregister";

    private int mEventId;
    private String mUserName;
    private int mRegistrationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
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
        if (mUserName.isEmpty()) {
            startActivity(new Intent(EventActivity.this, AboutActivity.class));
        } else {
            String operation = REGISTER_OP;
            if (mRegistrationId > 0) {
                operation = UNREGISTER_OP;
            }
            new RegisterEventTask().execute(mUserName, "" + mEventId, operation);
        }
    }

    private void updateUI() {
        mUserName = MainActivity.getSharedPrefs().getString(USERNAME.get(), "");
        mRegistrationId = DataManager.getRegistrationId(mEventId);
        Button button = (Button) findViewById(R.id.eventRegistration);
        if (mRegistrationId > 0) {
            button.setText(R.string.event_unregister);
        } else {
            button.setText(R.string.event_register);
        }
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
        private final ProgressDialog dialog = MainActivity.getLoadingDialog(EventActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            int response = -1;
            String userName = params[0];
            String eventId = params[1];
            String operation = params[2];
            try {
                if (!mUserName.equals(DEFAULT_STRING) && operation.equals(REGISTER_OP)) {
                    int id = Integer.parseInt(eventId);
                    response = DataManager.getUserClient().registerUser(userName, id);
                } else if (operation.equals(UNREGISTER_OP)) {
                    response = DataManager.getUserClient().unregisterUser(mRegistrationId);
                }
            } catch (UserClientException e) {
                Log.e("RegisterEventTask", e.getMessage());
            }
            DataManager.updateData();
            return response;
        }

        @Override
        protected void onPostExecute(Integer response) {
            String message;
            switch (response) {
                case HTTP_NO_CONTENT:
                    message = getString(R.string.event_registration_success);
                    updateUI();
                    break;
                default:
                    message = getString(R.string.event_registration_problem);
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(EventActivity.this, message, Toast.LENGTH_SHORT).show();
            super.onPostExecute(response);
        }
    }
}
