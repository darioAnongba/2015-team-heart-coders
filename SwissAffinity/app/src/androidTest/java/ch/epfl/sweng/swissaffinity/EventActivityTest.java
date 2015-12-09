package ch.epfl.sweng.swissaffinity;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by yannick on 05.12.15.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EventActivityTest {

    @Rule
    public ActivityTestRule<EventActivity> mEventTest =
            new ActivityTestRule<>(EventActivity.class);

    @Before
    public void setup() {

    }

    @Test
    public void test() {

    }



}
