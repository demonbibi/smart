package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.piano.R;

import mooc.OnlineCourse;


public class MoocFragment extends Fragment {
    private OnlineCourse onlineCourse;
    private FragmentManager mManager;

    public MoocFragment() {
        // Required empty public constructor

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.mooc_fragment, container, false);
        mManager = getFragmentManager();
        FragmentTransaction ft = mManager.beginTransaction();
        onlineCourse = new OnlineCourse();
        ft.replace(R.id.mooc_content, onlineCourse);
        ft.commit();
//        selectContent();
        return view;
    }
    private void selectContent() {
        FragmentTransaction ft = mManager.beginTransaction();
        clear(ft);
        if(onlineCourse!=null){
            ft.show(onlineCourse);
        }else{
            onlineCourse = new OnlineCourse();
            ft.add(R.id.mooc_content, onlineCourse);
        }
        ft.commit();
    }
    private void clear(FragmentTransaction ft) {
        if(onlineCourse!=null){
            ft.hide(onlineCourse);
        }
    }
}
