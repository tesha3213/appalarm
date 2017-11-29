package com.example.dande.alarm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dande on 11/28/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<MyFragment> fragments;

    public ViewPagerAdapter(FragmentManager fm, List<MyFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return (fragments == null)? 0:fragments.size();
    }
}