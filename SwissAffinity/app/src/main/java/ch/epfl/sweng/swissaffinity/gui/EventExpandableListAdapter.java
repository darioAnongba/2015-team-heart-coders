package ch.epfl.sweng.swissaffinity.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.epfl.sweng.swissaffinity.R;
import ch.epfl.sweng.swissaffinity.events.Event;

/**
 * Created by Lionel on 05/11/15.
 */
public class EventExpandableListAdapter extends AbstractExpandableListAdapter<String, Event> {

    public EventExpandableListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent)
    {

        // TODO: change it to reflect group display (ev. hard code for 2 groups.)

        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.groupEvents);
        textView.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition,
                             final int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent)
    {

        // TODO: change it to reflect event display.
        Event event = (Event) getChild(groupPosition, childPosition);

        final String eventName = event.getName();
        final String eventDescription = event.getDesription();

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        ((TextView) convertView.findViewById(R.id.rowEventName)).setText(eventName);
        ((TextView) convertView.findViewById(R.id.rowEventDetails)).setText(eventDescription);

        return convertView;
    }
}
