package ch.epfl.sweng.swissaffinity.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import ch.epfl.sweng.swissaffinity.R;
import ch.epfl.sweng.swissaffinity.events.Event;
import ch.epfl.sweng.swissaffinity.utilities.parsers.DateParser;

/**
 * Representation of an expandable list adapter for the events.
 */
public class EventExpandableListAdapter extends AbstractExpandableListAdapter<String, Event> {

    /**
     * Constructor of the class.
     *
     * @param context the context {@link Context}
     */
    public EventExpandableListAdapter(Context context) {
        super(context);
    }

    /**
     * Constructor of the class.
     *
     * @param context the context {@link Context}
     * @param groups  the groups (or headers)
     * @param data    the children of the groups
     */
    public EventExpandableListAdapter(
            Context context,
            List<String> groups,
            Map<String, List<Event>> data)
    {
        super(context, groups, data);
    }

    @Override
    public View getGroupView(
            int groupPosition,
            boolean isExpanded,
            View convertView,
            ViewGroup parent)
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
        Event event = (Event) getChild(groupPosition, childPosition);
        String name = event.getName();
        String location = event.getLocation().getName();
        String dateBegin = DateParser.dateToString(event.getDateBegin());

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        ((TextView) convertView.findViewById(R.id.rowEventName)).setText(name);
        ((TextView) convertView.findViewById(R.id.rowEventLocation)).setText(location);
        ((TextView) convertView.findViewById(R.id.rowEventDateBegin)).setText(dateBegin);

        return convertView;
    }
}
