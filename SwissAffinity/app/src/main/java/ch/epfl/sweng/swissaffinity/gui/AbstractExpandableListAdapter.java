package ch.epfl.sweng.swissaffinity.gui;

import android.content.Context;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of an Expandable List Adapter.
 *
 * @param <A> Type for the groups (usually String)
 * @param <B> Type for the children
 */
public abstract class AbstractExpandableListAdapter<A, B> extends BaseExpandableListAdapter {

    protected final Context mContext;
    private final List<A> mGroups;
    private final Map<A, List<B>> mChildren;

    protected AbstractExpandableListAdapter(Context context) {
        mContext = context;
        mGroups = new ArrayList<>();
        mChildren = new HashMap<>();
    }

    /**
     * Set the data for the expandable list adapter.
     *
     * @param groups   the groups.
     * @param children the children to add.
     */
    @SafeVarargs
    public final void setData(List<A> groups, List<B>... children) {
        if (groups == null || children == null || groups.size() != children.length) {
            throw new IllegalArgumentException();
        }
        notifyDataSetInvalidated();
        mGroups.clear();
        mChildren.clear();
        for (int i = 0; i < children.length; ++i) {
            A group = groups.get(i);
            List<B> child = children[i];
            if (child != null && !child.isEmpty()) {
                mGroups.add(group);
                mChildren.put(group, child);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildren.get(mGroups.get(groupPosition)).get(childPosition);
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
        return mChildren.get(mGroups.get(groupPosition)).size();
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
