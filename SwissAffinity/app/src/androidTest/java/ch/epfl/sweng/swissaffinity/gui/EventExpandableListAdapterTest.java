package ch.epfl.sweng.swissaffinity.gui;

import android.content.Context;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.swissaffinity.DataForTesting;
import ch.epfl.sweng.swissaffinity.MainActivity;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.events.SpeedDatingEvent;
import ch.epfl.sweng.swissaffinity.utilities.parsers.ParserException;

import static org.junit.Assert.assertEquals;

/**
 * Created by sahinfurkan on 03/12/15.
 */
public class EventExpandableListAdapterTest {
    /* getGroupView
     * getChildView
     * getChildId
     */

    EventExpandableListAdapter adapter;
    List<List<Event>> childrenTest;
    List<String> groupsTest;
    @Before
    public void setUp(){
        Context context = Mockito.mock(Context.class);

        adapter = new EventExpandableListAdapter(context);

        childrenTest = new ArrayList<>();
        try {
            List<Event> events = new ArrayList<>();
            events.add(DataForTesting.createSpeedDatingEvent());
            events.add(DataForTesting.createSpeedDatingEvent());
            childrenTest.add(events);
            events = new ArrayList<Event>();
            events.add(DataForTesting.createSpeedDatingEvent());
            childrenTest.add(events);
        } catch (ParserException e) {
            e.printStackTrace();
        }

        groupsTest = new ArrayList<>();
        groupsTest.add("group1");
        groupsTest.add("group2");
    }

    @Test
    public void getGroupCount(){
        adapter.setData(groupsTest, childrenTest);

        assertEquals(adapter.getGroupCount(), groupsTest.size());
    }

    @Test
    public void getChildrenCount(){
        adapter.setData(groupsTest, childrenTest);

        assertEquals(adapter.getChildrenCount(0), childrenTest.get(0).size());
        assertEquals(adapter.getChildrenCount(1), childrenTest.get(1).size());
    }

    @Test
    public void hasStableId(){
        assertEquals(adapter.hasStableIds(), false);
    }

    @Test
    public void isEmptyTest() {
        assertEquals(adapter.isEmpty(), true);
        adapter.setData(groupsTest, childrenTest);
        assertEquals(adapter.isEmpty(), false);
    }

    @Test
    public void getChildTest(){
        adapter.setData(groupsTest, childrenTest);

        assertEquals(adapter.getChild(0, 0).equals(childrenTest.get(0).get(0)), true);
        assertEquals(adapter.getChild(1,0).equals(childrenTest.get(1).get(0)), true);
    }

    @Test
    public void getChildIdTest(){
        adapter.setData(groupsTest, childrenTest);

        assertEquals(adapter.getChildId(0, 0), childrenTest.get(0).indexOf(adapter.getChild(0, 0)));
        assertEquals(adapter.getChildId(1, 0), childrenTest.get(1).indexOf(adapter.getChild(1, 0)));
    }

    @Test
    public void getGroupViewTest(){
        // TODO
    }

    @Test
    public void getChildViewTest(){
        // TODO
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDataGroupNull(){
        List<List<Event>> children = new ArrayList<>();

        adapter.setData(null, children);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDataChildrenNull(){
        List<String> groups = new ArrayList<>();

        adapter.setData(groups, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void childrenNumIsNotEqualGroupNum(){
        List<List<Event>> children = new ArrayList<>();
        List<String> groups = new ArrayList<>();
        groups.add("group1");

        adapter.setData(groups, children);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void getChildIllegalIndexForGroup(){
        adapter.setData(groupsTest, childrenTest);

        adapter.getChild(3, 0);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void getChildIllegalIndexForChild(){
        adapter.setData(groupsTest, childrenTest);

        adapter.getChild(1, 5);
    }

}
