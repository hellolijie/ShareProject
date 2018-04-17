package com.example.test.databasemanagerlibrary.ui.database;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.databasemanagerlibrary.utils.DatabaseUtil;
import com.example.test.databasemanagerlibrary.R;
import com.example.test.databasemanagerlibrary.ui.HorizontalFixLayout;

/**
 * Created by lijie on 2017/4/17.
 */

public class TableFragment extends Fragment {
    private View view;
    private HorizontalFixLayout header;
    private RecyclerView table;

    private SQLiteDatabase sqLiteDatabase;
    private String tableName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_table, container, false);
        header = (HorizontalFixLayout) view.findViewById(R.id.hfl_header);
        table = (RecyclerView) view.findViewById(R.id.rcv_table);

        init();
        return view;
    }

    public void setDatabase(SQLiteDatabase sqliteDatabase, String tableName){
        this.sqLiteDatabase = sqliteDatabase;
        this.tableName = tableName;
    }

    private void init(){
        String[] columnArr = DatabaseUtil.getColumns(sqLiteDatabase, tableName);
        for (String columnName : columnArr){
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setText(columnName);
            header.addView(textView);
        }

        table.setAdapter(new TableAdapter(getActivity(), DatabaseUtil.getPage(sqLiteDatabase, tableName, 0, 100), columnArr));
    }

    public String getTableName(){
        return tableName;
    }
}
