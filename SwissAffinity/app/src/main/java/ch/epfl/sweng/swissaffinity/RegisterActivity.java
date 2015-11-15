package ch.epfl.sweng.swissaffinity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import org.json.JSONException;
import org.json.JSONObject;
import ch.epfl.sweng.swissaffinity.users.User;
import static ch.epfl.sweng.swissaffinity.MainActivity.SHARED_PREF;
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
        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);

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

        Button registerButton = (Button) findViewById(R.id.userRegistration);
        registerButton.setClickable(false);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                JSONObject json = createJson();
                Log.v("Json", json.toString());
            }
        });

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


    private JSONObject createJson() {
        EditText emailText = (EditText) findViewById(R.id.registerEmail);
        EditText userNameText = (EditText) findViewById(R.id.registerUserName);
        EditText firstNameText = (EditText) findViewById(R.id.registerFirstName);
        EditText lastNameText = (EditText) findViewById(R.id.registerLastName);
        EditText birthdayText = (EditText) findViewById(R.id.registerBirthDay);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.put("email", emailText.getText().toString());
            jsonObject.put("username",userNameText.getText().toString());
            jsonObject.put("firstName",firstNameText.getText().toString());
            jsonObject.put("lastName",lastNameText.getText().toString());
            jsonObject.put("gender",gender);
            jsonObject.put("birthDate",birthdayText.getText().toString());
            jsonObject.put("facebookId",facebookId);
            jsonObject.put("plainPassword","123Test");
        } catch (JSONException e) {
        }
        return jsonObject;
    }

    public void post() {
        
    }





}
