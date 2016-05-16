package student;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.example.piano.R;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import teacher.HttpHelper;
import teacher.HttpParser;
import teacher.ThreeLevelExpandableAdapter;
import teacher.UniversalObject;

/**
 * Created by CC on 2016/5/12.
 */
public class ExerciseRecord extends Activity {
    private static final String TAG = "ExerciseRecord";
    private String result;

    private List<UniversalObject> universalObjectList;

    private String studentId;
    private List<String> courseIds;
    private List<String> chapterIds;
    private List<String> sceneIds;
    private List<String> scores;

    private ArrayList<Level1> items;

    private Button mButton;
    private ExpandableListView eListView;
    private MainAdapter mMainAdapter;
    private OnItemClickListener mOnItemClickListener;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_record);
        mButton = (Button) findViewById(R.id.huoquchengji);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchExerciseRecord().execute();
            }
        });
        eListView = (ExpandableListView) findViewById(R.id.chengjiliebiao);
        eListView.setGroupIndicator(null);
        initListener();
        mMainAdapter = new MainAdapter(this, mOnItemClickListener);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==12580){
                    processData();
                    test();
                    prepareData();
                    eListView.setAdapter(mMainAdapter);
                    Log.i(TAG, "handleMessage");
                }
            }
        };
    }

    private void initListener() {
        mOnItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d(TAG,"position:"+position);
            }
        };
    }

    class FetchExerciseRecord extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            String path = "http://10.108.150.154:7001/pianoRoom/course/allStudentScore?studentId=1";

            result = HttpHelper.fetchExerciseRecord(path, 1);
            universalObjectList = new ArrayList<>();
            HttpParser.parseStudentExerciseRecord(result, universalObjectList);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Message msg = mHandler.obtainMessage();
            msg.what=12580;
            mHandler.sendMessage(msg);
        }
    }

    private void processData(){
        courseIds = new ArrayList<>();
        chapterIds = new ArrayList<>();
        sceneIds = new ArrayList<>();
        scores = new ArrayList<>();
        boolean courseChange=false, chapterChange=false;
        if(universalObjectList!=null && universalObjectList.size()>0){
            for(int i=0; i<universalObjectList.size(); i++){
                String totalId = universalObjectList.get(i).getTotalId();
                String[] data = totalId.trim().split("\\.");//courseId, chapterId, sceneId, studentId
                Log.i(TAG, totalId);
                studentId = data[0];
                if(courseIds.size()==0 || !courseIds.get(courseIds.size()-1).equals(data[1].trim())){
                    if(courseIds.size()>0){
                        courseChange = true;
                    }
                    courseIds.add(data[1]);

                }
                if(chapterIds.size()==0 || !chapterIds.get(chapterIds.size()-1).equals(data[2].trim())){
                    if(courseChange==true && chapterIds.size()>0){
                        chapterIds.add("segment");
                        courseChange = false;
                    }
                    if(chapterIds.size()>0){
                        chapterChange = true;
                    }
                    chapterIds.add(data[2]);
                }
                if(sceneIds.size()==0 || !sceneIds.get(sceneIds.size()-1).equals(data[3].trim())){
                    if(chapterChange==true && sceneIds.size()>0){
                        sceneIds.add("segment");
                        chapterChange = false;
                    }
                    sceneIds.add(data[3]);
                }

                scores.add(universalObjectList.get(i).getScore());
            }
        }
    }

    private void prepareData() {
        items = new ArrayList<>();
        int chapterIndex=0, sceneIndex=0;
        for (int i=0; i<courseIds.size(); i++) {
            Level1 level1 = new Level1();
            level1.title = "课程Id"+courseIds.get(i);
            items.add(level1);
            for (; chapterIndex<chapterIds.size(); chapterIndex++) {
                if(chapterIds.get(chapterIndex).equals("segment")){
                    chapterIndex+=1;
                    break;
                }
                Level2 level2 = new Level2();
                level2.title = "  课次Id"+chapterIds.get(chapterIndex);
                level1.child.add(level2);
                for (; sceneIndex<sceneIds.size(); sceneIndex++) {
                    if(sceneIds.get(sceneIndex).equals("segment")){
                        sceneIndex+=1;
                        break;
                    }
                    Level3 level3 = new Level3();
                    level3.title = "场景Id"+sceneIds.get(sceneIndex)+"   "+scores.get(sceneIndex);
                    Log.i(TAG, "场景Id"+sceneIds.get(sceneIndex)+"   "+scores.get(sceneIndex));
                    level2.child.add(level3);
                }
            }
        }
    }

    private void test() {
        for(String s :courseIds){
            Log.i(TAG, "courseId:"+s);
        }
        for(String s : chapterIds){
            Log.i(TAG, "chapterId:"+s);
        }
        for(String s : sceneIds){
            Log.i(TAG, "sceneId:"+s);
        }
    }
    /*========================================三级目录相关代码============================================*/
    class Level1 {
        String title;
        ArrayList<Level2> child = new ArrayList<Level2>();
    }

    class Level2 {
        String title;
        ArrayList<Level3> child = new ArrayList<Level3>();
    }

    class Level3 {
        String title;
    }

    class MainAdapter extends ThreeLevelExpandableAdapter {

        public MainAdapter(Context context, AdapterView.OnItemClickListener litener) {
            super(context, litener);
        }

        @Override
        public int getGroupCount() {
            return items.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return items.get(groupPosition).child.size();
        }

        @Override
        public Level1 getGroup(int groupPosition) {
            return items.get(groupPosition);
        }

        @Override
        public Level2 getChild(int groupPosition, int childPosition) {
            return items.get(groupPosition).child.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setTextSize(30);
            textView.setTextColor(getResources().getColor(
                    android.R.color.black));
            Level1 level1 = getGroup(groupPosition);
            textView.setText(level1.title);
            return textView;
        }

        @Override
        public int getThreeLevelCount(int firstLevelPosition,
                                      int secondLevelPosition) {
            return getGroup(firstLevelPosition).child.get(secondLevelPosition).child
                    .size();
        }

        @Override
        public View getSecondLevelView(int firstLevelPosition,
                                       int secondLevelPosition, boolean isExpanded, View convertView,
                                       ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setWidth(600);
            textView.setTextSize(25);
            textView.setTextColor(getResources().getColor(
                    android.R.color.black));
            Level2 level2 = getChild(firstLevelPosition, secondLevelPosition);
            textView.setText(level2.title);
            return textView;
        }

        @Override
        public View getThreeLevelView(int firstLevelPosition,
                                      int secondLevelPosition, int ThreeLevelPosition,
                                      View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            textView.setTextSize(20);
            textView.setTextColor(getResources().getColor(
                    android.R.color.black));
            Level3 level3 = getGrandChild(firstLevelPosition,
                    secondLevelPosition, ThreeLevelPosition);
            textView.setText(level3.title);
            Log.i(TAG, "ThreeView");
            return textView;
        }

        @Override
        public Level3 getGrandChild(int groupPosition, int childPosition,
                                    int grandChildPosition) {
            return getChild(groupPosition, childPosition).child
                    .get(grandChildPosition);
        }

    }

}
