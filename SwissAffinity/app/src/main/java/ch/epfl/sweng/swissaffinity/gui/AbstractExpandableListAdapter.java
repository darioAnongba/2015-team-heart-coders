package ch.epfl.sweng.swissaffinity.gui;

import android.content.Context;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of an Expandable List Adapter.
 *
 * @param <A> Type for the groups (usually String)
 * @param <B> Type for the data (could be any type)
 */
public abstract class AbstractExpandableListAdapter<A, B> extends BaseExpandableListAdapter {

    protected final Context mContext;
    private final List<A> mGroups;
    private final Map<A, List<B>> mData;

    protected AbstractExpandableListAdapter(Context context, List<A> groups, Map<A, List<B>> data) {
        mContext = context;
        mGroups = new ArrayList<>(groups);
        mData = new HashMap<>(data);
    }

    protected AbstractExpandableListAdapter(Context context) {
        this(context, Collections.<A>emptyList(), Collections.<A, List<B>>emptyMap());
    }

    /**
     * Add a group to the list adapter.
     *
     * @param group the group
     *
     * @return true if the group was added. false otherwise.
     */
    public boolean addGroup(A group) {
        boolean added = false;
        if (!mData.containsKey(group)) {
            mData.put(group, new ArrayList<B>());
            added = mGroups.add(group);
            if (added) {
                notifyDataSetChanged();
            }
        }
        return added;
    }

    /**
     * Add a child in the desired group.
     *
     * @param group the group.
     * @param child the child to add to that group.
     *
     * @return true if the child was added. false otherwise.
     */
    public boolean addChild(A group, B child) {
        addGroup(group);
        boolean added = mData.get(group).add(child);
        if (added) {
            notifyDataSetChanged();
        }
        return added;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mData.get(mGroups.get(groupPosition)).get(childPosition);
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
        return true;
    }
}
