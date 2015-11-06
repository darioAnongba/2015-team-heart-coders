package ch.epfl.sweng.swissaffinity.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractExpandableListAdapter<A, B> extends BaseExpandableListAdapter {

    protected final Context mContext;
    private final List<A> mGroups;
    private final Map<A, List<B>> mData;

    public AbstractExpandableListAdapter(Context context) {
        mContext = context;
        mData = new HashMap<>();
        mGroups = new ArrayList<>();
    }

    public boolean addGroup(A group) {
        boolean added = false;
        if (!mData.containsKey(group)) {
            mData.put(group, new ArrayList<B>());
            added = mGroups.add(group);
            notifyDataSetChanged();
        }
        return added;
    }

    public boolean addChild(A group, B child) {
        addGroup(group);
        boolean added = mData.get(group).add(child);
        if (added) {
            notifyDataSetChanged();
        }
        return added;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return mData.get(mGroups.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.get(mGroups.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
