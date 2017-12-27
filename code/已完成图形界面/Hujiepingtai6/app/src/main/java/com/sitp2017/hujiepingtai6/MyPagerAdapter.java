package com.sitp2017.hujiepingtai6;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gameben on 2017/5/8.
 */
public class MyPagerAdapter extends FragmentPagerAdapter { //Pager中Fragment的适配器
    private final String[] TITLES = { "充电宝", "雨伞", "笔记", "篮球", "足球",
    "乒乓球", "游戏账号"};
    List<MyFragment> fragments = new ArrayList<MyFragment>();
    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        for(String title:TITLES){
            MyFragment fragment = new MyFragment();
            Bundle args =new Bundle();
            args.putString("param", title);
            fragment.setArguments(args);
            fragments.add(fragment);
        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
    //PagerSlidingTabStrip中的标题是从这里获得的

    @Override
    public int getCount() {
        return TITLES.length;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}

