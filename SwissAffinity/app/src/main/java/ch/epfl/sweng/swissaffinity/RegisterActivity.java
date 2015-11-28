package ch.epfl.sweng.swissaffinity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ch.epfl.sweng.swissaffinity.gui.DataManager;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.users.User.Gender;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;
import ch.epfl.sweng.swissaffinity.utilities.parsers.user.UserParser;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject.DEFAULT_STRING;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailText;
    private EditText userNameText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText birthdayText;
    private EditText passwordText;
    private EditText passwordConfirmation;
    private String facebookId;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final RadioGroup.OnCheckedChangeListener radioChecker =
                new RadioGroup.OnCheckedChangeListener() {

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
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        JSONObject json = createJson();
                        if (json != null) {
                            Log.v("UserJson", json.toString());
                            new UploadUserTask().execute(json.toString());
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "There has been a problem",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    /**
     * Fill the EditText with the info sended by facebook
     */
    private void fillData() {

        User user = (User) getIntent().getSerializableExtra(MainActivity.EXTRA_USER);

        userNameText = (EditText) findViewById(R.id.registerUserName);
        userNameText.setText(user.getUsername());
        firstNameText = (EditText) findViewById(R.id.registerFirstName);
        firstNameText.setText(user.getFirstName());
        lastNameText = (EditText) findViewById(R.id.registerLastName);
        lastNameText.setText(user.getLastName());
        emailText = (EditText) findViewById(R.id.registerEmail);
        emailText.setText(user.getEmail());
        birthdayText = (EditText) findViewById(R.id.registerBirthDay);
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        birthdayText.setText(dateFormat.format(user.getBirthDate()));
        gender = user.getGender().get();
        facebookId = user.getFacebookId();
        if (gender.equalsIgnoreCase(Gender.FEMALE.get())) {
            ((RadioButton) findViewById(R.id.registerFemale)).setChecked(true);
        } else if (gender.equalsIgnoreCase(Gender.MALE.get())) {
            ((RadioButton) findViewById(R.id.registerMale)).setChecked(true);
        }
        passwordText = (EditText) findViewById(R.id.registerPassword);
        passwordConfirmation = (EditText) findViewById(R.id.registerPasswordConfirmation);
    }

    /**
     * Create a Json with the editText , got a lot of condition to avoid some field
     *
     * @return a json with the info of a user
     */
    private JSONObject createJson() {

        JSONObject jsonObject = null;

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
                jsonObject.put("email", emailText.getText().toString());
                jsonObject.put("username", userNameText.getText().toString());
                jsonObject.put("firstName", firstNameText.getText().toString());
                jsonObject.put("lastName", lastNameText.getText().toString());
                jsonObject.put("gender", gender);
                jsonObject.put("birthDate", birthdayText.getText().toString());
                jsonObject.put("facebookId", facebookId);
                jsonObject.put("plainPassword", passwordText.getText().toString());
            } catch (JSONException e) {
                Log.e("Got a problem with Json", jsonObject.toString());
            }
        }
        return jsonObject;
    }

    /**
     * Check is the mail is a valid format
     *
     * @param target the sequence of character
     * @return true if it has the form of an email
     */
    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * Task to send User Registration to the server
     */
    private class UploadUserTask extends AsyncTask<String, Void, String> {

        private final ProgressDialog dialog = MainActivity.getLoadingDialog(RegisterActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject response = new JSONObject();
            try {
                JSONObject jsonObject = new JSONObject(params[0]);
                response = DataManager.getUserClient().postUser(jsonObject);
            } catch (UserClientException | JSONException e) {
                Log.e("UploadUserTask", e.getMessage());
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String response) {
            dialog.dismiss();
            try {
                SafeJSONObject responseJson = new SafeJSONObject(response);
                if (responseJson.get(EMAIL.get(), DEFAULT_STRING)
                                .equals(emailText.getText().toString()) &&
                    responseJson.get(FIRST_NAME.get(), DEFAULT_STRING)
                                .equals(firstNameText.getText().toString()))
                {
                    DataManager.saveUser(new UserParser().parse(responseJson));
                    Toast.makeText(
                            RegisterActivity.this, R.string.register_positive,
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    String error = "";
                    try {
                        JSONArray jsonUsernameError = responseJson.getJSONObject("errors")
                                                                  .getJSONObject("children")
                                                                  .getJSONObject("username")
                                                                  .getJSONArray("errors");

                        for (int i = 0; i < jsonUsernameError.length(); i++) {
                            error = error + jsonUsernameError.getString(i);
                        }
                    } catch (JSONException e) {
                        Log.e("No Username Error", e.getMessage());
                    }
                    try {
                        JSONArray jsonEmailError = responseJson.getJSONObject("errors")
                                                               .getJSONObject("children")
                                                               .getJSONObject("email")
                                                               .getJSONArray("errors");

                        for (int i = 0; i < jsonEmailError.length(); i++) {
                            error = error + jsonEmailError.getString(i);
                        }
                    } catch (JSONException e) {
                        Log.e("No Email Error", e.getMessage());
                    }
                    if (!error.equals("")) {
                        Toast.makeText(
                                RegisterActivity.this, error,
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(
                                RegisterActivity.this, "unhandled error " + response,
                                Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException | ParserException e) {
                Log.e("UploadUserTask", e.getMessage());
            }
        }
    }
}
