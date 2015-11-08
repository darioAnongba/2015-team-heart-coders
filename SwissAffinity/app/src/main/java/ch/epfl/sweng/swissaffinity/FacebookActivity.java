package ch.epfl.sweng.swissaffinity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import ch.epfl.sweng.swissaffinity.db.UserDBAdapter;


public class FacebookActivity extends AppCompatActivity {

    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private UserDBAdapter mDbHelper;

    private String mId = "";
    private String mUserName= "";
    private String mEmail= "";
    private String mEnabled= "";
    private String mLocked= "";
    private String mLastName= "";
    private String mFirstName= "";
    private String mPhone= "";
    private String mGender= "";
    private String mBirthdate= "";
    private String mProfession= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new UserDBAdapter(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_facebook);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile", "user_location"));
        info = (TextView)findViewById(R.id.info);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mDbHelper.open();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    MainActivity.email = (String) object.get("email");
                                    mEmail = MainActivity.email;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    MainActivity.userName = (String) object.get("name");
                                    mUserName = MainActivity.userName;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    mId = (String) object.get("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    mLastName = (String) object.get("last_name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    mFirstName= (String) object.get("first_name");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    mGender = (String) object.get("gender");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    mBirthdate = (String) object.get("birthday");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.v("LoginActivity", response.toString());
                            }
                        });
                request.executeAsync();

                mDbHelper.createData(mId, mUserName,mEmail,true, false,mFirstName, mLastName,"",mGender, mBirthdate, "");
                mDbHelper.close();

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
