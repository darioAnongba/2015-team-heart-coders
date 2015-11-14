package ch.epfl.sweng.swissaffinity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static ch.epfl.sweng.swissaffinity.MainActivity.SHARED_PREF;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BIRTHDAY;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FACEBOOKID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.GENDER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;
import static com.facebook.AccessToken.getCurrentAccessToken;
import static com.facebook.GraphRequest.newMeRequest;

public class AboutActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        FacebookSdk.sdkInitialize(context);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_about);
        sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        setLogedText();
        LoginButton loginBtn = (LoginButton) findViewById(R.id.login_button);
        loginBtn.setReadPermissions("public_profile", "email", "user_birthday");
        loginBtn.registerCallback(
                callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = newMeRequest(
                                getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object, GraphResponse response) {
                                        SafeJSONObject json = null;
                                        try {
                                            json = new SafeJSONObject(response.getRawResponse());
                                        } catch (JSONException e) {
                                            Log.e("AboutActivity", e.toString());
                                        }
                                        if (json != null) {
                                            fillUserData(json);
                                            Log.v("LoginActivity", response.toString());
                                            Intent registerIntent = new Intent(AboutActivity.this,RegisterActivity.class);
                                            startActivity(registerIntent);
                                            finish();
                                        } else {
                                            //TODO: if no data...
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,first_name,last_name,email,gender,birthday");
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
                                AboutActivity.this, "Login attempt failed", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    deleteUserData();
                    setLogedText();
                }
            }
        };
    }

    private void fillUserData(SafeJSONObject jsonObject) {
        String userId = jsonObject.getString(ID.get(), "");
        String name = jsonObject.getString(NAME.get(), "");
        String lastName = jsonObject.getString(LAST_NAME.get(), "");
        String firstName = jsonObject.getString(FIRST_NAME.get(), "");
        String gender = jsonObject.getString(GENDER.get(), "");
        String birthday = jsonObject.getString(BIRTHDAY.get(), "");
        String email = jsonObject.getString(EMAIL.get(), "");
        sharedPreferences.edit()
                .putString(USERNAME.get(), name)
                .putString(FACEBOOKID.get(), userId)
                .putString(EMAIL.get(), email)
                .putString(GENDER.get(), gender)
                .putString(BIRTHDAY.get(), birthday)
                .putString(FIRST_NAME.get(), firstName)
                .putString(LAST_NAME.get(), lastName)
                .apply();
    }

    private void deleteUserData() {
        sharedPreferences.edit()
                .putString(USERNAME.get(), null)
                .putString(FACEBOOKID.get(), null)
                .putString(EMAIL.get(), null)
                .putString(GENDER.get(), null)
                .putString(BIRTHDAY.get(), null)
                .putString(FIRST_NAME.get(), null)
                .putString(LAST_NAME.get(), null)
                .apply();
    }

    private void setLogedText() {
        final TextView logged = ((TextView) findViewById(R.id.aboutLogedText));
        String userName = sharedPreferences.getString(USERNAME.get(), null);
        if (userName == null) {
            logged.setText("To start, you have to login:");
            logged.setTextSize(20);
        } else {
            String loggedText = getString(R.string.about_logged);
            logged.setText(String.format(loggedText, userName));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
