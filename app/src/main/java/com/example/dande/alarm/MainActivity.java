package com.example.dande.alarm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mViewPagerAdapter;
    private List<MyFragment> fragmentList;
    private MyDatabase myDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this,AlarmService.class));
        myDatabase = MyDatabase.getInstance(this);
        myDatabase.open();
        initFragments();
        addComponents();
        addEvents();


    }


    private void addEvents() {

    }
    private void initFragments(){
        fragmentList = new ArrayList<>();
        fragmentList.add(new AlarmFragment());
        fragmentList.add(new StopwatchFragment());
        fragmentList.add(new TimeFragment());
    }

    private void addComponents() {
        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        setSupportActionBar(mToolbar);

        mViewPager = (ViewPager) findViewById(R.id.mViewpager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        mViewPager.setAdapter(mViewPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
        mTabLayout.setSelectedTabIndicatorHeight(2);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
