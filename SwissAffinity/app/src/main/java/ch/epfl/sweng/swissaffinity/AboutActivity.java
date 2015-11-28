package ch.epfl.sweng.swissaffinity;

import android.app.ProgressDialog;
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

import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.gui.DataManager;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;
import ch.epfl.sweng.swissaffinity.utilities.parsers.user.FacebookUserParser;

import static android.widget.Toast.LENGTH_SHORT;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BIRTHDAY;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.GENDER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;
import static com.facebook.AccessToken.getCurrentAccessToken;

/**
 * The about activity of the application.
 */
public class AboutActivity extends AppCompatActivity {
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_about);

        updateUI();

        setLoginButton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Get the field needed to do a request to facebook
     *
     * @return a String with the needed field
     */
    private static String getFields() {
        return ID.get() + "," + NAME.get() + "," + FIRST_NAME.get() + "," + LAST_NAME.get() + "," +
               EMAIL.get() + "," + GENDER.get() + "," + BIRTHDAY.get();
    }

    /**
     * Set the loggedText in function of the username in sharedPreference . null = please login , else welcome "username"
     */
    private void updateUI() {
        TextView logged = ((TextView) findViewById(R.id.aboutLogedText));
        String userName = MainActivity.getSharedPrefs().getString(USERNAME.get(), null);
        if (userName == null) {
            logged.setText(getString(R.string.welcome_not_logged_text));
            logged.setTextSize(20);
            logged.setTextColor(Color.RED);
        } else {
            String loggedText = getString(R.string.about_logged);
            logged.setText(String.format(loggedText, userName));
        }
    }

    private void displayToast(String message) {
        Toast.makeText(AboutActivity.this, message, LENGTH_SHORT).show();
    }

    private void setLoginButton() {
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile", "email", "user_birthday");
        loginButton.registerCallback(callbackManager, new SwissAffinityCallback());
        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
                if (currentAccessToken == null) {
                    DataManager.deleteUser();
                    updateUI();
                }
            }
        };
    }

    private class SwissAffinityCallback implements FacebookCallback<LoginResult> {
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(
                    getCurrentAccessToken(), new GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject json, GraphResponse rep) {
                            SafeJSONObject userJson = null;
                            try {
                                userJson = new SafeJSONObject(rep.getRawResponse());
                            } catch (JSONException e) {
                                Log.e("AboutActivity", e.toString());
                            }
                            Log.v("AboutActivity", rep.toString());
                            if (userJson != null) {
                                User user = null;
                                try {
                                    user = new FacebookUserParser().parse(userJson);
                                } catch (ParserException e) {
                                    Log.e("UserParser", e.getMessage());
                                }
                                new ConnectionToServer().execute(user);
                            } else {
                                onError(new FacebookException("No data from server"));
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
            displayToast(getString(R.string.facebook_cancel));
        }

        @Override
        public void onError(FacebookException e) {
            displayToast(getString(R.string.facebook_error) + e.getMessage());
        }
    }

    private class ConnectionToServer extends AsyncTask<User, Void, User> {
        private final ProgressDialog dialog = MainActivity.getLoadingDialog(AboutActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected User doInBackground(User... params) {
            User user = params[0];
            try {
                user = DataManager.getUserClient().fetchByFacebookID(user.getFacebookId());
            } catch (UserClientException e) {
                Log.d("UserFetch : ", e.getMessage());
            }
            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {
                DataManager.saveUser(user);
            } else {
                Intent intent = new Intent(AboutActivity.this, RegisterActivity.class);
                intent.putExtra(MainActivity.EXTRA_USER, user);
                startActivity(intent);
            }
            dialog.dismiss();
            finish();
        }
    }
}
