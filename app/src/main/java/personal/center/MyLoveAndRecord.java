package personal.center;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.piano.R;

import java.util.ArrayList;
import java.util.List;

import personal.center.adapter.MyFragmentPageAdapter;
import personal.center.music.resource.A;
import personal.center.music.resource.B;
import personal.center.music.resource.C;
import personal.center.music.resource.D;

/**
 * Created by CC on 2016/4/7.
 */
public class MyLoveAndRecord extends Fragment {
    private ViewPager mPager;
    private MyFragmentPageAdapter mAdapter;
    private List<Fragment> mFragments;
    private TextView text1, text2, text3, text4;
    private A fg1;
    private B fg2;
    private C fg3;
    private D fg4;
    private FragmentManager mManager;
    private View view;


    //定义颜色值
    private int BLACK = 0xFF000000;
    private int Green =0xFF45C01A;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mylove_fragment, container, false);
        initMyPager();
        initView();
        mPager.setCurrentItem(0);
        text1.setTextColor(Green);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initMyPager() {
        mManager = getChildFragmentManager();
        mPager = (ViewPager) view.findViewById(R.id.mypager);
        mFragments = new ArrayList<>();
        fg1 = new A();
        fg2 = new B();
        fg3 = new C();
        fg4 = new D();
        mFragments.add(fg1);
        mFragments.add(fg2);
        mFragments.add(fg3);
        mFragments.add(fg4);
        mAdapter = new MyFragmentPageAdapter(mManager, mFragments);
        Log.i("initpager", "pager");
    }
    private void initView() {
        MyOnClick myOnClick = new MyOnClick();
        text1 = (TextView) view.findViewById(R.id.text1);
        text2 = (TextView) view.findViewById(R.id.text2);
        text3 = (TextView) view.findViewById(R.id.text3);
        text4 = (TextView) view.findViewById(R.id.text4);
        text1.setOnClickListener(myOnClick);
        text2.setOnClickListener(myOnClick);
        text3.setOnClickListener(myOnClick);
        text4.setOnClickListener(myOnClick);
        mPager.setAdapter(mAdapter);
        mPager.setOnPageChangeListener(new MyPageChangeListener());
        Log.i("initview", "view");
    }


    class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if(state==2){
                int i = mPager.getCurrentItem();
                clearChoice();
                iconChange(i);
            }
        }
    }


    class MyOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            clearChoice();
            iconChange(v.getId());
        }
    }

    private void clearChoice() {
        text1.setTextColor(BLACK);
        text2.setTextColor(BLACK);
        text3.setTextColor(BLACK);
        text4.setTextColor(BLACK);
    }
    private void iconChange(int num) {
        switch (num) {
            case R.id.text1:case 0:
                text1.setTextColor(Green);
                mPager.setCurrentItem(0);
                break;
            case R.id.text2:case 1:
                text2.setTextColor(Green);
                mPager.setCurrentItem(1);
                break;
            case R.id.text3:case 2:
                text3.setTextColor(Green);
                mPager.setCurrentItem(2);
                break;
            case R.id.text4:case 3:
                text4.setTextColor(Green);
                mPager.setCurrentItem(3);
                break;
        }
    }

}
