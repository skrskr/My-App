package com.mohamed.myapplication;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by mohamed on 13/04/18.
 */

public class MyTaskPageAdapter extends FragmentStatePagerAdapter {

    public MyTaskPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0)
            return TaskSwipeFragment.newInstance("Upcoming");
        else
            return TaskSwipeFragment.newInstance("Past");
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "Upcoming";
        else
            return "Past";
    }
}
