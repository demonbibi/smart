package personal.center;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piano.R;

import personal.center.adapter.PersonalCenterListAdapter;

/**
 * Created by CC on 2016/4/7.
 */
public class PersonalCenterList extends Fragment implements AdapterView.OnItemClickListener{

    private TextView textView;
    private ListView listView;
    private PersonalCenterListAdapter mAdapter;
    private String[] text;
    private Drawable[] drawableLeft;
    private Drawable drawableRight;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_center_list, container, false);
        initRes();
        textView = (TextView) view.findViewById(R.id.title);
        listView = (ListView) view.findViewById(R.id.list);
        textView.setText("Welcome");
        mAdapter = new PersonalCenterListAdapter(text, drawableLeft, drawableRight, getActivity());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    private void initRes() {
        text = new String[]{"最近播放","我喜欢的","练习记录","帮助文档"};
        drawableLeft = new Drawable[]{getResources().getDrawable(R.mipmap.user__11),
                getResources().getDrawable(R.mipmap.user__25),
                getResources().getDrawable(R.mipmap.user__46),
                getResources().getDrawable(R.mipmap.user__51)
        };
        for(Drawable drawable : drawableLeft){
            drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        }
        drawableRight = getResources().getDrawable(R.mipmap.user__18);
        drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(),
                drawableRight.getMinimumHeight());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mAdapter.getOldView()!=null){
            mAdapter.getOldView().setBackgroundColor(
                    getResources().getColor(R.color.listitem_default));

            Log.i("PersonalCenter", "clear");
        }
        view.setBackgroundColor(
                getResources().getColor(R.color.listitem_selected)
        );
        mAdapter.setOldView(view);
        Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
    }
}
