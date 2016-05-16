package student;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import teacher.ThreeLevelExpandableAdapter;

/**
 * Created by baidu on 16/5/13.
 */
public class ExReAdapter extends ThreeLevelExpandableAdapter {
    public ExReAdapter(Context context, AdapterView.OnItemClickListener listener) {
        super(context, listener);
    }

    @Override
    public int getThreeLevelCount(int firstLevelPosition, int secondLevelPosition) {
        return 0;
    }

    @Override
    public View getSecondLevelView(int firstLevelPosition, int secondLevelPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getThreeLevelView(int firstLevelPosition, int secondLevelPosition, int ThreeLevelPosition, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public Object getGrandChild(int groupPosition, int childPosition, int grandChildPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int i) {
        return 0;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        return null;
    }
}
