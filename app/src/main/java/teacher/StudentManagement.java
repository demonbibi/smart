package teacher;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piano.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by CC on 2016/5/9.
 */
public class StudentManagement extends Activity {
    private static final String TAG = "StudentManagement";
    private Button mButton;
    private ListView studentList;
    private EditText mEditText;
    private TextView mTextView;
    private Set<Student> studentInfos;
    private Course course;
    private String classroomId;
    private String result;
    private StdManageAdapter mAdapter;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_management);
        initView();
        init();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.kaoqin);
        mEditText = (EditText) findViewById(R.id.jiaoshibianhao);
        mTextView = (TextView) findViewById(R.id.dangqiankecheng);
        studentList = (ListView) findViewById(R.id.studentListView);
    }
    private void init() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getClassroomId();
                if(!classroomId.equals("")){
                    new MyTask().execute();
                }
            }
        });
        studentInfos = new HashSet<>();
        course = new Course();
        preProcess();
        mAdapter = new StdManageAdapter(StudentManagement.this,
                studentInfos);
        studentList.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();//重写了该方法！注意！
        mHandler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                if(msg.what==123){
                    store2SharedPerferences();
                }
            }
        };
        if(course.getCourseId()!=-1){
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("课程Id："+course.getCourseId());
            mTextView.setTextSize(30);
        }
    }

    private void preProcess(){
        SharedPreferences pref = getSharedPreferences("students", MODE_PRIVATE);
        if(pref==null)return;
        Map<String, ?> map2 = pref.getAll();
        Student student;
        for(Map.Entry<String, ?> e : map2.entrySet()){
            student = new Student();
            student.setStudentId(Integer.parseInt(e.getKey()));
            student.setStudentName((String)e.getValue());
            studentInfos.add(student);
        }
        SharedPreferences pref2 = getSharedPreferences("courseId", MODE_PRIVATE);
        course.setCourseId(pref2.getInt("courseId", -1));
    }

    private void getClassroomId() {
       classroomId = mEditText.getText().toString().trim();
    }

    private void store2SharedPerferences(){
        SharedPreferences.Editor editor1 =
                getSharedPreferences("chapters", MODE_PRIVATE).edit();
        Log.i(TAG, "chapters: "+course.getChapters());
        for(int i=0; i<course.getChapters(); i++){
            Log.i(TAG, course.getScene(i)+" "+i);
            editor1.putString(String.valueOf(2*i), String.valueOf(course.getChapterId(i)));
            editor1.putString(String.valueOf(2*i+1), String.valueOf(course.getScene(i)));
            Log.i(TAG, "场景数量"+course.getScene(i));
        }
        editor1.commit();
        SharedPreferences.Editor editor2 =
                getSharedPreferences("students", MODE_PRIVATE).edit();
        for(Student s : studentInfos){
            editor2.putString(String.valueOf(s.getStudentId()), s.getStudentName());
        }
        editor2.commit();
        SharedPreferences.Editor editor3 =
                getSharedPreferences("courseId", MODE_PRIVATE).edit();
        editor3.putInt("courseId", course.getCourseId());
        editor3.commit();
    }

    class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String path = "http://10.108.150.154:7001/pianoRoom/getStudents?classroomId="
                    + classroomId;
            result = HttpHelper.fetchStudentsInClass(path);
            HttpParser.parseRoomStudent(result, studentInfos, course);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(StudentManagement.this, "获取学生信息", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mAdapter.notifyDataSetChanged();
            mTextView.setVisibility(View.VISIBLE);
            mTextView.setText("课程Id："+course.getCourseId());
            mTextView.setTextSize(30);
            Message msg = mHandler.obtainMessage();
            msg.what = 123;
            mHandler.sendMessage(msg);
        }
    }
}
