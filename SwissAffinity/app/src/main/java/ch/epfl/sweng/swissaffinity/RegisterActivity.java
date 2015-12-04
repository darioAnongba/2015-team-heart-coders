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

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ch.epfl.sweng.swissaffinity.gui.DataManager;
import ch.epfl.sweng.swissaffinity.users.User;
import ch.epfl.sweng.swissaffinity.users.User.Gender;
import ch.epfl.sweng.swissaffinity.utilities.network.users.UserClientException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;
import ch.epfl.sweng.swissaffinity.utilities.parsers.user.UserParser;

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
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.registerGender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.registerFemale) {
                            gender = "female";
                        } else if (checkedId == R.id.registerMale) {
                            gender = "male";
                        }
                    }
                });

        fillData();

        Button registerButton = (Button) findViewById(R.id.userRegistration);
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        JSONObject json = createJson();
                        if (json != null) {
                            Log.v("UserJson", json.toString());
                            new UploadUserTask().execute(json.toString());
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
            firstNameText = (EditText) findViewById(R.id.registerFirstName);
            lastNameText = (EditText) findViewById(R.id.registerLastName);
            emailText = (EditText) findViewById(R.id.registerEmail);
            birthdayText = (EditText) findViewById(R.id.registerBirthDay);
            passwordText = (EditText) findViewById(R.id.registerPassword);
            passwordConfirmation = (EditText) findViewById(R.id.registerPasswordConfirmation);

        if (user != null) {
            userNameText.setText(user.getUsername());
            firstNameText.setText(user.getFirstName());
            emailText.setText(user.getEmail());
            SimpleDateFormat dateFormat =
                    new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            birthdayText.setText(dateFormat.format(user.getBirthDate()));
            lastNameText.setText(user.getLastName());
            gender = user.getGender().get();
            facebookId = user.getFacebookId();
            if (gender.equalsIgnoreCase(Gender.FEMALE.get())) {
                ((RadioButton) findViewById(R.id.registerFemale)).setChecked(true);
            } else if (gender.equalsIgnoreCase(Gender.MALE.get())) {
                ((RadioButton) findViewById(R.id.registerMale)).setChecked(true);
            }
        }

    }

    /**
     * Create a Json with the editText , got a lot of condition to avoid some field
     *
     * @return a json with the info of a user, null in case some field's content is not
     * in the correct format.
     */
    private JSONObject createJson() {

        JSONObject jsonObject = null;

        if (emailText.getText().toString().isEmpty() ||
            emailText.getText().toString().length() > 100 ||
            !isValidEmail(emailText.getText().toString()))
        {
            Toast.makeText(
                    RegisterActivity.this,
                    R.string.toast_text_error_mail,
                    Toast.LENGTH_SHORT).show();
        } else if ((userNameText.getText().toString().isEmpty() ||
                    userNameText.getText().toString().length() > 50))
        {
            Toast.makeText(
                    RegisterActivity.this, R.string.toast_text_error_username,
                    Toast.LENGTH_SHORT).show();
        } else if ((firstNameText.getText().toString().isEmpty() ||
                    firstNameText.getText().toString().length() > 50))
        {
            Toast.makeText(
                    RegisterActivity.this, R.string.toast_text_error_firstname,
                    Toast.LENGTH_SHORT).show();
        } else if ((lastNameText.getText().toString().isEmpty() ||
                    lastNameText.getText().toString().length() > 50))
        {
            Toast.makeText(
                    RegisterActivity.this, R.string.toast_text_error_lastname,
                    Toast.LENGTH_SHORT).show();
        } else if (passwordText.getText().toString().isEmpty()) {
            Toast.makeText(
                    RegisterActivity.this, R.string.toast_text_error_password,
                    Toast.LENGTH_SHORT).show();
        } else if (!passwordText.getText().toString().equals(
                passwordConfirmation.getText()
                                    .toString()))
        {
            Toast.makeText(
                    RegisterActivity.this, R.string.toast_text_error_passwordconfirmation,
                    Toast.LENGTH_SHORT).show();
        } else if (gender == null) {
            Toast.makeText(
                    RegisterActivity.this, R.string.toast_text_error_gender,
                    Toast.LENGTH_SHORT).show();
        } else if (birthdayText.getText().toString().length() == 0 ||
                   birthdayText.getText().toString().length() > 20 || !isThisDateValid(birthdayText.getText().toString(),"dd/MM/yyyy"))
        {
            Toast.makeText(
                    RegisterActivity.this, R.string.toast_text_error_birthday,
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

    public static boolean isThisDateValid(String dateToValidate, String dateFromat){

        if(dateToValidate == null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
        sdf.setLenient(false);

        try {
            //if not valid, it will throw ParseException
            java.util.Date date = sdf.parse(dateToValidate);
            System.out.println(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;
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
            } catch (JSONException e) {
                Log.e("UploadUserTask", e.getMessage());
            } catch (UserClientException e){
                return e.getMessage();
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String response) {
            dialog.dismiss();
            try {
                SafeJSONObject responseJson = new SafeJSONObject(response);
                DataManager.saveUser(new UserParser().parse(responseJson));
                Toast.makeText(
                        RegisterActivity.this, R.string.register_positive,
                        Toast.LENGTH_LONG).show();
                finish();
            } catch (JSONException e) {
                Toast.makeText(
                        RegisterActivity.this, response,
                        Toast.LENGTH_LONG).show();
            } catch (ParserException e){
                Log.e("UploadUserTask", e.getMessage());
            }
        }
    }
}
