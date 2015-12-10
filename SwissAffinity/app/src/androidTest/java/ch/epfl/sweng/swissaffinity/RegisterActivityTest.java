package ch.epfl.sweng.swissaffinity;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by max on 29/11/2015.
 */
public class RegisterActivityTest  extends ActivityInstrumentationTestCase2<RegisterActivity> {


    public RegisterActivityTest() {
        super(RegisterActivity.class);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }


    public void testEditTextEmpty(){
        getActivity();
        closeSoftKeyboard();
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_mail)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    public void testEditTextEmailNotValid() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("ceci ne marcheras pas"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_mail)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    public void testEditTextEmailTooLong() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("azertyuiopqsdfghjklmwxcvbnazertyuiopqsdfghjklmwxcvbnazertyuiopqsdfghjklmwxcvbnazertyuiopqsdf"));
        closeSoftKeyboard();
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_mail)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.e("Test failed",e.getMessage());
        }
    }

    public void testEmailIsGood() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("maxime.premitestneedtobelong@test.test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_username)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Log.e("Test failed",e.getMessage());
        }
    }


public void testUsernameGood() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.test"));
        onView(withId(R.id.registerUserName)).perform(typeText("maxpremi"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_firstname)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

public void testUsernameTooLong() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.test"));
        onView(withId(R.id.registerUserName)).perform(typeText("azertyuiopqsdfghjklmwxcvbnazertyuiopqsdfghjklmwxcvbnazertyuiopqsdfghjklmwxcvbnazertyuiopqsdf"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_username)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

public void testFirstNameTooLong() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.test"));
        onView(withId(R.id.registerUserName)).perform(typeText("maxpremi"));
        onView(withId(R.id.registerFirstName)).perform(typeText("azertyuiopqsdfghjklmwxcvbnazertyuiopqsdfghjklmwxcvbn"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_firstname)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

public void testFirstNameGood() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.test"));
        onView(withId(R.id.registerUserName)).perform(typeText("maxpremi"));
        onView(withId(R.id.registerFirstName)).perform(typeText("Darude"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_lastname)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

public void testLastNameTooLong() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.test"));
        onView(withId(R.id.registerUserName)).perform(typeText("maxpremi"));
        onView(withId(R.id.registerFirstName)).perform(typeText("Darude"));
        onView(withId(R.id.registerLastName)).perform(typeText("azertyuiopqsdfghjklmwxcvbnazertyuiopqsdfghjklmwxcvbn"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_lastname)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

public void testLastNameGood() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.test"));
        onView(withId(R.id.registerUserName)).perform(typeText("maxpremi"));
        onView(withId(R.id.registerFirstName)).perform(typeText("Darude"));
        onView(withId(R.id.registerLastName)).perform(typeText("Sandstrom"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_password)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

public void testPasswordOnly() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.test"));
        onView(withId(R.id.registerUserName)).perform(typeText("maxpremi"));
        onView(withId(R.id.registerFirstName)).perform(typeText("Darude"));
        onView(withId(R.id.registerLastName)).perform(typeText("Sandstrom"));
        onView(withId(R.id.registerPassword)).perform(typeText("TUTUTUTU"),ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_passwordconfirmation)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

public void testPasswordDoNotMatch() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.test"));
        onView(withId(R.id.registerUserName)).perform(typeText("maxpremi"));
        onView(withId(R.id.registerFirstName)).perform(typeText("Darude"));
        onView(withId(R.id.registerLastName)).perform(typeText("Sandstrom"));
        onView(withId(R.id.registerPassword)).perform(typeText("TUTUTUTU"));
        onView(withId(R.id.registerPasswordConfirmation)).perform(typeText("TURUTURU"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_passwordconfirmation)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

public void testPasswordDoMatch() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.test"));
        onView(withId(R.id.registerUserName)).perform(typeText("maxpremi"));
        onView(withId(R.id.registerFirstName)).perform(typeText("Darude"));
        onView(withId(R.id.registerLastName)).perform(typeText("Sandstrom"));
        onView(withId(R.id.registerPassword)).perform(typeText("TUTUTUTU"));
        onView(withId(R.id.registerPasswordConfirmation)).perform(typeText("TUTUTUTU"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_gender)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

public void testGender() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.test"));
        onView(withId(R.id.registerUserName)).perform(typeText("maxpremi"));
        onView(withId(R.id.registerFirstName)).perform(typeText("Darude"));
        onView(withId(R.id.registerLastName)).perform(typeText("Sandstrom"));
        onView(withId(R.id.registerPassword)).perform(typeText("TUTUTUTU"));
        onView(withId(R.id.registerPasswordConfirmation)).perform(typeText("TUTUTUTU"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.registerFemale)).perform(click());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_birthday)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
        }

public void testDateNotGood() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.testizhfoizen"));
        onView(withId(R.id.registerUserName)).perform(typeText("maxpreminottaken"));
        onView(withId(R.id.registerFirstName)).perform(typeText("Darude"));
        onView(withId(R.id.registerLastName)).perform(typeText("Sandstrom"));
        onView(withId(R.id.registerPassword)).perform(typeText("TUTUTUTU"));
        onView(withId(R.id.registerPasswordConfirmation)).perform(typeText("TUTUTUTU"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.registerFemale)).perform(click());
        onView(withId(R.id.registerBirthDay)).perform(typeText("1045898"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_birthday)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

        }
}
