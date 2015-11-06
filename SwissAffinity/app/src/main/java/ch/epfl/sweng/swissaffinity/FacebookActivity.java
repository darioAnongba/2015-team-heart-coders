package ch.epfl.sweng.swissaffinity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

import java.util.Arrays;

public class FacebookActivity extends AppCompatActivity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_facebook);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_location"));
        info = (TextView)findViewById(R.id.info);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    MainActivity.email = (String) object.get("email");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    MainActivity.userName = (String) object.get("name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.v("LoginActivity", response.toString());
                            }
                        });
                request.executeAsync();

                info.setText("\n\n\n" +
                                "User ID :" + loginResult.getAccessToken().getUserId()
                                + "\n" + "Permissions" + loginResult.getAccessToken().getPermissions().toString()
                                + "\n" + "Auth Token :" + loginResult.getAccessToken().getToken()
                );

                loginButton.setVisibility(View.INVISIBLE);
                MainActivity.USER_REGISTERED=true;
                Intent myIntent = new Intent( FacebookActivity.this,EventActivity.class);
                FacebookActivity.this.startActivity(myIntent);
            }

            @Override
            public void onCancel() {
                info.setText("You canceled your login attempt");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
