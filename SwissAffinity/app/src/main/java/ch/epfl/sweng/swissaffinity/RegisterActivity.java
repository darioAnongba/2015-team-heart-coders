package ch.epfl.sweng.swissaffinity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ch.epfl.sweng.swissaffinity.users.User;

import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.BIRTHDAY;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.EMAIL;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FACEBOOKID;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.FIRST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.GENDER;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LAST_NAME;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.USERNAME;

public class RegisterActivity extends AppCompatActivity {

    private User mUser = null;
    private String name;
    private String email;
    private String firstName;
    private String facebookId;
    private String gender = null;
    private String lastName;
    private String birthday;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPreferences = MainActivity.SHARED_PREFS;

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

        RadioGroup gender = (RadioGroup) this.findViewById(R.id.registerGender);
        gender.setOnCheckedChangeListener(radioChecker);
        fillData();

    }

    public void fillData(){

        name = sharedPreferences.getString(USERNAME.get(), "");
        firstName = sharedPreferences.getString(FIRST_NAME.get(), "");
        lastName = sharedPreferences.getString(LAST_NAME.get(), "");
        email = sharedPreferences.getString(EMAIL.get(), "");
        birthday = sharedPreferences.getString(BIRTHDAY.get(), "");
        gender = sharedPreferences.getString(GENDER.get(), "");
        facebookId = sharedPreferences.getString(FACEBOOKID.get(),"");

        EditText userName = (EditText) findViewById(R.id.registerUserName);
        userName.setText(name);
        EditText firstNameText = (EditText) findViewById(R.id.registerFirstName);
        firstNameText.setText(firstName);
        EditText lastNameText = (EditText) findViewById(R.id.registerLastName);
        lastNameText.setText(lastName);
        EditText emailText = (EditText) findViewById(R.id.registerEmail);
        emailText.setText(email);
        EditText birthdayText = (EditText) findViewById(R.id.registerBirthDay);
        birthdayText.setText(birthday);
        if (gender == "female") {
            RadioButton female = (RadioButton) findViewById(R.id.registerFemale);
            female.setChecked(true);
        } else if (gender == "male") {
            RadioButton male = (RadioButton) findViewById(R.id.registerMale);
            male.setChecked(true);
        }
    }
}
