package ch.epfl.sweng.swissaffinity.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import ch.epfl.sweng.swissaffinity.R;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utilities.Location;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;

/**
 * Created by Lionel on 05/11/15.
 */
public class EventExpandableListAdapter extends AbstractExpandableListAdapter<String, Event> {

    public EventExpandableListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getGroupView(
            int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.groupEvents);
        textView.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(
            int groupPosition,
            final int childPosition,
            boolean isLastChild,
            View convertView,
            ViewGroup parent)
    {

        // TODO: change it to reflect event display.
        Event event = (Event) getChild(groupPosition, childPosition);

        String eventName = event.getName();
        Location location = event.getLocation();
        String eventLocation = "";
        if (location != null) {
            eventLocation = location.getName();
        }
        Date dateBegin = event.getDateBegin();
        String eventDateBegin = "";
        if (dateBegin != null) {
            eventDateBegin = DateParser.dateToString(dateBegin);
        }

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        ((TextView) convertView.findViewById(R.id.rowEventName)).setText(eventName);
        ((TextView) convertView.findViewById(R.id.rowEventLocation)).setText(eventLocation);
        ((TextView) convertView.findViewById(R.id.rowEventDateBegin)).setText(eventDateBegin);

        return convertView;
    }
}
