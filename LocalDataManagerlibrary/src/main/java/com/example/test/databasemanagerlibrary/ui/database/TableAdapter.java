package com.example.test.databasemanagerlibrary.ui.database;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test.databasemanagerlibrary.ui.HorizontalFixLayout;

import java.util.List;
import java.util.Map;

/**
 * Created by lijie on 2017/4/19.
 */

public class TableAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Map<String, String>> dataList;
    private String[] columnArr;

    public TableAdapter(Context context, List<Map<String, String>> dataList, String[] columnArr){
        this.context = context;
        this.dataList = dataList;
        this.columnArr = columnArr;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TableHolder(new HorizontalFixLayout(context));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TableHolder tableHolder = (TableHolder) holder;
        Map<String, String> data = dataList.get(position);

        tableHolder.horizontalFixLayout.removeAllViews();
        for (String column : columnArr){
            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setText(data.get(column));
            tableHolder.horizontalFixLayout.addView(textView);
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class TableHolder extends RecyclerView.ViewHolder{
        HorizontalFixLayout horizontalFixLayout;

        public TableHolder(View itemView) {
            super(itemView);
            horizontalFixLayout = (HorizontalFixLayout) itemView;
            horizontalFixLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        }
    }
}
