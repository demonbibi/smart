package student;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baidu on 16/5/13.
 */
public class LeSiAdapter extends BaseExpandableListAdapter {

    private static final String TAG = "LeSiAdapter";

    private List<String> courseIds;
    private List<List<String>> chapterScores;
    private List<List<String>> chapterComments;
    private List<List<String>> chapterIds;

    private List<String> chapters, scores, comments;

    private Context mContext;
    List<String> list;

    public LeSiAdapter(Context context, List<String> courseIds, List<String> chapters, List<String> scores,
                       List<String> comments) {
        mContext = context;
        this.courseIds = courseIds;
        chapterIds = new ArrayList<>();
        chapterScores = new ArrayList<>();
        chapterComments = new ArrayList<>();
        this.chapters = chapters;
        this.scores = scores;
        this.comments = comments;
    }

    private void processData() {
        list = new ArrayList<>();
        for(String s : chapters){
            if(s.equals("segment")){
                chapterIds.add(list);
                list  = new ArrayList<>();
                continue;
            }
            list.add(s);
        }
        chapterIds.add(list);
        list = new ArrayList<>();
        for(String s : scores){
            if(s.equals("segment")){
                chapterScores.add(list);
                list  = new ArrayList<>();
                continue;
            }
            list.add(s);
        }
        chapterScores.add(list);
        list = new ArrayList<>();
        for(String s : comments){
            if(s.equals("segment")){
                chapterComments.add(list);
                list  = new ArrayList<>();
                continue;
            }
            list.add(s);
        }
        chapterComments.add(list);
    }
    @Override
    public int getGroupCount() {
        return courseIds.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return chapterIds.get(0).size();
    }

    @Override
    public Object getGroup(int i) {
        return courseIds.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return chapterIds.get(i).get(i1)+" "+chapterScores.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        TextView kecheng = getTextView();
        kecheng.setText("课程"+courseIds.get(i));
        return kecheng;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        TextView keci = getTextView();
        keci.setText("课次"+chapterIds.get(i).get(i1));
        ll.addView(keci);
        TextView fenshu = getTextView();
        fenshu.setText("分数："+chapterScores.get(i).get(i1));
        ll.addView(fenshu);
        return ll;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private TextView getTextView() {
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(300, 80);
        TextView textView = new TextView(mContext);
        textView.setLayoutParams(lp);
        textView.setGravity(Gravity.LEFT);
        textView.setPadding(30,0,0,0);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void notifyDataSetChanged() {
        processData();
        super.notifyDataSetChanged();
        Log.i(TAG, "data set changed");
        test();
    }

    private void test(){
        for(String s: courseIds){
            Log.i(TAG, "courseId:"+s);
        }
        for(List<String> list : chapterIds){
            for(String s : list){
                Log.i(TAG, "chapterId:"+s);
            }
        }
        for(List<String> list : chapterScores){
            for(String s : list){
                Log.i(TAG, "chapterScore:"+s);
            }
        }
        for(List<String> list : chapterComments){
            for(String s : list){
                Log.i(TAG, "chapterComment:"+s);
            }
        }
    }
}
