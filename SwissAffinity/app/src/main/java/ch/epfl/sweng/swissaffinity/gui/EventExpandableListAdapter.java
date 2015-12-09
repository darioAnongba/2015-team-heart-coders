package ch.epfl.sweng.swissaffinity.gui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import ch.epfl.sweng.swissaffinity.EventActivity;
import ch.epfl.sweng.swissaffinity.MainActivity;
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

    @Override
    public View getGroupView(
            int groupPosition,
            boolean isExpanded,
            View convertView,
            ViewGroup parent)
    {
        String title = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_group, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.groupEvents);
        textView.setText(title);

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
        final Event event = (Event) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }
        convertView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), EventActivity.class);
                        intent.putExtra(MainActivity.EXTRA_EVENT, event.getId());
                        v.getContext().startActivity(intent);
                    }
                });
        String name = event.getName();
        String location = event.getLocation().getName();
        String dateBegin = DateParser.dateToString(event.getDateBegin());
        ((TextView) convertView.findViewById(R.id.rowEventName)).setText(name);
        ((TextView) convertView.findViewById(R.id.rowEventLocation)).setText(location);
        ((TextView) convertView.findViewById(R.id.rowEventDateBegin)).setText(dateBegin);

        return convertView;
    }

    public void setData(List<List<Event>> children)
    {
        if (children == null || children.size() > 2) {
            throw new IllegalArgumentException();
        }
        super.setData(getGroups(), children);
    }

    private List<String> getGroups() {
        String myEvents = mContext.getString(R.string.my_events);
        String upcomingEvents = mContext.getString(R.string.upcoming_events);
        return Arrays.asList(myEvents, upcomingEvents);
    }
}
