package com.example.test.databasemanagerlibrary.ui.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.test.databasemanagerlibrary.utils.DatabaseUtil;
import com.example.test.databasemanagerlibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijie on 2017/4/14.
 */

public class TableActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<String> tableList;

    private SQLiteDatabase sqLiteDatabase;
    private String databaseName;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_table);

        init();
    }

    private void init(){
        databaseName = getIntent().getStringExtra("databaseName");

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        sqLiteDatabase = openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
        tableList = DatabaseUtil.getTableList(sqLiteDatabase, databaseName);

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);

        List<TableFragment> fragmentList = new ArrayList<>();
        for (String tableName : tableList){
            TableFragment tableFragment = new TableFragment();
            tableFragment.setDatabase(sqLiteDatabase, databaseName);
            fragmentList.add(tableFragment);
        }

        viewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragmentList));
        tabLayout.setupWithViewPager(viewPager);
    }
}
