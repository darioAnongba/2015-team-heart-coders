package ch.epfl.sweng.swissaffinity;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
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

    public void testUsername() {
        getActivity();
        onView(withId(R.id.registerEmail)).perform(typeText("max.premi@test.test"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.registerUserName)).perform(typeText("maxpremi"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.userRegistration)).perform(click());
        onView(withText(R.string.toast_text_error_firstname)).inRoot(withDecorView(not(is(getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }



}
