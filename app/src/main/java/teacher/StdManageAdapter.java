package teacher;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.piano.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by baidu on 16/5/10.
 */
public class StdManageAdapter extends BaseAdapter {

    private static final String TAG = "StdManageAdapter";
    private Context context;
    private LayoutInflater mInflater;
    private Set<Student> students;
    private List<String> ids, names, connects;
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CONNECT = "connect";

    public StdManageAdapter(Context context, Set<Student> students) {
        this.context = context;
        this.students = students;
        mInflater = LayoutInflater.from(context);

    }
    public void prepareData(Set<Student> students) {
        ids = new ArrayList<>();
        names = new ArrayList<>();
        for(Student s : students){
            String str = String.valueOf(s.getStudentId());
            ids.add(str);
            str = s.getStudentName();
            names.add(str);
        }
    }
    public String getData(String type, int position) {
        String result = "";
        switch (type){
            case ID:
                result = ids.get(position);
                break;
            case NAME:
                result = names.get(position);
                break;
            case CONNECT:
                result = connects.get(position);
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mHolder;
        if(view==null){
            view = mInflater.inflate(R.layout.sm_listitem, viewGroup, false);
            mHolder = new ViewHolder();
            mHolder.id = (TextView) view.findViewById(R.id.sm_zuoweihao);
            mHolder.name = (TextView) view.findViewById(R.id.sm_xingming);
            mHolder.connect = (TextView) view.findViewById(R.id.sm_zhaungtai);
            view.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) view.getTag();
        }
            mHolder.id.setText(getData(ID, i));
            mHolder.name.setText(getData(NAME, i));
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        prepareData(students);
        super.notifyDataSetChanged();
        for(String s : ids){
            Log.i(TAG, s);
        }
        for(String s : names){
            Log.i(TAG, s);
        }
    }

    private static class ViewHolder {
        TextView id, name, connect;
    }
}
