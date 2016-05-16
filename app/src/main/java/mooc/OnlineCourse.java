package mooc;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.example.piano.R;

import org.json.JSONArray;
import org.json.JSONObject;


import java.util.ArrayList;

import utils.HttpUtils;

/**
 * Created by CC on 2016/4/13.
 */
public class OnlineCourse extends Fragment {
    View view;
    Handler mHandler;
    ArrayList<String> coursePicUrl;
    Button tempButton;
    GridView mCourseWall;
    OnlineCourseAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.onlinecourse, container, false);
        initView();
        initHandler();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.cancelAllTasks();
    }

    private void initView() {
        coursePicUrl = new ArrayList<String>();
        mCourseWall = (GridView) view.findViewById(R.id.onlineCourseGridView);
        tempButton = (Button) view.findViewById(R.id.temp);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new MyThread()).start();
            }
        });

    }
    private void initHandler() {

        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if(msg.what==520){
                    String result = (String)msg.obj;
                    try {
                        JSONObject jObject = new JSONObject(result);
                        JSONArray courses = jObject.getJSONArray("courses");

                        for(int i=0; i<courses.length(); i++){
                            coursePicUrl.add(HttpUtils.getIP()+
                                    courses.getJSONObject(i).getString("coursePic")
                            );
                            Log.i("coursePic", courses.getJSONObject(i).getString("coursePic"));
                        }
                        mCourseWall.setAdapter(new OnlineCourseAdapter(getActivity(), 0,
                                mCourseWall, coursePicUrl
                        ));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
    }
    class MyThread implements Runnable {

        @Override
        public void run() {
            Looper.prepare();
            String result = HttpUtils.sendPostForAllCourse(HttpUtils.getCourseUrl(), "utf-8");
            Message msg = mHandler.obtainMessage();
            msg.what = 520;
            msg.obj = result;
            mHandler.sendMessage(msg);
            Looper.loop();
        }
    }
}
