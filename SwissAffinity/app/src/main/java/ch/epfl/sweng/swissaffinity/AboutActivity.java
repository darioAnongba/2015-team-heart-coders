package ch.epfl.sweng.swissaffinity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.GraphJSONObjectCallback;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static ch.epfl.sweng.swissaffinity.MainActivity.SERVER_URL;
import static ch.epfl.sweng.swissaffinity.MainActivity.SHARED_PREFS;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BIRTHDAY;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FACEBOOK_ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.GENDER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

public class AboutActivity extends AppCompatActivity {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        FacebookSdk.sdkInitialize(context);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_about);

        setLoggedText();

        LoginButton loginBtn = (LoginButton) findViewById(R.id.login_button);
        loginBtn.setReadPermissions("public_profile", "email", "user_birthday");
        loginBtn.registerCallback(
                callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                AccessToken.getCurrentAccessToken(),
                                new GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject json, GraphResponse rep) {
                                        SafeJSONObject jsonObject = null;
                                        try {
                                            jsonObject = new SafeJSONObject(rep.getRawResponse());
                                        } catch (JSONException e) {
                                            Log.e("AboutActivity", e.toString());
                                        }
                                        Log.v("AboutActivity", rep.toString());
                                        if (jsonObject != null) {
                                            fillUserData(jsonObject);
                                                new ConnectionToServer().execute();
                                        } else {
                                            //TODO: no data...
                                            onError(null);
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        String fields = getFields();
                        parameters.putString("fields", fields);
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(
                                AboutActivity.this,
                                "You canceled your login attempt",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException e) {
                        Toast.makeText(
                                AboutActivity.this,
                                "Login attempt failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
                if (currentAccessToken == null) {
                    deleteUserData();
                    setLoggedText();
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Get the field needed to do a request to facebook
     * @return a String with the needed field
     */
    private static String getFields() {
        return ID.get() + "," + NAME.get() + "," + FIRST_NAME.get() + "," + LAST_NAME.get() + "," +
                EMAIL.get() + "," + GENDER.get() + "," + BIRTHDAY.get();
    }

    /**
     * Take the value from the json and put them in sharedPreference , put default_string if not found
     * @param jsonObject the json in which you take the field
     */
    private void fillUserData(SafeJSONObject jsonObject) {
        String facebookID = jsonObject.get(ID.get(), SafeJSONObject.DEFAULT_STRING);
        String userName = jsonObject.get(NAME.get(), SafeJSONObject.DEFAULT_STRING);
        String lastName = jsonObject.get(LAST_NAME.get(), SafeJSONObject.DEFAULT_STRING);
        String firstName = jsonObject.get(FIRST_NAME.get(), SafeJSONObject.DEFAULT_STRING);
        String gender = jsonObject.get(GENDER.get(), SafeJSONObject.DEFAULT_STRING);
        String birthday = jsonObject.get(BIRTHDAY.get(), SafeJSONObject.DEFAULT_STRING);
        String email = jsonObject.get(EMAIL.get(), SafeJSONObject.DEFAULT_STRING);
        SHARED_PREFS.edit()
                .putString(FACEBOOK_ID.get(), facebookID)
                .putString(USERNAME.get(), userName)
                .putString(LAST_NAME.get(), lastName)
                .putString(FIRST_NAME.get(), firstName)
                .putString(GENDER.get(), gender)
                .putString(BIRTHDAY.get(), birthday)
                .putString(EMAIL.get(), email)
                .apply();
    }

    private void deleteUserData() {
        SHARED_PREFS.edit()
                .putString(FACEBOOK_ID.get(), null)
                .putString(USERNAME.get(), null)
                .putString(LAST_NAME.get(), null)
                .putString(FIRST_NAME.get(), null)
                .putString(GENDER.get(), null)
                .putString(BIRTHDAY.get(), null)
                .putString(EMAIL.get(), null)
                .apply();
    }

    /**
     * Set the loggedText in function of the username in sharedPreference . null = please login , else welcome "username"
     */
    private void setLoggedText() {
        TextView logged = ((TextView) findViewById(R.id.aboutLogedText));
        String userName = SHARED_PREFS.getString(USERNAME.get(), null);
        if (userName == null) {
            logged.setText(getString(R.string.welcome_not_logged_text));
            logged.setTextSize(20);
            logged.setTextColor(Color.RED);
        } else {
            String loggedText = getString(R.string.about_logged);
            logged.setText(String.format(loggedText, userName));
        }
    }


    private class ConnectionToServer extends AsyncTask<String, Void, Boolean> {
        private final ProgressDialog dialog = MainActivity.getLoadingDialog(AboutActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            boolean code = false;
            String facebookId = SHARED_PREFS.getString(FACEBOOK_ID.get(), SafeJSONObject.DEFAULT_STRING);
            try {
                code = DefaultNetworkProvider.checkConnection(
                        SERVER_URL + "/api/users/" + facebookId);
            } catch (IOException e) {
                Log.e("CheckCode", e.getMessage());
            }
            return code;
        }

        @Override
        protected void onPostExecute(Boolean code) {
            dialog.dismiss();
            if (code) {
                finish();
            } else {
                Intent registerIntent = new Intent(AboutActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        }
    }
}
