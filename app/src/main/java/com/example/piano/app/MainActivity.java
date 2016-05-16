package com.example.piano.app;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.piano.R;

import fragment.LoginFragment;
import fragment.MainFragment;
import fragment.MoocFragment;
import fragment.PersonFragment;
import student.ExerciseRecord;
import student.LearningSituation;
import teacher.CourseBehavior;
import teacher.CourseJudge;
import teacher.StudentManagement;
import teacher.test2.TreeView;

public class MainActivity extends AppCompatActivity {

    RadioButton rb1,rb2,rb3,rb4;
    FragmentManager mManager;
    View mNavigationbar;
    ViewPager mViewPager;
    MainFragment mainFragment;
    MoocFragment moocFragment;
    PersonFragment personFragment;
    LoginFragment loginFragment;
    RadioGroup radioGroup;
    boolean backPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        mNavigationbar = findViewById(R.id.navigation_below_container);
        radioGroup = (RadioGroup) mNavigationbar.findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new MyCheckedChangeListener());
        rb1 = (RadioButton)mNavigationbar.findViewById(R.id.radioButton1);
        rb2 = (RadioButton)mNavigationbar.findViewById(R.id.radioButton2);
        rb3 = (RadioButton)mNavigationbar.findViewById(R.id.radioButton3);
        rb4 = (RadioButton)mNavigationbar.findViewById(R.id.radioButton4);
        initDefault();
    }

    private void initDefault(){
        mainFragment = new MainFragment();
        mManager = getSupportFragmentManager();
        FragmentTransaction ft = mManager.beginTransaction();
        ft.replace(R.id.content, mainFragment);
        ft.commit();
    }
    @Override
    public void onBackPressed(){
        if(!backPressed){
            Toast.makeText(getApplication(),"再按一次返回键退出",Toast.LENGTH_SHORT).show();
            backPressed = true;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    backPressed = false;
                }
            };
            Handler handler = new Handler();
            handler.postDelayed(runnable,3000);
        }else{
            super.onBackPressed();
        }
    }
    class MyCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(mManager==null){
                mManager = getSupportFragmentManager();
            }
            switch(checkedId){
                case R.id.radioButton1:
//                    Fragment mainFragment = new MainFragment();
//                    FragmentTransaction ft1 = mManager.beginTransaction();
//                    ft1.replace(R.id.content, mainFragment);
//                    ft1.commit();
                    showFragment(1);
                    break;
                case R.id.radioButton2:
                    startActivity(new Intent(MainActivity.this, LearningSituation.class));
                    break;
                case R.id.radioButton3:
//                    Fragment moocFragment = new MoocFragment();
//                    FragmentTransaction ft3 = mManager.beginTransaction();
//                    ft3.replace(R.id.content, moocFragment);
////                    fragmentTransaction.addToBackStack("01");
//                    ft3.commit();
                    startActivity(new Intent(MainActivity.this, CourseJudge.class));
//                    showFragment(3);
                    break;
                case R.id.radioButton4:
////                    startActivity(new Intent(CourseBehavior.this, PersonalCenterActivity.class));
//                    Fragment personFragment = new PersonFragment();
//                    FragmentTransaction ft4 = mManager.beginTransaction();
//                    ft4.replace(R.id.content, personFragment);
////                    fragmentTransaction.addToBackStack("01");
//                    ft4.commit();
                    startActivity(new Intent(MainActivity.this, ExerciseRecord.class));
//                    showFragment(4);
                    break;
                default:
                    break;

            }
        }
    }
    private void showFragment(int index) {
        FragmentTransaction ft = mManager.beginTransaction();
        // 想要显示一个fragment,先隐藏所有fragment，防止重叠
        hideFragments(ft);
        switch (index) {
            case 1:
                // 如果fragment1已经存在则将其显示出来
                if (mainFragment != null)
                    ft.show(mainFragment);
                    // 否则是第一次切换则添加fragment1，注意添加后是会显示出来的，replace方法也是先remove后add
                else {
                    mainFragment = new MainFragment();
                    ft.add(R.id.content, mainFragment);
                }
                ft.commit();
                break;
            case 2:

                break;
            case 3:
                if (moocFragment != null)
                    ft.show(moocFragment);
                else {
                    moocFragment = new MoocFragment();
                    ft.add(R.id.content, moocFragment);
                }
                ft.commit();
                break;
            case 4:
                if (personFragment != null)
                    ft.show(personFragment);
                else {
                    personFragment = new PersonFragment();
                    ft.add(R.id.content, personFragment);
                }
                ft.commit();
                break;
        }

    }

    // 当fragment已被实例化，就隐藏起来
    private void hideFragments(FragmentTransaction ft) {
        if (mainFragment != null){
            ft.hide(mainFragment);
        }
        if (moocFragment != null){
            ft.hide(moocFragment);
        }
        if (personFragment != null){
            ft.hide(personFragment);
        }
    }


}
