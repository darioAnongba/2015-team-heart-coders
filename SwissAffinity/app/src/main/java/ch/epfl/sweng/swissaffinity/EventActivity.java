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

    private Event mEvent;
    private String mUserName;
    private int mRegistrationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        mEvent = DataManager.getEvent(getIntent().getIntExtra(MainActivity.EXTRA_EVENT, -1));
        mUserName = MainActivity.getSharedPrefs().getString(USERNAME.get(), "");
        new DownloadImageTask().execute(mEvent.getImagePath());
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
            new RegisterEventTask().execute(mUserName, "" + mEvent.getId(), operation);
        }
    }

    private void updateUI() {
        mRegistrationId = DataManager.getRegistrationId(mEvent.getId());
        Button button = (Button) findViewById(R.id.eventRegistration);
        if (mRegistrationId > 0) {
            button.setText("Unregister");
        } else {
            button.setText("Register");
        }
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
                ((ImageView) findViewById(R.id.eventPicture)).setImageBitmap(bitmap);
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
            switch (response) {
                case HTTP_NO_CONTENT:
                    updateUI();
                    break;
                default:
                    String message = getString(R.string.event_registration_problem);
                    Toast.makeText(EventActivity.this, message, Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            super.onPostExecute(response);
        }
    }
}
