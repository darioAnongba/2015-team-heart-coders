package ch.epfl.sweng.swissaffinity.end_to_end;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import ch.epfl.sweng.swissaffinity.MainActivity;
import ch.epfl.sweng.swissaffinity.events.Establishment;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.utilities.Address;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.network.DefaultNetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.NetworkProvider;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClient;
import ch.epfl.sweng.swissaffinity.utilities.network.events.EventClientException;
import ch.epfl.sweng.swissaffinity.utilities.network.events.NetworkEventClient;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;
import ch.epfl.sweng.swissaffinity.utilities.parsers.SafeJSONObject;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Joel on 11/19/2015.
 * Modified by Dario on 09.12.2015
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class GetEventTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() {
        mActivityRule.getActivity();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullNetworkProviderForEvent() {
        new NetworkEventClient(NetworkProvider.SERVER_URL, null);
    }

    @Test
    public void testGetSpeedDatingEvent() throws EventClientException {
        NetworkProvider networkProvider = new DefaultNetworkProvider();
        EventClient eventClient = new NetworkEventClient(NetworkProvider.SERVER_URL, networkProvider);
        SpeedDatingEvent event = (SpeedDatingEvent) eventClient.fetchBy(6);
        //Oktoberfest event at Zurich.
        Date beginDate;
        Date endDate;
        try {
            beginDate = DateParser.parseFromString(
                    "2016-10-15T21:00:00+0200",
                    DateParser.SERVER_DATE_FORMAT);
            endDate = DateParser.parseFromString(
                    "2016-10-16T00:00:00+0200",
                    DateParser.SERVER_DATE_FORMAT);
        } catch (ParserException e) {
            throw new EventClientException(e);
        }

        assertEquals(event.getName(), "Let's celebrate Oktoberfest");
        assertEquals(
                event.getDescription(),
                "Come and join us" +
                        " to celebrate the Oktoberfest , and use this occasion to meet new People . The event" +
                        " will take place to Forum , a bar in the center of Zürich.");
        assertEquals(event.getImagePath(), "5647627162808.jpg");
        assertEquals(event.getMinAge(), 21);
        assertEquals(event.getMaxAge(), 32);
        assertEquals(event.getMaxPeople(), 40);
        assertEquals(event.getMenSeats(), 20);
        assertEquals(event.getWomenSeats(), 20);
        assertEquals(event.getWomenRegistered(), 0);

        assertEquals(event.getBasePrice(), 35.0, 0.000001);
        assertEquals(event.getDateBegin().toString(), beginDate.toString());
        assertEquals(event.getDateEnd().toString(), endDate.toString());
        assertEquals(event.getLocation().getName(), new Location(6, "Zürich").getName());
        assertEquals(
                event.getEstablishment(),
                new Establishment(
                        2,
                        "Forum",
                        Establishment.Type.BAR,
                        new Address("CH", 8004, "Zürich", "Zürich", 120, "Badenerstrasse"),
                        "+41 43 243 88 88",
                        "Located at the corner of Badenerstrasse, Forum is an airy lounge bar and restaurant, ideal for kicking back and unwinding.",
                        SafeJSONObject.DEFAULT_STRING,
                        250,
                        SafeJSONObject.DEFAULT_STRING)
        );
    }
}
