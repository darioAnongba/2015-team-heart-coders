package ch.epfl.sweng.swissaffinity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Set;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.swissaffinity.utilities.network.ServerTags.LOCATIONS_INTEREST;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by sahinfurkan on 03/12/15.
 */
public class PreferenceActivityTest {
    String[] locations;
    Activity mActivity;
    SharedPreferences mPreferences;
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        mActivity = mActivityRule.getActivity();
        mPreferences = MainActivity.getPreferences();
        onView(withId(R.id.action_settings)).perform(click());
        locations = mActivityRule.getActivity().getResources().getStringArray(R.array.location_list);
    }

    @Test
    public void chooseNotChosenTest() throws Exception {
        Set<String> preferencesBefore = mPreferences.getStringSet(
                LOCATIONS_INTEREST.get(),
                null);
        if (preferencesBefore == null)
            fail("preferences could not be fetched!");

        for (String location : locations)
            if (!preferencesBefore.contains(location))
                onView(withText(location)).perform(click());

        pressBack();

        Set<String> preferencesAfter = mPreferences.getStringSet(
                LOCATIONS_INTEREST.get(),
                null);

        if (preferencesAfter != null) {
            assertEquals(preferencesAfter.size(), locations.length);
        }
        else
            fail("preferences could not be fetched!");
    }

    @Test
    public void chosenAreCheckedTest(){
        Set<String> preferencesBefore = mPreferences.getStringSet(
                LOCATIONS_INTEREST.get(),
                null);
        if (preferencesBefore == null)
            fail("preferences could not be fetched");

        for (String location : locations)
            if (!preferencesBefore.contains(location))
                onView(withText(location)).perform(click());

        for (String location : preferencesBefore){
            onView(withText(location)).check(matches(isChecked()));
        }
    }

    @Test
    public void notChosenAreNotCheckedTest(){
        Set<String> preferencesBefore = mPreferences.getStringSet(
                LOCATIONS_INTEREST.get(),
                null);
        if (preferencesBefore == null)
            fail("preferences could not be fetched");

        for (String location : preferencesBefore)
            onView(withText(location)).perform(click());

        for (String location : preferencesBefore){
            onView(withText(location)).check(matches(not(isChecked())));
        }
    }

    @Test
    public void chooseChosenTest() throws Exception {
        Set<String> preferencesBefore = mPreferences.getStringSet(
                LOCATIONS_INTEREST.get(),
                null);
        if (preferencesBefore == null)
            fail("preferences could not be fetched");

        for (String location : preferencesBefore)
            onView(withText(location)).perform(click());

        pressBack();

        Set<String> preferencesAfter = mPreferences.getStringSet(
                LOCATIONS_INTEREST.get(),
                null);

        if (preferencesAfter != null) {
            assertEquals(preferencesAfter.size(), 0);
        } else
            fail("preferences could not be fetched");
    }


}
