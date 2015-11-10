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

import ch.epfl.sweng.swissaffinity.db.userDBAdapter;

import static ch.epfl.sweng.swissaffinity.MainActivity.SHARED_PREF;
import static ch.epfl.sweng.swissaffinity.MainActivity.USERID;
import static ch.epfl.sweng.swissaffinity.MainActivity.USERNAME;
import static com.facebook.AccessToken.getCurrentAccessToken;
import static com.facebook.GraphRequest.newMeRequest;

public class AboutActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private LoginButton loginBtn;
    private CallbackManager callbackManager;
    private userDBAdapter mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        FacebookSdk.sdkInitialize(context);
        mDbHelper = new userDBAdapter(this);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_about);
        sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        setLogedText();
        loginBtn = (LoginButton) findViewById(R.id.login_button);
        loginBtn.setReadPermissions("public_profile", "email", "user_birthday");
        loginBtn.registerCallback(
                callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        mDbHelper.open();
                        GraphRequest request = newMeRequest(
                                getCurrentAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object, GraphResponse response)
                                    {
                                        JSONObject json = response.getJSONObject();
                                        if (json != null) {
                                            try {
                                                String userId = json.getString("id");
                                                String name = json.getString("name");
                                                String lastName = json.getString("last_name");
                                                String firstName = json.getString("first_name");
                                                String gender = json.getString("gender");
                                                String birthday = json.getString("birthday");
                                                String email = json.getString("email");
                                                sharedPreferences.edit()
                                                                 .putString(USERNAME, firstName)
                                                                 .putString(USERID, userId)
                                                                 .apply();
                                                mDbHelper.createData(
                                                        userId,
                                                        name,
                                                        email,
                                                        true,
                                                        false,
                                                        firstName,
                                                        lastName,
                                                        "",
                                                        gender,
                                                        birthday,
                                                        "");
                                                mDbHelper.close();
                                                Log.v("LoginActivity", response.toString());
                                                finish();
                                            } catch (JSONException e) {
                                                Log.e("AboutActivity", e.getMessage());
                                            }
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString(
                                "fields", "id,name,first_name,last_name,email,gender,birthday");
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
                    AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
                if (currentAccessToken == null) {
                    sharedPreferences.edit()
                                     .putString(USERNAME, null)
                                     .putString(USERID, null)
                                     .apply();
                    setLogedText();
                }
            }
        };
    }

    private void setLogedText() {
        final TextView logged = ((TextView) findViewById(R.id.aboutLogedText));
        String userName = sharedPreferences.getString(MainActivity.USERNAME, null);
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
