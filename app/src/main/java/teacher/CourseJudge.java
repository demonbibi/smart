package teacher;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.piano.R;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by baidu on 16/5/10.
 */
public class CourseJudge extends Activity {
    private static final String TAG = "CourseJudge";
    private Set<Student> studentInfos;
    private Course course;
    private String classroomId;

    private ArrayAdapter<String> spinnerAdapter;
    private String[] chaptersData;
    private int[] studentIds;
    private Spinner mSpinner;

    private ListView mListView;
    private StdManageAdapter mStdAdapter;

    private Button mButton;
    private EditText editScore, editComment;

    private String curChapterId="1", curStudentId="1";
    private String score="81", comment="哈哈哈哈哈哈";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_judge);
        initInfo();
//        test();
        init();
        initView();
    }

    private void initView() {
        mSpinner = (Spinner) findViewById(R.id.xuanzekeci);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                chaptersData);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CourseJudge.this, chaptersData[i], Toast.LENGTH_SHORT).show();
                curChapterId = chaptersData[i].substring(2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mListView = (ListView) findViewById(R.id.mingdan);
        mListView.setAdapter(mStdAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                curStudentId = studentIds[i]+"";
                Toast.makeText(CourseJudge.this, "选中学生的Id:"+(i+1), Toast.LENGTH_SHORT).show();
            }
        });
        mStdAdapter.notifyDataSetChanged();
        mButton = (Button) findViewById(R.id.shangchuan);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score = editScore.getText().toString().trim();
                comment = editComment.getText().toString().trim();
                if(score.equals("")){
                    Toast.makeText(CourseJudge.this, "请输入分数", Toast.LENGTH_SHORT).show();
                }
                if(comment.equals("")){
                    Toast.makeText(CourseJudge.this, "请输入评语", Toast.LENGTH_SHORT).show();
                }
                new UploadTask().execute();
            }
        });
        editScore = (EditText) findViewById(R.id.shurufenshu);
        editComment = (EditText) findViewById(R.id.shurupingyu);
    }

    private void initInfo() {
        studentInfos = new HashSet<>();
        course = new Course();
        SharedPreferences pref2 = getSharedPreferences("students", MODE_PRIVATE);
        Map<String, ?> map2 = pref2.getAll();
        Student student;
        studentIds = new int[map2.entrySet().size()];
        int i=0;
        for(Map.Entry<String, ?> e : map2.entrySet()){
            student = new Student();
            student.setStudentId(Integer.parseInt(e.getKey()));
            studentIds[i] = Integer.parseInt(e.getKey());
            i++;
            student.setStudentName((String)e.getValue());
            studentInfos.add(student);
        }
        SharedPreferences pref1 = getSharedPreferences("chapters",MODE_PRIVATE);
        Map<String, ?> map1 = pref1.getAll();
        course.setChapters(map1.entrySet().size()>>1);
        Log.i(TAG, "map1 entrySet size: "+map1.entrySet().size());
        int count=0;
        for(Map.Entry<String, ?> e : map1.entrySet()){
            if(count%2!=0){
                course.addScene(Integer.parseInt((String)e.getValue()));//按顺序读出来?
                count++;
            }
        }
    }

    private void init(){
        int chapters = course.getChapters();
        chaptersData = new String[chapters];
        for(int i=0; i<chapters; i++){
            chaptersData[i] = "课次"+(i+1);
        }
        mStdAdapter = new StdManageAdapter(CourseJudge.this, studentInfos);

    }

    class UploadTask extends AsyncTask<String, Integer, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            String path = "http://10.108.150.154:7001/pianoRoom/chapter/update?"+
                    "studentId="+curStudentId+"&"+
                    "chapterId="+curChapterId+"&"+
                    "score="+score+"&"+"comment=";
            int reponseCode = HttpHelper.teacherUpload(path, comment);
            return reponseCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(CourseJudge.this, "上传中...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Integer a) {
            super.onPostExecute(a);
            if(a==200){
                Toast.makeText(CourseJudge.this, "上传成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CourseJudge.this, "上传失败", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
