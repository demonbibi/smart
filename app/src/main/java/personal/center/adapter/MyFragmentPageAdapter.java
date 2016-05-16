package personal.center.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by CC on 2016/4/11.
 */
public class MyFragmentPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public MyFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }
    public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragmentList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        Log.i("getItem","get");
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
