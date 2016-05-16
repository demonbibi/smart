package teacher;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piano.R;

public class CourseBehavior extends Activity {

	protected static final String TAG = "CourseBehavior";
	private ArrayList<Level1> items;
	private OnItemClickListener mOnItemClickListener;
	private RadioButton rb1, rb2;
	private List<UniversalObject> universalObjectList;
    private int curStudentId;
    private int curChapterId;

	private String courseId;
    private List<String> chapterIds;
	private List<String> sceneIds;
	private Set<String> studentIds;
	private List<String> students;
	private List<String> scores;

    private MainAdapter mMainAdapter;
	private Handler mHandler;

	private int flag;
	private static final int RB1 = 1000;
	private static final int RB2 = 2000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.course_behavior);
//		initInfo();
		final ExpandableListView listView = (ExpandableListView) findViewById(R.id.cb_list);
		rb1 = (RadioButton) findViewById(R.id.cb_xuesheng);
		rb1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				flag = RB1;
				new FetchSceneScoresTask().execute();
			}
		});
		rb2 = (RadioButton) findViewById(R.id.cb_kecheng);
		rb2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				flag = RB2;
				new FetchSceneScoresTask().execute();
			}
		});
		listView.setGroupIndicator(null);
		mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1000){
					Log.i(TAG, "handler");
					processData();
					if(flag==RB1){
						prepareData4rb1();
					}else if(flag==RB2){
						prepareData4rb2();
					}
					listView.setAdapter(mMainAdapter);
				}
			}
		};
		initListener();
		mMainAdapter = new MainAdapter(CourseBehavior.this, mOnItemClickListener);
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

	private void prepareData4rb2() {
		items = new ArrayList<>();
		int count = 0;
		for (String chapterId : chapterIds) {
			Level1 level1 = new Level1();
			level1.title = "课次"+chapterId;
			items.add(level1);
			for (String sceneId : sceneIds) {
				Level2 level2 = new Level2();
				level2.title = "  场景"+sceneId;
				level1.child.add(level2);
				for (int i=0; i<studentIds.size(); i++) {
					Level3 level3 = new Level3();
					level3.title = "Id"+students.get(count)+"："+scores.get(count);
					level2.child.add(level3);
					count++;
				}
			}
		}
	}
	private void prepareData4rb1() {
		items = new ArrayList<>();
		int count = 0;
		for (String studentId : studentIds) {
			Level1 level1 = new Level1();
			level1.title = "学生Id"+studentId;
			items.add(level1);
			for (String chapterId : chapterIds) {
				Level2 level2 = new Level2();
				level2.title = "  课次"+chapterId;
				level1.child.add(level2);
				for (String sceneId : sceneIds) {
					Level3 level3 = new Level3();
					level3.title = "场景Id"+sceneId+"   "+scores.get(count);
					level2.child.add(level3);
					count++;
				}
			}
		}
	}

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

		public MainAdapter(Context context, OnItemClickListener litener) {
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
			return textView;
		}

		@Override
		public Level3 getGrandChild(int groupPosition, int childPosition,
				int grandChildPosition) {
			return getChild(groupPosition, childPosition).child
					.get(grandChildPosition);
		}

	}

	private Set<Student> studentInfos;
	private Course course;

	class FetchSceneScoresTask extends AsyncTask<String, Integer, String> {
		String result;
		@Override
		protected String doInBackground(String... strings) {
			Log.i(TAG, "do in background start");
			String path = "http://10.108.150.154:7001/pianoRoom/course/allScores?courseId=1";

			result = HttpHelper.fetchSceneScore(path, 1);
			universalObjectList = new ArrayList<>();
			HttpParser.parseStudentChapterSceneScore(result, universalObjectList);

			Log.i(TAG, "do in background start");
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(CourseBehavior.this, "获取场景成绩中...", Toast.LENGTH_LONG).show();
		}

		@Override
		protected void onPostExecute(String s) {
			Log.i(TAG, "onPost");
			super.onPostExecute(s);
			Message msg = mHandler.obtainMessage();
			msg.what=1000;
			mHandler.sendMessage(msg);
		}
	}

	private void processData(){
		chapterIds = new ArrayList<>();
		sceneIds = new ArrayList<>();
		studentIds = new LinkedHashSet<>();
		scores = new ArrayList<>();
		students = new ArrayList<>();
		if(universalObjectList!=null && universalObjectList.size()>0){
			for(int i=0; i<universalObjectList.size(); i++){
				String totalId = universalObjectList.get(i).getTotalId();
				String[] data = totalId.trim().split("\\.");//courseId, chapterId, sceneId, studentId
				courseId = data[0];
				if(chapterIds.size()==0 || !chapterIds.get(chapterIds.size()-1).equals(data[1].trim())){
					chapterIds.add(data[1]);
				}
				if(sceneIds.size()==0 || !sceneIds.get(sceneIds.size()-1).equals(data[2].trim())){
					sceneIds.add(data[2]);
				}
				studentIds.add(data[3]);
				students.add(data[3]);
				scores.add(universalObjectList.get(i).getScore());
			}
		}
	}

}
