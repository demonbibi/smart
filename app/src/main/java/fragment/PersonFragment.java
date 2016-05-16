package fragment;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.piano.R;

import personal.center.MyLoveAndRecord;
import personal.center.PersonalCenterList;


public class PersonFragment extends Fragment {
    PersonalCenterList listFragment;
    View view;
    MyLoveAndRecord myLoveAndRecord;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.personal_center, container, false);
        init();
        return view;
    }
    private void init() {
        if(listFragment==null){
            listFragment = new PersonalCenterList();
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ftList = fm.beginTransaction();
        ftList.replace(R.id.person_list, listFragment);
        ftList.commit();
        FragmentManager cfm = getChildFragmentManager();
        FragmentTransaction ftContent = cfm.beginTransaction();
        myLoveAndRecord = new MyLoveAndRecord();
        ftContent.replace(R.id.person_content, myLoveAndRecord);
        ftContent.commit();
    }
}
