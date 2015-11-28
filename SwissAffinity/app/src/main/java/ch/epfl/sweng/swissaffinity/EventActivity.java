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

public class EventActivity extends AppCompatActivity {

    private Event mEvent;
    private String mUserName;
    private int mRegistrationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mEvent = DataManager.getEvent(getIntent().getIntExtra(MainActivity.EXTRA_EVENT, -1));
        new DownloadImageTask().execute(mEvent.getImagePath());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserName = MainActivity.getSharedPrefs().getString(USERNAME.get(), "");
        mEvent = DataManager.getEvent(getIntent().getIntExtra(MainActivity.EXTRA_EVENT, -1));
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

    public void register(View view) {
        if (mUserName.isEmpty()) {
            startActivity(new Intent(EventActivity.this, AboutActivity.class));
        } else {
            String operation = "r";
            if (mRegistrationId > 0) {
                operation = "u";
            }
            new RegisterEventTask().execute(mUserName, "" + mEvent.getId(), operation);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap image = null;
            try {
                image = DataManager.getEventClient().imageFor(params[0]);
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

    private class RegisterEventTask extends AsyncTask<String, Void, String> {
        private final ProgressDialog dialog = MainActivity.getLoadingDialog(EventActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String response = null;
            try {
                if (params[2].equals("r") && !mUserName.equals("")) {
                    response = DataManager.getUserClient().registerUser(
                            params[0],
                            Integer.parseInt(params[1]));
                } else if (params[2].equals("u")) {
                    int ret = DataManager.getUserClient().unregisterUser(mRegistrationId);
                    if (ret == 204) {
                        response = "";
                    }
                }
            } catch (UserClientException e) {
                Log.e("RegisterEventTask", e.getMessage());
            }
            DataManager.updateData();
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response == null) {
                Toast.makeText(EventActivity.this, "Problem with registration", Toast.LENGTH_SHORT)
                     .show();
            } else if (response.equals("")) {
                onResume();
            }
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
