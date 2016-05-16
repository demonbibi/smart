package student;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.piano.R;

import java.util.ArrayList;
import java.util.List;

import teacher.HttpHelper;
import teacher.HttpParser;
import teacher.UniversalObject;

/**
 * Created by CC on 2016/5/12.
 */
public class LearningSituation extends Activity {

    private static final String TAG = "LearningSituation";

    private Button mButton;
    private ExpandableListView eListView;
    private LeSiAdapter leSiAdapter;
    private Handler mHandler;

    private String result;
    private List<UniversalObject> universalObjectList;
    private String studentId;
    private List<String> courseIds;
    private List<String> chapterIds;
    private List<String> chapterScores;
    private List<String> chapterComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learning_situation);
        processData();
        initView();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.chaxun);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchLearningSituation().execute();
            }
        });
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==2500){
                    processData();
                    test();
                    leSiAdapter = new LeSiAdapter(LearningSituation.this, courseIds, chapterIds, chapterScores, chapterComments);
                    eListView.setAdapter(leSiAdapter);
                    leSiAdapter.notifyDataSetChanged();
                }
            }
        };
        eListView = (ExpandableListView) findViewById(R.id.xuexiqingkuang_list);
        eListView.setGroupIndicator(null);

    }

    private void processData() {
        courseIds = new ArrayList<>();
        chapterIds = new ArrayList<>();
        chapterScores = new ArrayList<>();
        chapterComments = new ArrayList<>();
        boolean courseChange=false;
        if(universalObjectList!=null && universalObjectList.size()>0){
            for(int i=0; i<universalObjectList.size(); i++){
                String studentTotalId = universalObjectList.get(i).getStudentTotalId();
                String[] data = studentTotalId.trim().split("\\.");//courseId, chapterId, sceneId, studentId
                Log.i(TAG, studentTotalId);
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
//                        courseChange = false;
                    }
                    chapterIds.add(data[2]);
                }

                if(courseChange==true && chapterScores.size()>0){
                    chapterScores.add("segment");
                }
                chapterScores.add(universalObjectList.get(i).getChapterScore());

                if(courseChange==true && chapterComments.size()>0){
                    chapterComments.add("segment");
                    courseChange = false;
                }
                chapterComments.add(universalObjectList.get(i).getChapterComment());
            }
        }
    }

    class FetchLearningSituation extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String path = "http://10.108.150.154:7001/pianoRoom/course/allStudentScore?studentId=1";
            result = HttpHelper.fetchExerciseRecord(path, 1);
            universalObjectList = new ArrayList<>();
            HttpParser.parseLearnSituation(result, universalObjectList);
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
            msg.what=2500;
            mHandler.sendMessage(msg);
        }
    }

    private void test() {
        for(String s : courseIds){
            Log.i(TAG, "courseId:"+s);
        }
        for(String s : chapterIds){
            Log.i(TAG, "courseId:"+s);
        }
        for(String s : chapterScores){
            Log.i(TAG, "score:"+s);
        }
        for(String s : chapterComments){
            Log.i(TAG, "comment:"+s);
        }
    }
}
