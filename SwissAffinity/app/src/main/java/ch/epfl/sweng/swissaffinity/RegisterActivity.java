package ch.epfl.sweng.swissaffinity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import ch.epfl.sweng.swissaffinity.users.User.Gender;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.users.NetworkUserClient;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;

import static ch.epfl.sweng.swissaffinity.MainActivity.SHARED_PREFS;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BIRTHDAY;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FACEBOOK_ID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.GENDER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText userNameText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText birthdayText;
    private String facebookId;
    private String gender = null;
    private String lastName;
    private String birthday;
    private SharedPreferences sharedPreferences;
    private final String SERVER_URL = "http://beecreative.ch/api/users";
    private DefaultNetworkProvider networkProvider;
    private EditText passwordText;
    private EditText passwordConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = MainActivity.SHARED_PREFS;
        networkProvider = new DefaultNetworkProvider();
        final RadioGroup.OnCheckedChangeListener radioChecker = new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.registerFemale) {
                    gender = "female";
                } else if (checkedId == R.id.registerMale) {
                    gender = "male";
                }
            }
        };

        fillData();

        Button registerButton = (Button) findViewById(R.id.userRegistration);
        registerButton.setClickable(false);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               JSONObject json =createJson();
                if (json!=null) {
                    new UploadUserTask().execute(json.toString());
                }
            }
        });
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JSONObject json = createJson();
                        if (json != null) {
                            Log.v("UserJson", json.toString());
                        }
                        Toast.makeText(
                                getApplicationContext(),
                                "Not implemented yet ;)",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void fillData() {

        String userName = SHARED_PREFS.getString(USERNAME.get(), "");
        String firstName = SHARED_PREFS.getString(FIRST_NAME.get(), "");
        String lastName = SHARED_PREFS.getString(LAST_NAME.get(), "");
        String email = SHARED_PREFS.getString(EMAIL.get(), "");
        String birthday = SHARED_PREFS.getString(BIRTHDAY.get(), "");
        gender = SHARED_PREFS.getString(GENDER.get(), "");
        facebookId = SHARED_PREFS.getString(FACEBOOK_ID.get(), "");

        userNameText = (EditText) findViewById(R.id.registerUserName);
        userNameText.setText(userName);
        firstNameText = (EditText) findViewById(R.id.registerFirstName);
        firstNameText.setText(firstName);
        lastNameText = (EditText) findViewById(R.id.registerLastName);
        lastNameText.setText(lastName);
        emailText = (EditText) findViewById(R.id.registerEmail);
        emailText.setText(email);
        birthdayText = (EditText) findViewById(R.id.registerBirthDay);
        birthdayText.setText(birthday);
        if (gender.equalsIgnoreCase(Gender.FEMALE.get())) {
            ((RadioButton) findViewById(R.id.registerFemale)).setChecked(true);
        } else if (gender.equalsIgnoreCase(Gender.MALE.get())) {
            ((RadioButton) findViewById(R.id.registerMale)).setChecked(true);
        }
        passwordText = (EditText) findViewById(R.id.registerPassword);
        passwordConfirmation = (EditText) findViewById(R.id.registerPasswordConfirmation);
    }

    private JSONObject createJson() {
        JSONObject jsonObject = null;
        JSONObject jsonRequest = null;


        if (emailText.getText().toString().isEmpty() ||
            emailText.getText().toString().length() > 100 ||
            !isValidEmail(emailText.getText().toString()))
        {
            Toast.makeText(
                    RegisterActivity.this,
                    "Mail is not in a valid format , empty or over 100 characters",
                    Toast.LENGTH_SHORT).show();
        } else if ((userNameText.getText().toString().isEmpty() ||
                    userNameText.getText().toString().length() > 50))
        {
            Toast.makeText(
                    RegisterActivity.this, "Username is empty , or over 50 characters",
                    Toast.LENGTH_SHORT).show();
        } else if ((firstNameText.getText().toString().isEmpty() ||
                    firstNameText.getText().toString().length() > 50))
        {
            Toast.makeText(
                    RegisterActivity.this, "First Name is empty , or over 50 characters",
                    Toast.LENGTH_SHORT).show();
        } else if ((lastNameText.getText().toString().isEmpty() ||
                    lastNameText.getText().toString().length() > 50))
        {
            Toast.makeText(
                    RegisterActivity.this, "Last Name is empty , or over 50 characters",
                    Toast.LENGTH_SHORT).show();
        } else if (passwordText.getText().toString().isEmpty()) {
            Toast.makeText(
                    RegisterActivity.this, "Password is empty ",
                    Toast.LENGTH_SHORT).show();
        } else if (!passwordText.getText().toString().equals(
                passwordConfirmation.getText()
                                    .toString()))
        {
            Toast.makeText(
                    RegisterActivity.this, "Password do not match ",
                    Toast.LENGTH_SHORT).show();
        } else if (gender == null) {
            Toast.makeText(
                    RegisterActivity.this, "No value found for Gender ",
                    Toast.LENGTH_SHORT).show();
        } else if (birthdayText.getText().toString().length() == 0 ||
                   birthdayText.getText().toString().length() > 20)
        {
            Toast.makeText(
                    RegisterActivity.this, "Birth Date is empty or too long ",
                    Toast.LENGTH_SHORT).show();
        } else {
            try {
                jsonObject = new JSONObject();
                jsonRequest = new JSONObject();
                jsonObject.put("email", emailText.getText().toString());
                jsonObject.put("username", userNameText.getText().toString());
                jsonObject.put("firstName", firstNameText.getText().toString());
                jsonObject.put("lastName", lastNameText.getText().toString());
                jsonObject.put("gender", gender);
                jsonObject.put("birthDate", birthdayText.getText().toString());
                jsonObject.put("facebookId", facebookId);
                jsonObject.put("plainPassword", passwordText.getText().toString());
                jsonRequest.put("rest_user_registration",jsonObject);
            } catch (JSONException e) {
            }
            TextView editText =(TextView) findViewById(R.id.registerInstruction);
            editText.setText(jsonObject.toString());
        }
        return jsonRequest;
    }



    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    private class UploadUserTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {

            NetworkUserClient networkUserClient = new NetworkUserClient(SERVER_URL, new DefaultNetworkProvider());
            try {
                JSONObject jsonObject = new JSONObject(params[0]);
                networkUserClient.postUser(SERVER_URL, jsonObject);
            } catch (UserClientException | JSONException e) {

            }
            return null;
        }

    };

}
