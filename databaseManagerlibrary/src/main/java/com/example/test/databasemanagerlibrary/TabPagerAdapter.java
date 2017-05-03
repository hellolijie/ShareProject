package com.example.test.databasemanagerlibrary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by lijie on 2017/4/19.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {

    private List<TableFragment> fragmentList;

    public TabPagerAdapter(FragmentManager fm, List<TableFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getTableName();
    }
}
